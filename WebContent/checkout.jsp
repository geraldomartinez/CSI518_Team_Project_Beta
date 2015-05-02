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
			h3{
        		background: #2C193B;
				padding-top: 5px;
				padding-bottom: 5px;
			}
	       	#checkout_form input{
	       		margin-bottom: 5px;
	       	}
	       	#checkout_items_wrapper{
	       		text-align: center;
	       	}
	       	#checkout_items_wrapper table{
				width: 100%;
				border-collapse: collapse;
			}
			#checkout_items_wrapper th{
				border-bottom: 1px solid white;
				padding-bottom: 5px;
			}
			#checkout_items_wrapper td{
				padding-top: 5px;
			}
			#total_table{
				display: inline-block;
				border-collapse: collapse;
			}
			#total_table td:nth-child(1){
				text-align: left;
			}
			#total_table td:nth-child(2){
				padding-left: 20px;
				text-align: right;
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
	                request.setAttribute("indexMessage", "Please log in as a buyer to access the checkout page");
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
            <h3 id="sub_header">Items</h3>
        	<div id="checkout_items_wrapper">
	        	<table style="width: 100%;">
		        	<tr>
						<th colspan="2">Product</th>
						<th>Price</th>
						<th>Shipping Cost</th>
						<th>Quantity</th>
					</tr>
		            <%
		            	float productShippingCost;
		                List<CartItem> items = cart.GetAllItems();
		            	Product prod;
		            	
		            	if (items.size() == 0){
		            	    request.setAttribute("indexMessage", "Please add items to your cart before checking out");
			                rd.forward(request, response); //Forward the user with the response above
		            	}
		            	
		           		for (int i=0; i < items.size(); i++){
							prod = AuthDAO.getProductById(items.get(i).GetProductID());
		       		%>
	       			<tr>
		     			<td>
		     				<img src="<%=prod.getPicture()%>" style="max-height: 50px; max-width: 50px;"/>
		     			</td>
		     			<td>
		     				<%=prod.GetProductName()%>
		     			</td>
		     			<td>
		     				$<%= String.format("%.02f", prod.GetPrice()) %>
		     			</td>
		     			<td>
		     				<%
							productShippingCost = -1.0f;
							switch(cart.GetShippingMethod()){
								case 1:
									productShippingCost = prod.GetGroundShippingCost();
									break;
								case 2:
									productShippingCost = prod.GetTwoDayShippingCost();
									break;
								case 3:
									productShippingCost = prod.GetNextDayShippingCost();
									break;
							}
							out.print("$"+String.format("%.02f", productShippingCost));
							%>
						</td>
		     			<td>
		     				<%= items.get(i).GetQuantity() %>
		     			</td>
	     			</tr>
	       		<%
	            	}
	            %>
	            </table>
	            <br />
	            
        	</div>
            <br />
            <br />
			<table id="total_table">
				<tr>
					<td>
						Cost:
					</td>
					<td>
						<%= "$"+String.format("%.2f", cart.GetCost()) %>
					</td>
				</tr>
				<tr>
					<td>
						Total Shipping Cost:
					</td>
					<td>
						<%= "$"+String.format("%.2f", cart.GetShippingCost()) %>
					</td>
				</tr>
				<tr>
					<td>
						Tax:
					</td>
					<td>
						<%= "$"+String.format("%.2f", cart.GetTax()) %>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold; border-top: 1px solid white;">
						Total:
					</td>
					<td style="font-weight: bold; border-top: 1px solid white;">
						<%= "$"+String.format("%.2f", cart.GetTotal()) %>
					</td>
				</tr>
			</table>
            <br />
            <br />
            <h3 id="sub_header">Order Details</h3>
            <div id="checkout_form_wrapper">
            	<div>
            	<span style="font-weight: bold;">Shipping Method:</span>
				<br /> 
            	<%
					switch(cart.GetShippingMethod()){
						case 1:
							out.print("Ground (3-5 days)");
							break;
						case 2:
							out.print("Two-day shipping");
							break;
						case 3:
							out.print("Next-day shipping");
							break;
					}
				%>
            	</div>
            	<br />
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
                    <br />
                    <button type="submit" name="place_order" class="gold_btn">Place Order</button>
                </form>
            </div>
        </div>
	</body>
</html>