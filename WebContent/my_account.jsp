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
	            int userID = usr.GetUserID();
	        %>
        	<div id="account_message" class="message"><%=accountMessage%></div>
        	<%
        	 String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
            if (loggedIn == null) { //Prevent null pointer exception
                loggedIn = "false";
            }

            if (loggedIn == "false") { //If the user is logged in
                //Alert the user that they are already logged in
            	out.println("<font color=red>You must log in to view your account!</font>");
            }else {%>
        	<br />
        	<h1>My Account</h1>
        	<br />
        	
			<%
			if (acctType.equals("S")){
			%>
			<a href="view_product_list.jsp">View Product List</a>
			<br />
        	<br />
			<a href="add_product.jsp">Add Product</a>
			<br />
        	<br />
			<a href="view_seller_account_info.jsp">Account Information</a>
			<br />
        	<br />
        	<%
			}
			%>
        	<%
			if ( acctType.equals("A")){
			%>
			<a href="admin_product_list.jsp">View Product List</a>
			<br/>
			<br/>
			<a href="add_product.jsp">Add Product</a>
			<br />
        	<br />
			<a href="view_sellers.jsp">View Sellers</a>
			<br/>
			<br/>
			<a href="view_buyers.jsp">View Buyers</a>
			<br/>
			<br/>
			<%
			}
        	if ( acctType.equals("B")){
			%>
			<a href="view_buyer_account_info.jsp">Account Information</a>
			<br />
        	<br />
        	<%
			}
			%>
			<a href="notifications.jsp?userID=<%=userID%>">Notifications</a>
			<br />
        	<br />
			<a href="view_orders.jsp"><%= ((acctType.equals("A"))?"All":"My") %> Orders</a>
			<br />
			<br />
			<br />
			<br />	
			<%		
			if (acctType.equals("S") || acctType.equals("B")){
			%>
			<br />
        	<br />
			<a style="color: #F23A5C;" href="deactivate.jsp">Deactivate Account</a>
			<br/>
			<br/>
			<%
			}
            }
			%>
        </div>
	</body>
</html>