
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import='java.sql.*'
	import='controller.AuthDAO'%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Product - Great Danes Electronics</title>

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
			String addProductMessage = (String) request.getAttribute("addProductMessage"); //Obtain the message from the session (if there is one)
			String strProductID = request.getParameter("productID");
			
			if (addProductMessage == null) { //Prevent null pointer exception
				addProductMessage = "";
			}
			
	        if (loggedIn == null) { //Prevent null pointer exception
	            loggedIn = "false";
	        }
	        
        	if (strProductID == null){
        		strProductID = "";
        	}
	
	        if (loggedIn != "true") {
	            request.setAttribute("indexMessage", "Please log into your seller account before attempting to add a product");
	            rd.forward(request, response); //Forward the user with the response above
	        }else if (strProductID == ""){
	        	rd = request.getRequestDispatcher("view_product_list.jsp"); //Setup the request dispatcher for the view productl ist page
	            request.setAttribute("productListMessage", "No product ID given");
	            rd.forward(request, response); //Forward the user with the response above
	        }
		%>
		<div id="add_product_message" class="message"><%=addProductMessage%></div>
		<br />
		<h1 id="header">Add A Product</h1>
		<form id="add_product" name="add_product" action="ProductServlet" method="POST" enctype="multipart/form-data">
			<table>
				<tr>
					<td>Product Category:</td>
					<td><select id=categoryID name=categoryID>
							<%
								Connection conn = null;
								ResultSet rs = null;
								int categoryID;
								String categoryName = "";

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `ProductCategories`");
									rs = pst.executeQuery();
									while (rs.next()) {
										categoryID = rs.getInt("categoryID");
										categoryName = rs.getString("categoryName");
							%>
							<option value="<%=categoryID%>" selected><%=categoryName%></option>
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
					<td><input name="productname" type="text" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><textarea  name="description"></textarea></td>
				</tr>
				<tr>
					<td>Specs:</td>
					<td><textarea name="specs"></textarea></td>
				</tr>
				<tr>
					<td>Price:</td>
					<td><input name="price" type="number" step="any"></td>
				</tr>
				<tr>
					<td>Ground (3-5 day) Shipping Cost:</td>
					<td><input name="ground_cost" type="number" ></td>
				</tr>
				<tr>
					<td>Two-Day Shipping Cost:</td>
					<td><input name="two_cost" type="number" ></td>
				</tr>
				<tr>
					<td>Next-Day Shipping Cost:</td>
					<td><input name="next_cost" type="number" ></td>
				</tr>
				<tr>
					<td>Quantity Available:</td>
					<td><input name="numinstock" type="number" ></td>
				</tr>
				<tr>
					<td>Product Image:</td>
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