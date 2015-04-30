<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
       	
       	<style type="text/css">
       		#page_content_wrapper{
       			text-align: center;
       		}
       		#page_content_wrapper a{
       			color: white;
       		}
       	</style>
	</head>
	<body>
	<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
		
        <div id="page_content_wrapper">
        <%@include file="top_menu.jsp"%>
       
       
        <H1>List of sellers</H1>
        
        <br>
        <br>
        <table id="sellerInfo" border=1 align=center>
				<tr>
					<th >First Name</th>
					<th>Middle Name</th>
					<th>Last Name</th>
					<th>Phone</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Zip</th>
					<th>Status<th>
				</tr>
				
				<%
        		//int sellerID = usr.GetUserID(); //Page will error out if there is no seller logged in...
	        	Connection conn = null;
				ResultSet rs = null; //Handles the list of categories
				ResultSet rs2 = null; //Handles the list of products
				int categoryID =0;
				int productID = 0;
				String firstName = "";
				String middleName = "";
				String lastName = "";
				String phone="";
				String address="";
				String city="";
				String state="";
				String zip="";
				int active=0;
				
				try {
					String sql = "select  u.firstName, u.middleName,u.lastName,u.phone,u.address,u.city,u.state,u.zip,v.active from UserProfile u,Users v WHERE u.UserID=v.UserID and v.accountType='B' ;";
					System.out.println(sql);
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
	
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						firstName = rs.getString("firstName");
						System.out.println(firstName);
						out.println("<tr><td>");out.println(firstName);out.println("</td>");
						middleName = rs.getString("middleName");
						out.println("<td>");out.println(middleName);out.println("</td>");
						lastName = rs.getString("lastName");
						out.println("<td>");out.println(lastName);out.println("</td>");
						phone = rs.getString("phone");
						out.println("<td>");out.println(phone);out.println("</td>");
						address=rs.getString("address");
						out.println("<td>");out.println(address);out.println("</td>");
						city=rs.getString("city");
						out.println("<td>");out.println(city);out.println("</td>");
						state=rs.getString("state");
						out.println("<td>");out.println(state);out.println("</td>");
						zip=rs.getString("zip");
						out.println("<td>");out.println(zip);out.println("</td>");
						active=rs.getInt("active");
						if(active==1)
						{
							out.println("<td>");out.println("Active");out.println("</td></tr>");
						
						}else
						{
							out.println("<td>");out.println("InActive");out.println("</td></tr>");
						}
						
						
        	
        		%>
        		
        		<% }
        			
					} catch (Exception e) {
						out.print(e);
					}
				%>
        </table>
</body>
</html>