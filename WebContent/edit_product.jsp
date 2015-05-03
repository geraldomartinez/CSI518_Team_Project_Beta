<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit a Product</title>

  <%@ page import="controller.AuthDAO" %>
<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
		
        <%@include file="top_menu.jsp"%>
        
</head>
   <body>
   <form id="edit_product" name="edit_product" method="UpdateProductServlet" method="POST" enctype="multipart/form-data">
    <div id="page_content_wrapper">
		<%
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
	        String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
			String updateProductMessage = (String) request.getAttribute("updateProductMessage"); //Obtain the message from the session (if there is one)
			
			if (updateProductMessage == null) { //Prevent null pointer exception
				updateProductMessage = "";
			}
			
	        if (loggedIn == null) { //Prevent null pointer exception
	            loggedIn = "false";
	        }
	
	        if (loggedIn != "true") { //If the user is logged in
	            //Alert the user that they are already logged in
	            request.setAttribute("indexMessage", "Please log into your seller account before attempting to edit a product");
	            rd.forward(request, response); //Forward the user with the response above
	        }
		%>
		<div id="update_product_message" class="message"><%=updateProductMessage%></div>
		<br />
    <%@ page import="controller.Product" %>
    <%
    String productName = "";
	String pDescription = "";
	String pSpecs = "";
	float pPrice = 0;
	int productID=0;
	String picture="";
	Connection conn = null;
	ResultSet rs = null;
	Product prod = null;
	int sellerID=0 ,categoryID,pQuantity=0;
	float groundCost=0, twoCost=0,nextCost=0;

	try {			
	
		String strProductID = request.getParameter("productID");
    	if (strProductID == null){
    		strProductID = "";
    	}
    	 productID = Integer.parseInt(strProductID);
         prod = AuthDAO.getProductById(productID);
		int userID=User.GetUserID();
			String sql = "SELECT * FROM Products WHERE productID='"+productID+"'AND sellerID="+userID+";";
			conn = AuthDAO.createConn();
			
			
			PreparedStatement pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				productName = rs.getString("productName");
				pDescription = rs.getString("description");
				pPrice = rs.getFloat("unitPrice");
				pSpecs = rs.getString("specs");
				productID = rs.getInt("productID");
				pQuantity=rs.getInt("quantity");
				groundCost=rs.getFloat("groundCost");
				twoCost=rs.getFloat("twoCost");
				nextCost=rs.getFloat("nextCost");
				picture = prod.getPicture();
			
				 
	
					}
					conn.close();
				} catch (Exception e) {
					out.print(e);
				}
	
		%>
		  
	
		<h4><a href="view_product.jsp?productID=<%=productID %>" style="color: white;"><%=productName%></a></h4>
		<div ><a href="view_product.jsp?productID=<%=productID %>"><img src="<%=picture%>" style="max-width: 300px; max-height: 300px;"></a></div>
		<br />
		
		 <input type="text" value="<%=productName%>" name="productName" />
		 <input type="text" value="<%=pDescription%>" name="description" />
		 <input type="text" value="<%=pSpecs%>" name="specs" />
		 <input type="text" value="<%=pPrice%>" name="price" />
		 <input type="text" value="<%=pQuantity%>" name="quantity" />
		 <input type="text" value="<%=groundCost%>" name="ground_cost" />
		 <input type="text" value="<%=twoCost%>" name="two_cost" />
		 <input type="text" value="<%=nextCost%>" name="next_cost" />
		 <input type="submit" value="submit" name="updatebt">
					
    </div>
<br/>

    </form>
</body>
</html>
