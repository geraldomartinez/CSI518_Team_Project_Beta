
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Product - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js">
       	
    /*   	function validate()
    	{
    	if(document.add_product.price.value !=="")
        {
            if (! (/^\d*(?:\.\d{0,2})?$/.test(document.add_product.price.value))) { 
                alert("Please enter a valid Breed id"); 
                document.add_product.price.focus(); 
                return false; 
            } 


        }
    	}*/
       	
       	</script> <!-- jQuery Library -->
	</head>
	
	<!-- 
	<style type="text/css">
	
            #page_content_wrapper{
               position: fixed; /* or absolute 
               margin-left:auto;
               margin-right:auto; */
                width: 100%;
                height: 70%;
                top: 15%;  /* center object vertically */
                text:color:White; /* */
                text-align: center; 
                display: none;
            }
             
           
        </style>
         -->
	<body >
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        	<%
                String addProductMessage = (String) request.getAttribute("addProductMessage"); //Obtain the message from the session (if there is one)
                if (addProductMessage == null) { //Prevent null pointer exception
                	addProductMessage = "";
                }
            %>
            <div id="add_product_message" class="message"><%=addProductMessage%></div>
        	<br />
        	Add Product Page
        	<form id="add_product" name="add_product" action="ProductServlet" method="POST">
        	<table >
                   <tr> <td>Category ID </td><td><input name="categoryid" type="text" >
                    </td></tr>
                    <tr></tr>
                  <tr> <td>Product Name </td><td><input name="productname" type="text" >
                    </td></tr>
                    <tr></tr>
                   <tr><td> Description</td><td><input name="description" type="text" >
                   </td></tr>
                    <tr></tr> 
                  <tr><td> Specs</td><td> <input name="specs" type="text" >
                    </td></tr>
                     <tr></tr>
                   <tr><td>Price</td><td> <input name="price" type="text">
                    </td></tr>
                     <tr></tr>
                    <tr><td>Quantity</td><td><input name="numinstock" type="text" >
                    </td></tr>
                     <tr></tr>
                     <tr><td></td><td><input type="submit" value="submit"  name="insertbt"></td></tr>
        	</table>
                 	
                </form>    
        </div>
	</body>
</html>