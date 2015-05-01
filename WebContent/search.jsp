<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<style type="text/css">
	h3{
       	background: rgba(44,25,59,0.5);
		padding-top: 5px;
		padding-bottom: 5px;
		text-align: center;
	}
	h4{
      		background: #2C193B;
		padding: 5px;
	}
</style>
</head>
<body>
<%@ page
		import="controller.AuthDAO,controller.Utilities,controller.Product,java.util.*, java.sql.*"%>
	<%@include file="top_menu.jsp"%>
	
	<div id="page_content_wrapper">
	
<%
String results = request.getParameter("results");
%>


<form action="search.jsp" method="get" style="text-align: center;">
	<input type="text" placeholder="Search Great Danes Electronics" name="results" style="width: 200px; padding: 5px 10px;" />
	&nbsp;
	<input type="submit" value="Search" class="gold_btn" style="padding: 5px 10px;" />
</form>

<h3>"<%=results %>" - Search Results </h3>
<br>

<%
	if(results == null){
		%> <h4>You did not enter a query</h4>
		<%
	} else {
			String productName = "";
				String pDescription = "";
				String pSpecs = "";
				float pPrice = 0;
				int productID = 0;
				String picture="";
				Connection conn = null;
				ResultSet rs = null;
				Product prod = null;

				try {			
					String sql = "SELECT * FROM Products WHERE removed = 0 AND (productName LIKE '%" +results+"%' OR specs LIKE '%" +results+"%' OR description LIKE '%" +results+"%');";
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
					
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						productName = rs.getString("productName");
						pDescription = rs.getString("description");
						pPrice = rs.getFloat("unitPrice");
						pSpecs = rs.getString("specs");
						productID = rs.getInt("productID");
					    prod = AuthDAO.getProductById(productID);
						picture = prod.getPicture();
						String floatConv = String.format("%.2f", pPrice);
					 
						
		%>
		
		<h4><a href="view_product.jsp?productID=<%=productID %>" style="color: white;"><%=productName%></a></h4>
		<div ><a href="view_product.jsp?productID=<%=productID %>"><img src="<%=picture%>" style="max-width: 300px; max-height: 300px;"></a></div>
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
			<li>Description: <%=pDescription%></li>
			<li>Price: $<%=floatConv%></li>
			<li>Specs: <%=pSpecs%></li>
		</ul>
		
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