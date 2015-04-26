
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import='java.sql.*'
	import='controller.AuthDAO'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Survey</title>
</head>
<body>
<%Connection conn = null;
ResultSet rs = null;
int productID;
%>
<form id="Survey" action="SurveyServlet" method="POST">
<table>
				<tr>
					<td>What are your favorite color(s)?</td>
					</tr>
					<tr>
					<td><select id=productID name=productID>
							<%
								
								String color = "";

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `Products`");
									rs = pst.executeQuery();
									while (rs.next()) {
										productID = rs.getInt("productID");
										color = rs.getString("color");
							%>
							<option value="<%=productID%>" selected><%=color%></option>
							<%
								}
									conn.close();
								} catch (Exception e) {
									out.print(e);
								}
							%>
					</select>
				</tr>
				</table>
	<table>
				<tr>
					<td>What type of electronic gadgets are you looking for?</td>
					</tr>
					<tr>
					<td><select id=categoryID name=categoryID>
							<%
								
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
				</table>
<p>What price range would you like it to be in?</p>
<p>		
<select name="price">
<option value="" selected>- Select -</option> 
<option  value="1">000.00 to 100.00</option>
<option  value="2">100.00 to 200.00</option>
<option  value="3">200.00 to 300.00</option>
<option  value="4">300.00 to 400.00</option>
<option  value="5">400.00 to 500.00</option>
<option  value="6">500.00 to 600.00</option>
<option  value="7">600.00 to 700.00</option>
<option  value="8">700.00 to 800.00</option>
<option  value="9">800.00 to 900.00</option>
<option  value="10">900.00 to 1000.00</option>
<option  value="10">900.00 to 1000.00</option>
<option  value="11">above 1000.00</option>
</select>
</p>
<p>Purpose of use?</p>
<p>
<input type="checkbox" name="use" value="1"/>Student<br/>
<input type="checkbox" name="use" value="2"/>Commercial<br/>
<input type="checkbox" name="use" value="3"/>Personal<br/>
</p>
<table>
				<tr>
					<td>What manufacturers do you prefer?</td>
					</tr>
					<tr>
					<td><select id=sellerID name=sellerID>
							<%
								
								int sellerID=0;
								String companyName = "";

								try {
									conn = AuthDAO.createConn();
									HttpSession ss = request.getSession();

									PreparedStatement pst = conn
											.prepareStatement("SELECT * FROM `SellerDetails`");
									rs = pst.executeQuery();
									while (rs.next()) {
										sellerID = rs.getInt("sellerID");
										companyName = rs.getString("companyName");
							%>
							<option value="<%=sellerID%>" selected><%=sellerID%></option>
							<%
								}
									conn.close();
								} catch (Exception e) {
									out.print(e);
								}
							%>
					</select>
				</tr>
				</table>

<button type="submit" name="submit" value="submit">Submit Survey</button>
</form>

</body>
</html>