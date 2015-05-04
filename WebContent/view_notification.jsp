<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Notification - Great Danes Electronics</title>
</head>
<body>
<%@ page
		import="java.util.*, java.sql.*, java.util.Date, java.text.SimpleDateFormat, controller.AuthDAO, controller.Notification"%>
        <%@include file="top_menu.jsp"%>
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
            }else {
            	String notificationID = request.getParameter("notificationID");
				Notification notification = AuthDAO.getNotificationByID(Integer.parseInt(notificationID));
            %>
            
            <h2>Notification #<%=notificationID %></h2>
        
            <h3><%=notification.getNotificationMessage() %></h3>
            
            <%
            if(notification.getNotificationType() == 'S' || notification.getNotificationType() == 'O' 
            || notification.getNotificationType() == 'C' || notification.getNotificationType() == 'Q'){
            	%>
            	<a href="view_order.jsp?orderID=<%=notification.getTypeID()  %>" style="color:white">Click here to view this order!</a>
            	<%
            	
            }else if(notification.getNotificationType() == 'R'){
            	%>
            	<%
					Connection conn = null;
					ResultSet rs = null;
					int categoryID;
					String datetime = null;
					String review = null;
					int rating = 0;
					int reviewerID = 0;
					String reviewerFirstName = null;
					String reviewerLastName = null;
					SimpleDateFormat fromDatabase = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
					SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
					String newDate = "";
					try {
						conn = AuthDAO.createConn();
						HttpSession ss = request.getSession();

						PreparedStatement pst = conn
								.prepareStatement("SELECT p.*, u.firstName, u.lastName FROM ProductReviews p JOIN UserProfile u ON u.userID = p.userID WHERE reviewID ="+notification.getTypeID()+ "");
						rs = pst.executeQuery();
						while (rs.next()) {
							datetime = rs.getTimestamp("time").toString();
							rating = rs.getInt("ranking");
							review = rs.getString("review");
							reviewerID = rs.getInt("userID");
							reviewerFirstName = rs.getString("firstName");
							reviewerLastName = rs.getString("lastName");
							reviewerLastName = reviewerLastName.substring(0, 1).toUpperCase() + ".";
							
							newDate = myFormat.format(fromDatabase.parse(datetime));
				%>
			<!--	<tr> -->
			<!--	<td nowrap> -->
				<h4>Reviewed by <strong><%=reviewerFirstName + " " + reviewerLastName %></strong> on <%=newDate%> PST</h4>
				<%
				for(int i = 1; i<=rating; i++){
					%>
					<span style="color: yellow;">&#9733;</span>
					<%
				}
				
				 %>
				<br>
				<%=review %>
		<!--		</td> -->
		<!--		</tr> -->
				<br>
				<%
					}
						conn.close();
					} catch (Exception e) {
						out.print(e);
					}
				%>
            	<%
            }
            }
            %>
         
         
         </div>

</body>
</html>