<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Product - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
	</head>
	<body>
		<%@ page import="controller.Product" %>
		<%@ page import="controller.AuthDAO" %>
        <%@include file="top_menu.jsp"%>
        <br />
        
        
        <%
       // int productID = request.getParameter("productID");
       		Product new_prd = AuthDAO.getProductById(11); //Hardcoded productID of the RazorBlade laptop
	      String pName = new_prd.GetProductName();
	      String pDescription = new_prd.GetDescription();
	      String pSpecs = new_prd.GetSpecs();
	      float pPrice = new_prd.GetPrice();
	      int pQuantity = new_prd.GetNumInStock();
	     
        %>
        
        <div id="page_content_wrapper">
        	<h3>Product Page</h3> <br/>
        	Product: <%=pName%>  <br/>
        	Description: <%=pDescription%>  <br/>
        	Specs: <%=pSpecs%>  <br/>
        	Quantity Available: <%=pQuantity%>  <br/>
        	Price: <%=pPrice%>  <br/>
        </div>
	</body>
</html>