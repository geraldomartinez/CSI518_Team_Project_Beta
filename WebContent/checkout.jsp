<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Checkout - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->       	
       	<style type="text/css">
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        <%
            //If the user is already logged in, just forward them to the account page
            //(note, this will only happen if the user deliberately accesses login.jsp while logged in via the browser's address bar. The buttons to get here are hidden to the user)
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
            String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
            if (loggedIn == null) { //Prevent null pointer exception
                loggedIn = "false";
            }

            if (loggedIn != "true" || !usr.getAccountType().equals("B")) { //If the user is not logged in OR they are not a buyer
                request.setAttribute("indexMessage", "Only buyers may access the checkout page");
                rd.forward(request, response); //Forward the user with the response above
            }
        %>
            <h1 id="header">Checkout</h1>   
            <h3 id="sub_header">Please fill out the following information to place your order</h3>
            <%
                String checkoutMessage = (String) request.getAttribute("checkoutMessage"); //Obtain the login message from the session
                if (checkoutMessage == null) { //Prevent null pointer exception
                	checkoutMessage = "";
                }
            %>
            <div id="checkout_message" class="message"><%=checkoutMessage%></div>
        	<br />
            <div id="checkout_form_wrapper">
                <form id="checkout_form" action="CheckoutServlet" method="POST">
                    <input name="email" type="text" placeholder="Email"/>
                    <br />
                    <input name="password" type="password" placeholder="Password"/>
                    <br />
                    <button type="submit" class="gold_button" name="login"><span>Place Order</span></button>
                </form>
            </div>
        </div>
	</body>
</html>