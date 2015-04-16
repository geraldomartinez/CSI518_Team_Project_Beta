<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse - Great Danes Electronics</title>
</head>
<body>
		<%@ page import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*" %>
	 <%@include file="top_menu.jsp"%>

		
		
		<div id="page_content_wrapper">
	        	<%
								Connection conn = null;
								ResultSet rs = null;
								int categoryID;
								String categoryName = "";
								int iterator = 0;
								int tracker = 0;
								HashMap<Integer, String> list = new HashMap<Integer, String>();

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `ProductCategories`");
									rs = pst.executeQuery();
									while (rs.next()) {
										categoryID = rs.getInt("categoryID");
										categoryName = rs.getString("categoryName");
										list.put(categoryID, categoryName);
							%>
							
							<a href="browse.jsp?categoryID=<%=Utilities.getMapKeyValue(list, list.get(categoryID)) %>" style="color: white;text-decoration: underline"><%=list.get(categoryID)%></a>
							||
						
							<%
							
								}
									//conn.close();
								} catch (Exception e) {
									out.print(e);
								}
							%>
							
							
        <%
        	String strCategoryID = request.getParameter("categoryID");
        	if (strCategoryID == null){
        		strCategoryID = "";
        	}
        	
        	if (strCategoryID == ""){
   		%>
	        	<h3 style="text-align: center;">Top 10 Rated Products</h3>
	      
   		<%
        	}
        	
        	else{
	       		categoryID = Integer.parseInt(strCategoryID);
	       		String current_category = list.get(categoryID);
	       			      	
        %>
       
        	<h3><%=current_category%></h3> <br/>
       
        <%
        	String productName = "";
        	String pDescription = "";
      		String pSpecs = "";
      		float pPrice = 0;
        	

			try {
				String sql = "SELECT * 	FROM  `Products` WHERE categoryID ='" + categoryID + "';";
				conn = AuthDAO.createConn();
				HttpSession ss = request.getSession();

				PreparedStatement pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				while (rs.next()) {
					productName = rs.getString("productName");
					pDescription = rs.getString("description");
					pPrice = rs.getFloat("unitPrice");
					pSpecs = rs.getString("specs");
		%>
		<h4><%=productName %></h4>
		<ul>
			<li>Description: <%=pDescription%></li>
			<li>Price: <%=pPrice%></li>
			<li>Specs: <%=pSpecs%></li>
		</ul>
		</br>
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