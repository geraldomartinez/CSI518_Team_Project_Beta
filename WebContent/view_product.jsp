<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Product - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
	</head>
	<body>
		<%@ page import="controller.Product" %>
		<%@ page import="controller.AuthDAO" %>
		<%@ page
		import="java.util.*, java.sql.*, java.util.Date, java.text.SimpleDateFormat"%>
        <%@include file="top_menu.jsp"%>
        <br />
        
        
        <%
        	String strProductID = request.getParameter("productID");
        	if (strProductID == null){
        		strProductID = "";
        	}
        	
        	if (strProductID == ""){
   		%>
	        <div id="page_content_wrapper">
	        	<h3 style="text-align: center;">No product ID given</h3>
	        </div>
   		<%
        	}else{
	       		int productID = Integer.parseInt(strProductID);
	       		Product new_prd = AuthDAO.getProductById(productID);
		      	String pName = new_prd.GetProductName();
		      	String pDescription = new_prd.GetDescription();
		      	String pSpecs = new_prd.GetSpecs();
		      	float pPrice = new_prd.GetPrice();
		      	int pQuantity = new_prd.GetNumInStock();
		      	String picture=new_prd.getPicture();
        %>
        
        <div id="page_content_wrapper">
	        <%
	            String productMessage = (String) request.getAttribute("productMessage"); //Obtain the message to be displayed for the product page (if there is one)
	            if (productMessage == null) { //Prevent null pointer exception
	            	productMessage = "";
	            }
	        %>
        	<div id="product_mkessage" class="message"><%=productMessage%></div>
        	<h3>Product Page</h3> <br/>
        	Product: <%=pName%>  <br/>
        	<div ><img src="data:image/jpeg;base64, <%=picture%>" height=200 width=200></div>
        	
        	<%
        	ArrayList<Integer> RatingAndCount = AuthDAO.getProductAverageRating(productID);
        	if(!RatingAndCount.isEmpty()){
	      	int avgRating = RatingAndCount.get(1);
	      	int numberOfReviewers = RatingAndCount.get(0);
				for(int i = 1; i<=avgRating; i++){
					%>
					<span style="color: yellow;">&#9733;</span> 
					<%
				}
				
				 %>
				(<%=numberOfReviewers %> Reviews) <%} 
        	else{
        		for(int i = 1; i<=5; i++){
					%>
					<span style="color: yellow;">&#9734;</span> 
					<%
				}
        		%>(No Reviews)<%
        	}
				
				
				%>
        	<br>
        	Description: <%=pDescription%>  <br/>
        	Specs: <%=pSpecs%>  <br/>
        	Quantity Available: <%=pQuantity%>  <br/>
        	Price: <%=pPrice%>  <br/>
        	
        	
        	<br />
        	
        	<%
        	if ((!navLoggedIn.equals("true") || (navLoggedIn.equals("true") && acctType.equals("B"))) && pQuantity > 0){
        	%>
			<form id="add_2_cart_form" action="Add2CartServlet" method="POST">
				<input type="hidden" name="productID" value="<%= Integer.toString(productID) %>" />
				<button type="submit">Add To Cart</button>
			</form>
			<%
        	}else{
        		%>
        		<h5>OUT OF STOCK!</h5>
        		<%
        		
        	}
			%>
        	
        	<%
        	if (navLoggedIn.equals("true") && acctType.equals("B")){
        	%>
			<br />
			<form id="add_2_cart_form" action="Add2WishlistServlet" method="POST">
				<input type="hidden" name="productID" value="<%= Integer.toString(productID) %>" />
				<input type="hidden" name="quantity" value="1" />
				<button type="submit" name="addToWishBtn">Add To Wish List</button>
			</form>
			<%
        	}
			%>
			
			<h3>Customer Reviews</h3>
			__________________________________________________________________________
			<br />
		<!--	<table> -->
			
			<%
					Connection conn = null;
					ResultSet rs = null;
					int categoryID;
					String datetime = null;
					String review = null;
					int rating = 0;
					int reviewerID = 0;
					String reviewerFirstName = null;
					String reviewerLastName = null;
					SimpleDateFormat fromDatabase = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
					SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
					String newDate = "";
					try {
						conn = AuthDAO.createConn();
						HttpSession ss = request.getSession();

						PreparedStatement pst = conn
								.prepareStatement("SELECT p.*, u.firstName, u.lastName FROM ProductReviews p JOIN UserProfile u ON u.userID = p.userID WHERE p.productID ="+productID+ "");
						rs = pst.executeQuery();
						while (rs.next()) {
							datetime = rs.getTimestamp("time").toString();
							rating = rs.getInt("ranking");
							review = rs.getString("review");
							reviewerID = rs.getInt("userID");
							reviewerFirstName = rs.getString("firstName");
							reviewerLastName = rs.getString("lastName");
							
							newDate = myFormat.format(fromDatabase.parse(datetime));
				%>
			<!--	<tr> -->
			<!--	<td nowrap> -->
			<br>
				Reviewed by <strong><%=reviewerFirstName + " " + reviewerLastName %></strong> on <%=newDate%>
				<br><br>
				<%
				for(int i = 1; i<=rating; i++){
					%>
					<span style="color: yellow;">&#9733;</span>
					<%
				}
				
				 %>
				<br>
				<%=review %>
				__________________________________________________________________________________
		<!--		</td> -->
		<!--		</tr> -->
				<br>
				<%
					}
						conn.close();
					} catch (Exception e) {
						out.print(e);
					}
				%>
					
<!--		</table> -->
			
        </div>
        <%
        	}
        %>
	</body>
</html>