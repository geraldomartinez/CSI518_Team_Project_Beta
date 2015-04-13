<%@ page import="controller.User" %>
<%
	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
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
				<a href="#">Browse</a>
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
			%>
				<td>
					<a href="#">
						<img src="img/cart.png" alt="cart" style="height: 20px; position: relative; top: -2px;" /> 
						&nbsp;
						0 items ($0.00)
					</a>
				</td>
			<%
					}
			    }
			%>
		</tr>
	</table>
</div>