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
					window.location.href = "view_product.jsp?productID="+$(this).data("productid");
				});
			});
		</script>
		<style type="text/css">
			h1{
				text-align: center;
			}
			
			#banner {
			  	position: relative;
			  	margin-left: -20px;
			  	text-align: center;
			  	padding: 10px;
			  	color: white;
			  	text-shadow: 2px 2px 0 black;
			  	width: 800px;
	
				/*Box Shadow*/
				-webkit-box-shadow: 0px 0px 1px 2px rgba(0,0,0,0.42);
				-moz-box-shadow: 0px 0px 1px 2px rgba(0,0,0,0.42);
				box-shadow: 0px 0px 1px 2px rgba(0,0,0,0.42);
			  
			  	background: repeating-linear-gradient(
			  		45deg,
			  		rgba(234,170,0,.55),
			  		rgba(234,170,0,.55) 20px,
			  		#3B067D 20px,
			  		#3B067D 40px
				);
			}
			
			#banner:before, #banner:after {
			  content: '';
			  border-top: 10px solid #2D0261;
			  position: absolute;
			  bottom: -10px;
			}
			
			#banner:before {
			    border-left: 10px solid transparent;
			    left: 0;
			}
			
			#banner:after {
			    border-right: 10px solid transparent;
			    right: 0;
			}
			
			#banner img {
			    width: 780px;
			}
			
			#sales{
				cursor: pointer;
				width: 775px;
				margin-left: 10px;
			}
			
			#footer{
				text-align: center;
				font-size: 10px;
			}
		</style>
	</head>
	<body>
	<%@ page
		import="controller.AuthDAO,controller.Utilities,controller.Product,java.util.*, java.sql.*"%>
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
        	<h1>Great Danes Electronics</h1>
        	<div id="banner">
	        	<div class="cycle-slideshow" id="sales" data-cycle-fx="tileSlide" data-cycle-pause-on-hover="true">
				    <img src="img/specials/1.jpg" data-productid="11">
				    <img src="img/specials/2.jpg" data-productid="33">
				    <img src="img/specials/3.jpg" data-productid="34">
				</div>
			</div>
			<br />
			<div id="Recommended Products" class="message">These are the Recommended Products
		<br />
		<h1 id="header">Survey</h1>

			<%
			
			Product[] productsArr = AuthDAO.getRecommendedProducts(User.GetUserID());
		System.out.println(productsArr);
		//display Products
			%>
	
			</div>
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

