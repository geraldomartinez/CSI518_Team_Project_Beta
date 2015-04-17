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
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        <%
            //If the user is already logged in, just forward them to the account page
            //(note, this will only happen if the user deliberately accesses login.jsp while logged in via the browser's address bar. The buttons to get here are hidden to the user)
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp"); //Setup the request dispatcher for the login page
            
            if (navLoggedIn != "true") { //If the user is logged in
                //Alert the user that they must be logged into view the cart
                request.setAttribute("loginMessage", "You must be logged in to access the cart page");
                rd.forward(request, response); //Forward the user with the response above
            }else if (!acctType.equals("B")){
            	rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
                request.setAttribute("indexMessage", "Only buyers are allowed to access carts");
                rd.forward(request, response); //Forward the user with the response above
            }
        %>
            <h1 id="sub_header">Cart</h1>
            <%
                String cartMessage = (String) request.getAttribute("cartMessage"); //Obtain the cart message from the session
                if (cartMessage == null) { //Prevent null pointer exception
                	cartMessage = "";
                }
            %>
            <div id="cart_message" class="message"><%=cartMessage%></div>
        	<br />
				
            </div>
        </div>
	</body>
</html>