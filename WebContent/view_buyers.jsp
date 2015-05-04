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
       		#buyerInfo{
       			border: none;
       			margin-left: auto;
       			margin-right: auto;
       			border-collapse: collapse;
       		}
       		#buyerInfo th, #buyerInfo td{
       			padding: 5px;
       		}
       		#buyerInfo th{
       			border-bottom: 1px solid white;
       		}
       		#buyerInfo td{
       			border-bottom: 1px solid rgba(255,255,255,0.1);
       		}
       	</style>
	</head>
	<body style="width: 1200px;">
	<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
		
        <%@include file="top_menu.jsp"%>
        <%			
	        RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
			
			if (navLoggedIn != "true" || !usr.getAccountType().equals("A")) { //If the user is logged in
				//Alert the user that they are already logged in
				request.setAttribute("indexMessage", "Please log into an admin account before attempting to view accounts");
				rd.forward(request, response); //Forward the user with the response above
			}
		%>
        <div id="page_content_wrapper">
       
       
        <H1>Buyers</H1>
        <%
            String adminMessage = (String) request.getAttribute("adminMessage"); //Obtain the message to be displayed for the index page (if there is one)
            if (adminMessage == null) { //Prevent null pointer exception
            	adminMessage = "";
            }
        %>
       	<div id="admin_message" class="message"><%=adminMessage%></div>
        
        <br>
        <br>
        <table id="buyerInfo">
				<tr>
					<th>User ID</th>
					<th>First Name</th>
					<th>Middle Name</th>
					<th>Last Name</th>
					<th>Phone</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Zip</th>
					<th>Status</th>
					<th>Toggle Activation</th>
				</tr>
				<tr>
				
				<%
        		//int sellerID = usr.GetUserID(); //Page will error out if there is no seller logged in...
	        	Connection conn = null;
				ResultSet rs = null; //Handles the list of categories
				ResultSet rs2 = null; //Handles the list of products
				int categoryID =0;
				int productID = 0;
				String strID = "";
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
					String sql = "select  u.userID,u.firstName, u.middleName,u.lastName,u.phone,u.address,u.city,u.state,u.zip,v.active from UserProfile u,Users v WHERE u.UserID=v.UserID and v.accountType='B' ;";
					System.out.println(sql);
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
	
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						strID = rs.getString("userID");
						out.println("<td>");out.println(strID);out.println("</td>");
						firstName = rs.getString("firstName");
						out.println("<td>");out.println(firstName);out.println("</td>");
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
							out.println("<td>");out.println("Active");out.println("</td>");
						
						}else
						{
							out.println("<td>");out.println("Inactive");out.println("</td>");
						}
        	
        		%>
					<td>
						<form id="toggle_activation" action="ToggleActivationStatusServlet" method="POST">
		            		<input name="userID" value="<%=rs.getInt("userID") %>" type="hidden" />
			            		<input name="page" value="buyer" type="text" style="display: none;" />
			            	<button type="submit" name="toggleBtn" value="toggle_activation"> Toggle Activation Status </button>
		            	</form>
					</td>
       			</tr>
        		<% }
        			
					} catch (Exception e) {
						out.print(e);
					}
				%>
        </table>
        </div>
</body>
</html>