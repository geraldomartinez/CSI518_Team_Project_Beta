<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.AuthDAO" %>
<%@ page import="controller.Product" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Checkout - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->       	
       	<style type="text/css">
	       	#page_content_wrapper{
	       		text-align: center;
	       	}
	       	#checkout_form input{
	       		margin-bottom: 5px;
	       	}
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
	        <%
	        	String itemsString = "";
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
            <%
                String checkoutMessage = (String) request.getAttribute("checkoutMessage"); //Obtain the login message from the session
                if (checkoutMessage == null) { //Prevent null pointer exception
                	checkoutMessage = "";
                }
            %>
            <div id="checkout_message" class="message"><%=checkoutMessage%></div>
        	<br />
            <h3 id="sub_header">Your items:</h3>
        	<div id="checkout_items_wrapper">
            <%
                List<CartItem> items = cart.GetAllItems();
            	Product prod;
           		for (int i=0; i < items.size(); i++){
					prod = AuthDAO.getProductById(items.get(i).GetProductID());
       		%>
       				<%=prod.GetProductName()%>
       		<%
            	}
            %>
        	</div>
            <h3 id="sub_header">Shipping Details:</h3>
            <div id="checkout_form_wrapper">
                <form id="checkout_form" action="CheckoutServlet" method="POST">
                    <input name="shipping_name" type="text" placeholder="Recipient's Name"/>
                    <br />
                    <input name="shipping_address" type="text" placeholder="Shipping Address"/>
                    <br />
                    <input name="shipping_city" type="text" placeholder="Shipping City"/>
                    <br />
                    <input name="shipping_state" type="text" placeholder="Shipping State"/>
                    <br />
                    <input name="shipping_zip" type="number" placeholder="Shipping Zip Code"/>
                    <br />
                    <input name="paypal_email" type="text" placeholder="PayPal Email Address"/>
                    <br />
                    <input name="paypal_password" type="number" placeholder="Shipping Zip Code"/>
                    <br />
                    <br />
                    <button type="submit" name="place_order">Place Order</button>
                </form>
            </div>
        </div>
	</body>
</html>