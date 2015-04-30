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
		
<form id="survey" name="survey" action="SurveyServlet" method="POST">
			<%
			
			

			String Btn = request.getParameter("recommendButton");
			String color=request.getParameter("color");
			System.out.println(color);
			Product prd=AuthDAO.getProductByColor(color);
			String productName = prd.GetProductName();
			System.out.println(productName);
			String Description = prd.GetDescription();
					Float unitPrice = prd.GetPrice();
				String specs = prd.GetSpecs();
				int productID=prd.GetProductID();
					
					//String picture = prd.getPicture();
					
		
			%>
			
		
		<br />
		<%
        	ArrayList<Integer> RatingAndCount = AuthDAO.getProductAverageRating(productID);
        	if(!RatingAndCount.isEmpty()){
	      	int avgRating = RatingAndCount.get(1);
	      	int numberOfReviewers = RatingAndCount.get(0);
				for(int i = 1; i<=avgRating; i++){
					%>
					<span style="color: yellow;">&#9733;</span> 
					<%
				}
				
				 %>
				(<%=numberOfReviewers %> Reviews) <%} 
        	else{
        		for(int i = 1; i<=5; i++){
					%>
					<span style="color: yellow;">&#9734;</span> 
					<%
				}
        		%>(No Reviews)<%
        	}
				
				
				%>
		
			<ul>
			<li>Description: <%=Description%></li>
			<li>Price: $<%=unitPrice%></li>
			<li>Specs: <%=specs%></li>
		</ul>
			</form>
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

