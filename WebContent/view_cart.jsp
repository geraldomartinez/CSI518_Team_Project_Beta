<%@ page import="controller.AuthDAO" %>
<%@ page import="controller.Product" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>View Cart - Great Danes Electronics</title>
		
		<!-- jQuery Library -->
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
		
		<style type="text/css">
			#page_content_wrapper {
				text-align: center;
			}
			h3{
        		background: #2C193B;
				padding-top: 5px;
				padding-bottom: 5px;
			}
			#cart_table{
				width: 100%;
				border-collapse: collapse;
			}
			#cart_table th{
				border-bottom: 1px solid white;
				padding-bottom: 5px;
			}
			#cart_table td{
				padding-top: 5px;
			}
			#cart_table td a{
				color: white;
			}
			.quantity{
				width: 50px;
				text-align: right;
			}
			#remove_all_form button{
				font-size: 16px;
				vertical-align: middle;
				cursor: pointer;
			}
			#remove_all_form button:hover{
				color: red;
			}
			#remove_all_form button img{
				height: 16px;
				vertical-align: middle;
			}
			#total_table{
				display: inline-block;
				border-collapse: collapse;
			}
			#total_table td:nth-child(1){
				text-align: left;
			}
			#total_table td:nth-child(2){
				text-align: right;
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function() { 
				$('input[name=shipping_method]').change(function() {
					$('#shipping_method_form').submit();
				});
			});
		</script>
	</head>
	<body style="width: 1000px;">
		<%@include file="top_menu.jsp"%>
		<br />
		<div id="page_content_wrapper">
			<%
				//If the user is not logged in, forward them to the login page
				//(note, this will only happen if the user deliberately accesses cart.jsp while logged in via the browser's address bar.)
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp"); //Setup the request dispatcher for the login page
	
				if (navLoggedIn == "true" && !acctType.equals("B")) {
					rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
					request.setAttribute("indexMessage", "Only buyers and guests are allowed to access cart features");
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
			<h3>Items</h3>
			<%
				CartItem tempItem;
				Product prod;
				List<CartItem> itemList = cart.GetAllItems();
				float productShippingCost = -1.0f;
				if (!itemList.isEmpty()){
			%>
				<table id="cart_table">
				<tr>
					<th colspan="2">Product</th>
					<th>Price</th>
					<th>Shipping Cost</th>
					<th>Quantity</th>
					<th>Remove Item</th>
					<th>Move To Wish List</th>
				</tr>
			<%
				for (int i = 0; i < itemList.size(); i++) {
					prod = AuthDAO.getProductById(itemList.get(i).GetProductID());
					out.print("<tr>");
						out.print("<td>");
							out.print("<a href='view_product.jsp?productID="+prod.GetProductID()+"'><img src='"+prod.getPicture()+"' style='max-width: 100px; max-height: 100px;'></a>");
						out.print("</td>");
						out.print("<td>");
							out.print("<a href='view_product.jsp?productID="+prod.GetProductID()+"'>"+prod.GetProductName()+"</a>");
						out.print("</td>");
						out.print("<td>");
							out.print("$"+String.format("%.2f", prod.GetPrice()));
						out.print("</td>");
						out.print("<td>");
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
							out.print("$"+String.format("%.2f", productShippingCost));
						out.print("</td>");
						out.print("<td>");
							out.print("<form id='update_qty_form' action='UpdateQuantityInCartServlet' method='POST'>");
								out.print("<input type='number' name='quantity' class='quantity' value='"+Integer.toString(itemList.get(i).GetQuantity())+"' />");
								out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
								out.print("<button type='submit'>Update</button>");
							out.print("</form>");
						out.print("</td>");
						out.print("<td>");
							out.print("<form id='update_qty_form' action='UpdateQuantityInCartServlet' method='POST'>");
								out.print("<input type='hidden' name='quantity' class='quantity' value='-1' />");
								out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
								out.print("<button type='submit'>Remove</button>");
							out.print("</form>");
						out.print("</td>");
						out.print("<td>");
							out.print("<form id='update_qty_form' action='Add2WishlistServlet' method='POST'>");
								out.print("<input type='hidden' name='quantity' class='quantity' value='"+Integer.toString(itemList.get(i).GetQuantity())+"' />");
								out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
								out.print("<input type='hidden' name='delFromCart' value='true' />");
								out.print("<button type='submit' name=wish >Move to Wish List</button>");
							out.print("</form>");
						out.print("</td>");
					out.print("</tr>");
					
					}
			%>
				</table>
				<br />
				<br />
				<form class="message" action="UpdateCartShippingMethodServlet" method="POST" id="shipping_method_form">
					<h4>Shipping Method:</h4>
					<div style="text-align: left; width: 200px;">
						<input type="radio" value="1" name="shipping_method" <%= ((cart.GetShippingMethod() == 1)? "checked='checked'":"") %> /> Ground Shipping
						<br />
						<input type="radio" value="2" name="shipping_method" <%= ((cart.GetShippingMethod() == 2)? "checked='checked'":"") %> /> Two-Day Shipping
						<br />
						<input type="radio" value="3" name="shipping_method" <%= ((cart.GetShippingMethod() == 3)? "checked='checked'":"") %> /> Next-Day Shipping
						<br />
						<br />
					</div>
				</form>
				<br />
				<br />
				<form id="remove_all_form" action="RemoveAllItemsInCartServlet" method="POST" onsubmit="return confirm('Are you sure you want to remove all items from your cart?')">
					<button type="submit"><img src="img/trash.png" alt="trashcan"/> Remove All Items</button>
				</form>
				<br />
				<h3>Cost</h3>
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
				<a href="checkout.jsp"><img src="img/paypal_button.gif" alt="Checkout with PayPal" /></a>
			<%
				}else{
					out.print("<h3>No items in cart</h3>");
				}
			%>
		</div>
	</body>
</html>