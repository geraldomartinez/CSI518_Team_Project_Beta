
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import='java.sql.*'
	import='controller.AuthDAO,controller.Product'%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Product - Great Danes Electronics</title>

<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
<!-- jQuery Library -->

<style type="text/css">	
	#page_content_wrapper {
		text-align: center;
	}
	
	#add_product table {
		color: white;
		display: inline-block;
		margin-left: auto;
		margin-right: auto;
	}
	
	#add_product td {
		text-align: right;
		padding-bottom: 10px;
	}
	
	#add_product input, #add_product select, #add_product textarea{
		width: 200px;
		margin-left: 10px;
	}
	
	#add_product textarea{
		height: 50px;	
	}
</style>
</head>
<body>
	<%@include file="top_menu.jsp"%>
	<br />
	<div id="page_content_wrapper">
		<%
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
	        String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
			String editProductMessage = (String) request.getAttribute("editProductMessage"); //Obtain the message from the session (if there is one)
	        String productID = (String) request.getParameter("productID"); //Get the "logged in" attribute from the session
	        Product prd = null;
			
			if (editProductMessage == null) { //Prevent null pointer exception
				editProductMessage = "";
			}
			
	        if (loggedIn == null) { //Prevent null pointer exception
	            loggedIn = "false";
	        }
	        
	        try{
	        	prd = AuthDAO.getProductById(Integer.parseInt(productID));
	        }catch (Exception ex){
	        	rd = request.getRequestDispatcher("view_product_list.jsp"); //Setup the request dispatcher for the index page
	            request.setAttribute("productListMessage", "An error has occured while trying to look up the product: "+ex);
	            rd.forward(request, response); //Forward the user with the response above
	        }
	
	        if (loggedIn != "true") { //If the user is logged in
	            //Alert the user that they are already logged in
	            request.setAttribute("indexMessage", "Please log into your seller account before attempting to add a product");
	            rd.forward(request, response); //Forward the user with the response above
	        }else if(prd == null || prd.GetSellerID() != usr.GetUserID()){
	        	
	        }
		%>
		<div id="edit_product_message" class="message"><%=editProductMessage%></div>
		<br />
		<h1 id="header">Edit Product</h1>
		<br />
		<img src="<%= prd.getPicture() %>" alt="Product Image" style="max-width: 200px;  max-height: 200px;" />
		<br />
		<a href="view_product.jsp?productID="+<%=productID%> style="color: white;">Click here to view current product page</a>
		<br />
		<br />
		<br />
		<form id="add_product" name="add_product" action="ProductServlet" method="POST" enctype="multipart/form-data">
			<table>
				<tr>
					<td>Product ID:</td>
					<td><%=productID%><input type='hidden' name='productID' value='<%=productID%>'/></td>
				</tr>
				<tr>
					<td>Product Category:</td>
					<td><select id=categoryID name=categoryID>
							<%
								Connection conn = null;
								ResultSet rs = null;
								int categoryID;
								String categoryName = "", selected = "";

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `ProductCategories`");
									rs = pst.executeQuery();
									while (rs.next()) {
										categoryID = rs.getInt("categoryID");
										categoryName = rs.getString("categoryName");
										
										selected = "";
										if (categoryID == prd.GetCategoryID()){
											selected = "selected";
										}
							%>
							<option value="<%=categoryID%>" <%=selected%>><%=categoryName%></option>
							<%
								}
									conn.close();
								} catch (Exception e) {
									out.print(e);
								}
							%>
					</select>
				</tr>
				<tr>
					<td>Product Name:</td>
					<td><input name="productname" type="text" value="<%=prd.GetProductName() %>" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><textarea  name="description"><%=prd.GetDescription() %></textarea></td>
				</tr>
				<tr>
					<td>Specs:</td>
					<td><textarea name="specs"><%=prd.GetSpecs() %></textarea></td>
				</tr>
				<tr>
					<td>Price:</td>
					<td><input name="price" type="number" step="any" value="<%=prd.GetPrice() %>"></td>
				</tr>
				<tr>
					<td>Ground (3-5 day) Shipping Cost:</td>
					<td><input name="ground_cost" type="number" value="<%=prd.GetGroundShippingCost() %>"></td>
				</tr>
				<tr>
					<td>Two-Day Shipping Cost:</td>
					<td><input name="two_cost" type="number" value="<%=prd.GetTwoDayShippingCost() %>"></td>
				</tr>
				<tr>
					<td>Next-Day Shipping Cost:</td>
					<td><input name="next_cost" type="number" value="<%=prd.GetNextDayShippingCost() %>" ></td>
				</tr>
				<tr>
					<td>Quantity Available:</td>
					<td><input name="numinstock" type="number" value="<%=prd.GetNumInStock() %>" ></td>
				</tr>
				<tr>
					<td>New Product Image:</td>
					<td><input type="file" id="product_image" name="product_image"></td>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" name="insertbt"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>