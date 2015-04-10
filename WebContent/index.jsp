<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
	        <%
	            String indexMessage = (String) request.getAttribute("indexMessage"); //Obtain the message to be displayed for the index page (if there is one)
	            if (indexMessage == null) { //Prevent null pointer exception
	            	indexMessage = "";
	            }
	        %>
        	<div id="index_message" class="message"><%=indexMessage%></div>
        	Home Page
        </div>
	</body>
</html>