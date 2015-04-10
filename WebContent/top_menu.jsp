<%
	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
	
	if (navLoggedIn == null) { //Prevent null pointer exception
	    navLoggedIn = "";
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
				<a href="#">Browse</a>
			</td>
			<td>
				<a href="view_product_list.jsp">View Product List</a>
			</td>
			<td>
				<a href="add_product.jsp">Add Product</a>
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
			<%
			    }
			%>
		</tr>
	</table>
</div>