<%@ page import="controller.AuthDAO" %>
<%@ page import="controller.Product" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>View Wish List - Great Danes Electronics</title>
		
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
			#wishlist_table{
				width: 100%;
				border-collapse: collapse;
			}
			#wishlist_table th{
				border-bottom: 1px solid white;
			}
			#wishlist_table td{
				padding-bottom: 5px;
			}
			#wishlist_table td a{
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
				padding-left: 20px;
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
				//(note, this will only happen if the user deliberately accesses wishlist.jsp while logged in via the browser's address bar.)
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp"); //Setup the request dispatcher for the login page
	
				if (navLoggedIn != "true" || !acctType.equals("B")) {
					rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
					request.setAttribute("indexMessage", "Only buyers are allowed to access wishlist features");
					rd.forward(request, response); //Forward the user with the response above
				}
			%>
			<h1 id="sub_header">Wish List</h1>
			<%
				String wishlistMessage = (String) request.getAttribute("wishListMessage"); //Obtain the wishlist message from the session
				if (wishlistMessage == null) { //Prevent null pointer exception
					wishlistMessage = "";
				}
			%>
			<div id="wishlist_message" class="message"><%=wishlistMessage%></div>
			<br />
			<h3>Items</h3>
			<%
				CartItem tempItem;
				Product prod;
				List<CartItem> itemList = wishlist.GetAllItems();
				float productShippingCost = -1.0f;
				if (!itemList.isEmpty()){
			%>
				<table id="wishlist_table">
				<tr>
					<th colspan="2">Product</th>
					<th>Price</th>
					<th>Shipping Cost</th>
					<th>Quantity</th>
					<th>Remove</th>
					<th>Move to Cart</th>
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
						switch(wishlist.GetShippingMethod()){
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
						out.print("<form id='update_qty_form' action='UpdateQuantityInWishListServlet' method='POST'>");
							out.print("<input type='number' name='quantity' class='quantity' value='"+Integer.toString(itemList.get(i).GetQuantity())+"' />");
							out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
							out.print("<button type='submit'>Update</button>");
						out.print("</form>");
					out.print("</td>");
					out.print("<td>");
						out.print("<form id='update_qty_form' action='UpdateQuantityInWishListServlet' method='POST'>");
							out.print("<input type='hidden' name='quantity' class='quantity' value='-1' />");
							out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
							out.print("<button type='submit'>Remove</button>");
						out.print("</form>");
					out.print("</td>");
					out.print("<td>");
						out.print("<form id='update_qty_form'  action='UpdateQuantityInWishListServlet' method='POST'>");
							out.print("<input type='hidden' name='quantity' class='quantity' value='-1' />");
							out.print("<input type='hidden' name='newquantity' value='"+Integer.toString(itemList.get(i).GetQuantity())+"' />");
							out.print("<input type='hidden' name='productID' value='"+Integer.toString(prod.GetProductID())+"' />");
							out.print("<input type='hidden' name='delFromWish' value='true' />");
							out.print("<button type='submit'>Move to cart</button>");
						out.print("</form>");
					out.print("</td>");
					out.print("</tr>");
					}
			%>
				</table>
				<br />
				<br />
				<form class="message" action="UpdateWishListShippingMethodServlet" method="POST" id="shipping_method_form">
					<h4>Shipping Method:</h4>
					<div style="text-align: left; width: 200px;">
						<input type="radio" value="1" name="shipping_method" <%= ((wishlist.GetShippingMethod() == 1)? "checked='checked'":"") %> /> Ground Shipping
						<br />
						<input type="radio" value="2" name="shipping_method" <%= ((wishlist.GetShippingMethod() == 2)? "checked='checked'":"") %> /> Two-Day Shipping
						<br />
						<input type="radio" value="3" name="shipping_method" <%= ((wishlist.GetShippingMethod() == 3)? "checked='checked'":"") %> /> Next-Day Shipping
						<br />
						<br />
					</div>
				</form>
				<br />
				<form id="remove_all_form" action="RemoveAllItemsInWishListServlet" method="POST" onsubmit="return confirm('Are you sure you want to remove all items from your wish list?')">
					<button type="submit"><img src="img/trash.png" alt="trashcan" style="height: 16px;" /> Remove All Items</button>
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
							<%= "$"+String.format("%.2f", wishlist.GetCost()) %>
						</td>
					</tr>
					<tr>
						<td>
							Tax:
						</td>
						<td>
							<%= "$"+String.format("%.2f", wishlist.GetShippingCost()) %>
						</td>
					</tr>
					<tr>
						<td>
							Tax:
						</td>
						<td>
							<%= "$"+String.format("%.2f", wishlist.GetTax()) %>
						</td>
					</tr>
					<tr>
						<td style="border-top: 1px solid white;">
							Total:
						</td>
						<td style="border-top: 1px solid white;">
							<%= "$"+String.format("%.2f", wishlist.GetTotal()) %>
						</td>
					</tr>
				</table>
				<br />
				<br />
				
			<%
				}else{
					out.print("<h3>No items in wish list</h3>");
				}
			%>
		</div>
	</body>
</html>
