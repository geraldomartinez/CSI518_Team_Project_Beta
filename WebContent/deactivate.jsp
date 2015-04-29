<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#page_content_wrapper {
	text-align: center;
}

#page_content_wrapper a {
	color: white;
}
</style>
</head>
<body>
	<%@include file="top_menu.jsp"%>
	<br />
	<div id="page_content_wrapper">

		<div id="signup_form_wrapper">
			<%
				String deactivatemessage = (String) request.getAttribute("deactivatemessage"); //Obtain the message to be displayed for the register page (if there is one)
				if (deactivatemessage == null) { //Prevent null pointer exception
					deactivatemessage = "";
				}
			%>
			<div id="register_message" class="message"><%=deactivatemessage%></div>
			<br />
			<form id="deactivate" action="DeactivateServlet" method="post">
				Are you sure you want to deactivate your account? 
				<br /> 
				<br /> 
				<input type="submit" value="Yes, I'm sure - Deactivate my account" name="deactivate">
			</form>
		</div>
	</div>
</body>
</html>