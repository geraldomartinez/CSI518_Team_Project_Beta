<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Notifications - Great Danes Electronics</title>
</head>
<body>
<%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        <%
        	 String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
            if (loggedIn == null) { //Prevent null pointer exception
                loggedIn = "false";
            }

            if (loggedIn == "false") { //If the user is logged in
                //Alert the user that they are already logged in
            	out.println("<font color=red>You must log in to view your account!</font>");
            }else {%>
        <h2>My Notifications</h2>
        
        <%} %>
        </div>

</body>
</html>