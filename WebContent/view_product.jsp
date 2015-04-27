<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		import="java.util.*, java.sql.*"%>
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
		      	System.out.println(picture+" i am ");
		      	
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
        	<div ><img src="img/<%=picture%>" height=200 width=200></div>
        	Description: <%=pDescription%>  <br/>
        	Specs: <%=pSpecs%>  <br/>
        	Quantity Available: <%=pQuantity%>  <br/>
        	Price: <%=pPrice%>  <br/>
        	
        	
        	<br />
        	
        	<%
        	if (!navLoggedIn.equals("true") || (navLoggedIn.equals("true") && acctType.equals("B"))){
        	%>
			<form id="add_2_cart_form" action="Add2CartServlet" method="POST">
				<input type="hidden" name="productID" value="<%= Integer.toString(productID) %>" />
				<button type="submit">Add To Cart</button>
			</form>
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
			<table>
			<tr>
			<%
					Connection conn = null;
					ResultSet rs = null;
					int categoryID;
					//String categoryName = "";
					String datetime = null;
					String review = null;
					int rating = 0;
					int reviewerID = 0;
					String reviewerFirstName = null;
					String reviewerLastName = null;
					
					try {
						conn = AuthDAO.createConn();
						HttpSession ss = request.getSession();

						PreparedStatement pst = conn
								.prepareStatement("SELECT * FROM `ProductReviews` where productID = '" + productID + "' ORDER BY time ASC");
						rs = pst.executeQuery();
						while (rs.next()) {
							//categoryID = rs.getInt("categoryID");
							//categoryName = rs.getString("categoryName");
							datetime = rs.getDate("time").toString();
							//list.put(categoryID, categoryName);
							rating = rs.getInt("ranking");
							review = rs.getString("review");
							reviewerID = rs.getInt("userID");
						}
						
						pst=conn.prepareStatement("SELECT * FROM UserProfile where userID = '" + reviewerID + "'");
						rs = pst.executeQuery();
						while(rs.next()){
							reviewerFirstName = rs.getString("firstName");
							reviewerLastName = rs.getString("lastName");
				%>
				<td><%=reviewerFirstName + " " + reviewerLastName %></td>
				<td>
					<%=datetime%>
				</td>
					<%=rating %>
				<td>
				</td>
				<td>
				 	<%=review %>
				</td>
				
				<%
					}
						//conn.close();
					} catch (Exception e) {
						out.print(e);
					}
				%>
					</tr>
		</table>
			
        </div>
        <%
        	}
        %>
	</body>
</html>