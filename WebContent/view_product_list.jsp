<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Products</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        <link rel="stylesheet" type="text/css" href="css/global.css" /> <!-- Global style sheet -->
        
        <style type="text/css">
        	#product_list_table{
        		width: 100%;
        	}
        	
        	#product_list_table th{
        		background: #2C193B;
        		text-align: left;
        		padding: 5px;
        	}
        	
        	#product_list_table td:first-child{
        		padding-left: 10px;
        	}
        	
        	#add_product_button{
        		vertical-align: middle;
        		text-align: center;
        		width: 135px;
        		border: 1px solid green;
        		background: #9ABF97;
        		cursor: pointer;
        		font-weight: bold;
        		padding: 5px;
        	}
        	
        	#add_product_button:hover{
        		color: black;
        	}
        	
        	.green_plus{
        		vertical-align: middle;
        		height: 20px;
        		margin-top: -3px;
        	}
        	
        </style>
        
        <script type="text/javascript">
        	$(document).ready(function(){
            	$("#add_product_button").click(function(){
            		window.location.href = "add_product.jsp";
            	});
        	})
        </script>
       	
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        	<div>
        		<div id="add_product_button">
        			<img class="green_plus" src="img/green_plus.png" alt="green_plus" />
        			Add Product
        		</div>
        	</div>
        	<br />

        	<h2>Products Listing For [Seller Name]</h2>
        	<table id="product_list_table">
        		<tr>
        			<th colspan="4">
        				[Product Category #1]
        			</th>
        		</tr>
        		<tr>
        			<td>
        				[Image]
        			</td>
        			<td>
        				[Product #1]
        			</td>
        			<td>
        				<button>Edit</button>
        			</td>
        			<td>
        				<button>Delete</button>
        			</td>
        		</tr>
        	</table>
        </div>
	</body>
</html>