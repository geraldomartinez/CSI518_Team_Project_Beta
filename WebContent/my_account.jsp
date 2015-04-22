<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
       	
       	<style type="text/css">
       		#page_content_wrapper{
       			text-align: center;
       		}
       		#page_content_wrapper a{
       			color: white;
       		}
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
	        <%
	            String accountMessage = (String) request.getAttribute("accountMessage"); //Obtain the message to be displayed for the my account page (if there is one)
	            if (accountMessage == null) { //Prevent null pointer exception
	            	accountMessage = "";
	            }
	        %>
        	<div id="account_message" class="message"><%=accountMessage%></div>
        	<br />
        	<h1>My Account</h1>
        	<br />
        	
			<%
			if (acctType.equals("S") || acctType.equals("A")){
			%>
			<a href="view_product_list.jsp">View Product List</a>
			<br />
        	<br />
			<a href="add_product.jsp">Add Product</a>
			<br />
        	<br />
			<a href="#">Notifications</a>
			<%
			}
			%>
			<br />
        	<br />
			<a href="#">Orders</a>
        </div>
	</body>
</html>