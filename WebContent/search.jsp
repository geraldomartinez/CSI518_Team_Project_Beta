<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
	<%@include file="top_menu.jsp"%>
	
	<div id="page_content_wrapper">
	
<%
String results = request.getParameter("results");
%>


<form action="search.jsp" method="get" style="float:right;">
		<input type="text" placeholder="Search again" name="results" />
		<input type="submit" value="submit" />
		</form>

<h3>"<%=results %>" - Search Results </h3>
__________________________________________
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

				try {			
					String sql = "SELECT * FROM Products WHERE productName LIKE '%" +results+"%' OR specs LIKE '%" +results+"%' OR description LIKE '%" +results+"%';";
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
					    picture=rs.getString("picture");
					 
						
		%>
		
		<a href="view_product.jsp?productID=<%=productID %>" style="color: white;"><%=productName%></a>
		<br>
		<br>
		<div ><img src="img/<%=picture%>" height=200 width=200></div>
		<ul>
			<li>Description: <%=pDescription%></li>
			<li>Price: <%=pPrice%></li>
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