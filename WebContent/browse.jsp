<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse - Great Danes Electronics</title>
</head>
<body>
		<%@ page import="controller.AuthDAO,java.util.*, java.sql.*" %>
	 <%@include file="top_menu.jsp"%>

		
		
		<div id="page_content_wrapper">
	        	<h3 style="text-align: center;">Top 10 Rated Products</h3>
	        	<%
								Connection conn = null;
								ResultSet rs = null;
								int categoryID;
								String categoryName = "";
								int iterator;
								HashMap<Integer, String> list = new HashMap<Integer, String>();

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `ProductCategories`");
									rs = pst.executeQuery();
									while (rs.next()) {
										categoryID = rs.getInt("categoryID");
										list.put(categoryID, rs.getString("categoryName"));
							%>
							
							<a href="#" style="color: white;text-decoration: underline"><%=list.get(categoryID)%></a>
							
							</br>
							<%
								}
									conn.close();
								} catch (Exception e) {
									out.print(e);
								}
							%>
	        </div>
	        
	        
</body>
</html>