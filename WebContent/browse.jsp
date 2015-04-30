<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Browse - Great Danes Electronics</title>
	<style type="text/css">
		#categoryMenu{
			width: 100%;
			border: none;
   			border-collapse: collapse;
		}
		#categoryMenu td{
			text-align: center;
			background: #000;
			height: 30px;
		}
		#categoryMenu td a{
			color: white;
		}
		#categoryMenu td.selected{
			background: #CCC;
		}
		#categoryMenu td.selected a{
			color: black;
			text-decoration: none;
		}
	</style>
</head>
<body>
	<%@ page
		import="controller.AuthDAO,controller.Utilities,controller.Product,java.util.*, java.sql.*"%>
	<%@include file="top_menu.jsp"%>



	<div id="page_content_wrapper">
		<table id="categoryMenu">
			<tr>
				<%
					Connection conn = null;
					ResultSet rs = null;
					int categoryID;
					String categoryName = "";
					int iterator = 0;
					int tracker = 0;
					HashMap<Integer, String> list = new HashMap<Integer, String>();
					
					String strCategoryID = request.getParameter("categoryID");
					if (strCategoryID == null) {
						strCategoryID = "";
					}

					try {
						conn = AuthDAO.createConn();
						HttpSession ss = request.getSession();

						PreparedStatement pst = conn
								.prepareStatement("SELECT * FROM `ProductCategories` ORDER BY `categoryName` ASC");
						rs = pst.executeQuery();
						while (rs.next()) {
							categoryID = rs.getInt("categoryID");
							categoryName = rs.getString("categoryName");
							list.put(categoryID, categoryName);
					
				%>

				<td <% if(!strCategoryID.equals("") && Integer.parseInt(strCategoryID) == categoryID){ out.print("class='selected'"); } %>>
					<a href="browse.jsp?categoryID=<%=Utilities.getMapKeyValue(list, list.get(categoryID)) %>"><%=list.get(categoryID)%></a>
				</td>
				<%
					}
						//conn.close();
					} catch (Exception e) {
						out.print(e);
					}
				%>
			</tr>
		</table>
		<br />
		<form action="search.jsp" method="get">
		<input type="text" placeholder="Search Great Danes Electronics" name="results" />
		<input type="submit" value="submit" />
		</form>
		<hr />
		<br />
		<%
			if (strCategoryID == "") {
		%>
		<h3 style="text-align: center;">Select a product category above to start browsing products</h3>

		<%
			}

			else {
				categoryID = Integer.parseInt(strCategoryID);
				String current_category = list.get(categoryID);
		%>

		<h3><%=current_category%></h3>
		<br />

		<%
			
			String productName = "";
				String pDescription = "";
				String pSpecs = "";
				float pPrice = 0;
				int productID = 0;
				String picture = "";
				Product prod = null;

				try {			
					String sql = "SELECT * 	FROM  `Products` WHERE categoryID ='"
							+ categoryID + "' AND removed = 0 order by productName;";
					//conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
					
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						productID = rs.getInt("productID");
						prod = AuthDAO.getProductById(productID);
						
						productName = rs.getString("productName");
						pDescription = rs.getString("description");
						pPrice = rs.getFloat("unitPrice");
						pSpecs = rs.getString("specs");
						picture = prod.getPicture();
						
						
		%>
		
		<a href="view_product.jsp?productID=<%=productID %>" style="color: white;"><%=productName%></a>
		<br>
		<br>
		<div ><a href="view_product.jsp?productID=<%=productID %>"><img src="<%=picture%>" style="max-width: 300px; max-height: 300px;"></a></div>
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
			<li>Description: <%=pDescription%></li>
			<li>Price: <%=pPrice%></li>
			<li>Specs: <%=pSpecs%></li>
		</ul>
		<br>
		<br>
		<br>
		<br>
		
		<%
			}
					conn.close();
				} catch (Exception e) {
					out.print(e);
				}
			}
		%>
	</div>


</body>
</html>
