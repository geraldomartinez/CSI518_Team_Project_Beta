<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Products</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        
        <style type="text/css">
        	#product_list_table{
        		width: 100%;
        	}
        	
        	#product_list_table th{
        		background: #2C193B;
        		text-align: left;
        		padding: 5px;
        	}
        	
        	#product_list_table td:first-child{
        		padding-left: 10px;
        	}
        	
        	#product_list_table td{
        		padding-top: 5px;
        		padding-bottom: 5px;
        	}
        	
        </style>
       	
	</head>
	<body>
	<%@ page
		import="controller.AuthDAO,controller.Product,controller.Utilities,java.util.*, java.sql.*"%>
        <%@include file="top_menu.jsp"%>
        <div id="page_content_wrapper">
	        <%
	            String productListMessage = (String) request.getAttribute("productListMessage"); //Obtain the message to be displayed for the product list page (if there is one)
	            if (productListMessage == null) { //Prevent null pointer exception
	            	productListMessage = "";
	            }
	           String sellerName = usr.GetFirstName();
	           
	        %>
        	<div id="product_list_message" class="message"><%=productListMessage%></div>
        	<br />
        	<h2>Products Listing For <%=sellerName %></h2>
        	<table id="product_list_table">
        	<%
        		int sellerID = usr.GetUserID(); //Page will error out if there is no seller logged in...
	        	Connection conn = null;
				ResultSet rs = null; //Handles the list of categories
				ResultSet rs2 = null; //Handles the list of products
				int categoryID =0;
				int productID = 0;
				String categoryName = "";
				String productName = "";
				Product prd;
				
				try {
					String sql = "select distinct p.categoryName, p.categoryID from ProductCategories p WHERE categoryID IN (SELECT categoryID FROM `Products` WHERE sellerID='"+ sellerID + "') order by p.categoryName;";
					
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
	
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						categoryID = rs.getInt("categoryID");
						categoryName = rs.getString("categoryName");					
        	
        		%>
        		<tr>
        			<th colspan="4">
        				<%=categoryName %>
        			</th>
        		</tr>
        		<%
        			String sql2 = "SELECT * FROM  `Products` WHERE categoryID ='"+ categoryID + "' and removed='0' order by productName;";
        			pst = conn.prepareStatement(sql2);
					rs2 = pst.executeQuery();
        			while(rs2.next()){
        				productID = rs2.getInt("productID");
        				prd = AuthDAO.getProductById(productID);
        				%>
        		<tr>
        			<td>
        				<img src="<%=prd.getPicture()%>" style="max-width: 50px; max-height: 50px;"/>
        			</td>
        			<td>
        				
        				<a href="view_product.jsp?productID=<%=prd.GetProductID()%>" style="color: white;"><%=prd.GetProductName()%></a>
        			</td>
        			<td>
        			
        			<button type="button"  value="edit" name="edit" onclick="document.location="edit_product.jsp?productID=<%=prd.GetProductID()%>'">Edit</button>
					
                    </td>
	            	<td>
		            	<form id="delete_product" action="RemoveProductServlet" method="POST">
		            		<input name="productID" value="<%=prd.GetProductID()%>" type="text" style="display: none;" />
			            	<button type="submit" name="delete" value="delete"> Delete </button>
		            	</form>
	            	</td>
        		</tr>
        		
        		<%
        				}//inner while loop
        			
					}//Outer loop
						conn.close();
					} catch (Exception e) {
						out.print(e);
					}
				%>
        		
        	
        		
        	</table>
        </div>
	</body>
</html>