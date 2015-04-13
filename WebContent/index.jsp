<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
       	<script type="text/javascript" src="js/jquery.cycle2.min.js"></script> <!-- Cycle2 Plug-in -->
       	<script type="text/javascript" src="js/jquery.cycle2.tile.js"></script> <!-- Cycle2 Plug-in -->
		<script type="text/javascript">
			$(document).ready(function(){
				$("#sales img").click(function(){
					alert($(this).data("productid"));
				});
			});
		</script>
		<style type="text/css">
			h1{
				text-align: center;
			}
			#sales{
				cursor: pointer;
			}
			#footer{
				text-align: center;
				font-size: 10px;
			}
		</style>
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
        	<br />
        	<h1>Great Danes Electronics</h1>
        	<br />
        	<br />
        	<div class="cycle-slideshow" id="sales" data-cycle-fx="tileSlide">
			    <img src="img/specials/1.jpg" data-productid="1">
			    <img src="img/specials/2.jpg" data-productid="2">
			    <img src="img/specials/3.jpg" data-productid="3">
			</div>
			<br />
			<br />
			<hr />
			<div id="footer">
				Copyright 2015 Great Danes Electronics, All rights reserved.
				&nbsp;&nbsp;&nbsp;
				Great Danes Electronics, LLC
				&nbsp;&nbsp;&nbsp;
				(555) 555-5555
				&nbsp;&nbsp;&nbsp;
				1 UAlbany Way, Albany, NY, 12205
			</div>
        </div>
	</body>
</html>