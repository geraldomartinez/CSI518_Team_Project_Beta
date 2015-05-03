<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Notifications - Great Danes Electronics</title>
	<style type="text/css">
        	#product_list_table{
        		width: 100%;
        	}
        	
        	#product_list_table th{
        		background: #2C193B;
        		text-align: left;
        		padding: 5px;
        	}
        	
        	#product_list_table td:first-child{
        		padding-left: 10px;
        	}
        	
        	#product_list_table td{
        		padding-top: 5px;
        		padding-bottom: 5px;
        	}
        	
        </style>


</head>
<body>
<%@include file="top_menu.jsp"%>
<%@ page
		import="controller.AuthDAO,controller.Notification,java.util.*, java.sql.*"%>
        <br />
        <div id="page_content_wrapper">
        <%
        	 String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
            if (loggedIn == null) { //Prevent null pointer exception
                loggedIn = "false";
            }

            if (loggedIn == "false") { //If the user is logged in
                //Alert the user that they are already logged in
            	out.println("<font color=red>You must log in to view your account!</font>");
            }else {%>
        <h2>My Notifications</h2>
        
        <table id="product_list_table">
        <tr>
        			<th colspan="1">
        				Notification ID
        			</th>
        			<th colspan="1">
        				Message
        			</th>
        			
        			<th colspan="1">
        				Date
        			</th>
        </tr>
        <%
        int userID = usr.GetUserID();
        List<Notification> notifications = AuthDAO.getUserNotifications(userID);
        
        for(int i = 0; i < notifications.size(); i++){
        	Notification notification = notifications.get(i);
        	%> 
        	
        	<tr>
        		<td align="center"><%=notification.getNotificationID() %></td>
        		<td align="center"><a href="#" style="color:white"><%=notification.getNotificationMessage() %></a></td>
        		<td align="center"><%=notification.getDatetime() %></td>
        	
        	</tr>
        	
        	<%
        	
        }
            
            
            } %>
            </tr>
            </table>
        </div>

</body>
</html>