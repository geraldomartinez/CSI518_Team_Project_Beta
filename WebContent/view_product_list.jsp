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
        	
        </style>
       	
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <div id="page_content_wrapper">
	        <%
	            String productListMessage = (String) request.getAttribute("productListMessage"); //Obtain the message to be displayed for the product list page (if there is one)
	            if (productListMessage == null) { //Prevent null pointer exception
	            	productListMessage = "";
	            }
	        %>
        	<div id="product_list_message" class="message"><%=productListMessage%></div>
        	<br />
        	<h2>Products Listing For [Seller Name]</h2>
        	<table id="product_list_table">
        		<tr>
        			<th colspan="4">
        				[Product Category #1]
        			</th>
        		</tr>
        		<tr>
        			<td>
        				[Image]
        			</td>
        			<td>
        				[Product #1]
        			</td>
        			<td>
						<button type="submit" class="gold_button" name="edit">Edit</button>
                    </td>
	            	<td>
		            	<form id="delete_product" action="RemoveProduct" method="POST">
		            		<input name="productID" value="3" type="text" style="display: none;" />
			            	<button type="submit" name="delete" value="delete"> Delete </button>
		            	</form>
	            	</td>
        		</tr>
        	</table>
        </div>
	</body>
</html>