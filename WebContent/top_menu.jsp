<%@ page import="java.util.*" %>
<%@ page import="controller.User" %>
<%@ page import="controller.CartItem" %>
<%@ page import="controller.Cart" %>
<%@ page import="controller.WishList" %>
<%
	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
	Cart cart = (Cart) session.getAttribute("cart"); //Get the cart from the session
	WishList wishlist = (WishList) session.getAttribute("wishlist"); //Get the wishlist from the session
	
	String acctType = "";
	if (usr != null){
		acctType = usr.getAccountType();
	}
	
	if (navLoggedIn == null) { //Prevent null pointer exception
	    navLoggedIn = "";
	}
	if (acctType == null) { //Prevent null pointer exception
		acctType = "";
	}
%>
<link rel="stylesheet" type="text/css" href="css/global.css" /> <!-- Global style sheet -->
<div id="menu_wrapper">
	<table>
		<tr>
			<td>
				<a href="index.jsp">
					<img id="great_dane_logo" src="img/great_dane_logo.png" />
				</a>
			</td>
			<td>
				<a href="index.jsp">Home</a>
			</td>
			<td>
				<a href="browse.jsp">Browse</a>
			</td>
			<%
			    if (navLoggedIn != "true") { //If the user is not logged in
			%>
			<td>
				<a href="signup.jsp">Register</a>
			</td>
			<td>
				<a href="login.jsp">Login</a>
			</td>
			<%
			    } else { //The user is logged in

			%>
				<td>
					<a href="LogoutServlet">Logout</a>
				</td>
				<td>
					<a href="my_account.jsp">
						<img src="img/user_icon.png" alt="profile icon" style="height: 20px; position: relative; top: -2px;" /> 
						&nbsp;
						My Account
					</a>
				</td>
			<%				
					if (acctType.equals("B")){
						wishlist.UpdateCostAndShippingCost();
				%>
					<td>
						<a href="view_wishlist.jsp">
							<img src="img/gold_star.png" alt="gold star" style="height: 20px; position: relative; top: -2px;" /> 
							&nbsp;
							My Wish List
						</a>
					</td>
				<%
			    	}
			    }
					
				if (navLoggedIn != "true" || (navLoggedIn == "true" && acctType.equals("B"))){
					if (cart == null){
						cart = new Cart();
						session.setAttribute("cart",cart);
					}
					cart.UpdateCostAndShippingCost();
			%>
					<td>
						<a href="view_cart.jsp">
							<img src="img/cart.png" alt="cart" style="height: 20px; position: relative; top: -2px;" /> 
							&nbsp;
							<%= cart.NumItems() %> item<%= (cart.NumItems() == 1) ? "" :"s" %> ($<%= String.format("%.2f", cart.GetCost()) %>)
						</a>
					</td>
			<%
			    }
			%>
		</tr>
	</table>
</div>