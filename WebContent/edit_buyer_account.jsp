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
       	 <script type="text/javascript" >
        function ToggleEditable (button) {
            var div = document.getElementById ("myDiv");

            if (div.contentEditable == "true") {
                div.contentEditable = "false";
                button.innerHTML = "Edit!";
            }
            else {
                div.contentEditable = "true";
                button.innerHTML = "Editing done!";
            }
        }
        
    </script>
	</head>
	<body>
	<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
		
        <%@include file="top_menu.jsp"%>
        <div id="page_content_wrapper">
       
       
        <H1>Account Information</H1>
        
        <button onclick="ToggleEditable (this);">Edit!</button>
        <br>
        <br>
        
        <table id="buyerInfo" border=1 align=center>
				<tr>
					<th >First Name</th>
					<th>Middle Name</th>
					<th>Last Name</th>
					<th>Phone</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Zip</th>
					
				</tr>
				
				<%
        		int userID = User.GetUserID(); //Page will error out if there is no seller logged in...
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
				
				
					String sql = "SELECT DISTINCT u.firstName, u.middleName, u.lastName, u.phone, u.address, u.city, u.state, u.zip FROM UserProfile u, Users v, SellerDetails s WHERE u.UserID = v.UserID AND v.accountType =  'B' AND u.UserID ='"+userID+"';";
					System.out.println(sql);
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
	
					PreparedStatement pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while (rs.next()) {
						firstName = rs.getString("firstName");
						System.out.println(firstName);
						middleName = rs.getString("middleName");
						lastName = rs.getString("lastName");
					
						phone = rs.getString("phone");
						
						address=rs.getString("address");

						city=rs.getString("city");
					
						state=rs.getString("state");
						
						zip=rs.getString("zip");
						
						
					
					}
				
					
        		%>
        	
        		<% 
        			
					} catch (Exception e) {
						out.print(e);
					}
				%>
			
		 <td>
		 <div id="myDiv" contentEditable="true"><%=firstName%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=middleName%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=lastName%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=phone%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=address%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=city%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=state%>
		 </div>
		 </td>
		 <td>
		 <div id="myDiv" contentEditable="true"><%=zip%>
		 </div>
		 </td>
		 <input type="submit" value="Submit" name="insertbt">
					
    </div>
        </table>
</body>
</html>