<%@ page import="controller.AuthDAO" %>
<%@ page import="controller.Product" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>View Cart - Great Danes Electronics</title>
		
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
		<!-- jQuery Library -->
		<style type="text/css">
		#page_content_wrapper {
			text-align: center;
		}
		#cart_table{
			width: 100%;
			border-collapse: collapse;
		}
		#cart_table td{
			border: 1px solid white;
		}
		#cart_table td a{
			color: white;
		}
		.quantity{
			width: 50px;
			text-align: right;
		}
		</style>
	</head>
	<body>
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
			<%
				CartItem tempItem;
				Product prod;
				List<CartItem> itemList = cart.GetAllItems();
				if (!itemList.isEmpty()){
			%>
				<table id="cart_table">
				<tr>
					<th>Product Name</th>
					<th>Price</th>
					<th>Shipping Cost</th>
					<th>Quantity</th>
				</tr>
			<%
				for (int i = 0; i < itemList.size(); i++) {
					prod = AuthDAO.getProductById(itemList.get(i).GetProductID());
					out.print("<tr>");
					out.print("<td>");
						out.print("<a href='view_product.jsp?productID="+prod.GetProductID()+"'>"+prod.GetProductName()+"</a>");
					out.print("</td>");
					out.print("<td>");
						out.print("$"+String.format("%.2f", prod.GetPrice()));
					out.print("</td>");
					out.print("<td>");
						out.print("$"+String.format("%.2f", prod.GetShippingCost()));
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
					out.print("<input type='hidden' name='quantity' class='quantity' value='-1' />");
					out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
						out.print("<button type='submit' name=wish >Add to WishList</button>");
					out.print("</form>");
				out.print("</td>");
					out.print("</tr>");
					
					}
			%>
				</table>
				<br />
				<br />
				<br />
				<table>
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
						<td style="border-top: 1px solid white;">
							Total:
						</td>
						<td style="border-top: 1px solid white;">
							<%= "$"+String.format("%.2f", cart.GetTotal()) %>
						</td>
					</tr>
				</table>
				<br />
				<br />
				<form id="remove_all_form" action="RemoveAllItemsInCartServlet" method="POST" onsubmit="return confirm('Are you sure you want to remove all items from your cart?')">
					<button type="submit">Remove All Items</button>
				</form>
				
			<%
				}else{
					out.print("<h3>No items in cart</h3>");
				}
			%>
		</div>
	</body>
</html>