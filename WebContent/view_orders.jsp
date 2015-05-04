<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>My Orders - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
       	
       	<style type="text/css">
       		#page_content_wrapper{
       			text-align: center;
       		}
       		#page_content_wrapper a{
       			color: white;
       		}
       		#orders_table{
       			border-collapse: collapse;
				width: 100%;
       		}
       		#orders_table tr:nth-child(2) td{
				padding-top: 10px;
       		}
       		#orders_table th{
				vertical-align: text-top;
				border-bottom: 1px solid white;
				padding-bottom: 10px;
       		}
       		.tinytext{
       			font-size: 10px;
       		}
       	</style>
	</head>
	<body style="width: 1200px;">
		<%@ page import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
        <%@include file="top_menu.jsp"%>
        <div id="page_content_wrapper">
	        <%
	        	RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
	            String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
	            if (loggedIn == null) { //Prevent null pointer exception
	                loggedIn = "false";
	            }
	
	            if (loggedIn != "true") { //If the user is not logged in
	                request.setAttribute("indexMessage", "Please log in to view orders");
	                rd.forward(request, response); //Forward the user with the response above
	            }
	        %>
        	<h1><%= ((acctType.equals("A"))?"All":"My") %> Orders</h1>
        	<br />
            <%
                String ordersMessage = (String) request.getAttribute("ordersMessage"); //Obtain the login message from the session
                if (ordersMessage == null) { //Prevent null pointer exception
                	ordersMessage = "";
                }
            %>
            <div id="orders_message" class="message"><%=ordersMessage%></div>
        	<table id="orders_table">
        		<tr>
       			 	<th>Order ID</th>
        			<th>Order Timestamp<br /><span class="tinytext">y-m-d h:m:s</span></th>
        			<%
					switch (acctType){
						case "B":
        			 %>
        			<th>Number of Items</th>
        			<th>Total Cost</th>
        			<th>Shipping Status<br /><span class="tinytext">None/Some/All Shipped</span></th>
        			<th>Cancellation Status<br /><span class="tinytext">None/Some/All Canceled</span></th>
        			<%
        					break;
						case "A":
        			 %>
          			<th>Buyer ID</th>
          			<%
          				//Don't break! We want all of the seller's <th> tags as well for admin
						case "S":
        			 %>
         			<th>Buyer Email</th>
        			<th>Number of Items</th>
        			<th>Total Cost</th>
        			<th>Shipping Status<br /><span class="tinytext">None/Some/All Shipped</span></th>
        			<th>Cancellation Status<br /><span class="tinytext">None/Some/All Canceled</span></th>
         			<%
						break;
					}
        			 %>
         			<th>View</th>
        		</tr>
        	<%
        		String sql = "";
	        	Connection conn = null;
				ResultSet rs = null;
				conn = AuthDAO.createConn();
				HttpSession ss = request.getSession();
				PreparedStatement pst;
				String sellerID = "";
				String orderID = "", orderTimestamp = "", orderTotal = "", shippingStatus = "", cancelStatus = "", buyerID = "", buyerEmail = "";
				int itemCount = -1, numItemsShipped = -1, numItemsCanceled = -1;
				try {
					switch (acctType){
						case "B":
							sql = "SELECT *, SUM(`OrderItems`.`quantity`) as itemCount, SUM(  `OrderItems`.`hasShipped` *  `quantity` ) AS numItemsShipped, SUM(  `OrderItems`.`canceled` *  `quantity` ) AS numItemsCanceled, SUM(`OrderItems`.`unitPrice` * `OrderItems`.`quantity`) as totalCost, SUM(`OrderItems`.`shippingPrice` * `OrderItems`.`quantity`) as totalShipping, SUM(`OrderItems`.`tax` * `OrderItems`.`quantity`) as totalTax "+
									"FROM `Orders` LEFT JOIN (`OrderItems`) ON (`OrderItems`.`orderID`=`Orders`.`orderID`)" + 
									"WHERE `buyerID` = '"+usr.GetUserID()+"'"+
									"GROUP BY `Orders`.`orderID`";
							System.out.println(sql);
							pst = conn.prepareStatement(sql);
							rs = pst.executeQuery();
							
							while (rs.next()) {				
								orderID = rs.getString("orderID");
								orderTimestamp = rs.getString("orderTimestamp").substring(0, rs.getString("orderTimestamp").length()-2); //Remove the ".0" millisecond from the end of the timestamp string
								itemCount = rs.getInt("itemCount");
								numItemsShipped = rs.getInt("numItemsShipped");
								numItemsCanceled = rs.getInt("numItemsCanceled");
								orderTotal = String.format("%.2f",rs.getFloat("totalCost") + rs.getFloat("totalShipping") + rs.getFloat("totalTax"));
								if (itemCount == numItemsShipped){
									shippingStatus = "All Shipped";
								}else if (numItemsShipped > 0){
									shippingStatus = "Some Shipped";
								}else{
									shippingStatus = "None Shipped";
								}
								
								if (itemCount == numItemsCanceled){
									cancelStatus = "All Canceled";
								}else if (numItemsCanceled > 0){
									cancelStatus = "Some Canceled";
								}else{
									cancelStatus = "None Canceled";
								}
		        	
			        		%>
			        		<tr>
			        			<td><a href="view_order.jsp?orderID=<%=orderID%>"><%=orderID%></a></td>
			        			<td><%=orderTimestamp%></td>
			        			<td><%=itemCount%></td>
			        			<td>$<%=orderTotal%></td>
			        			<td><%=shippingStatus%></td>
			        			<td><%=cancelStatus%></td>
			        			<td><a href="view_order.jsp?orderID=<%=orderID%>">View Order</a></td>
			        		</tr>
			        		<%
							}
							break;
						case "A":
						case "S":
							sql = "		SELECT `Orders`.`orderID`,`Orders`.`buyerID`,`Orders`.`orderTimestamp`,`Users`.`email`, SUM(`OrderItems`.`quantity`) as itemCount, SUM(  `OrderItems`.`hasShipped` *  `quantity` ) AS numItemsShipped, SUM(  `OrderItems`.`canceled` *  `quantity` ) AS numItemsCanceled, SUM(`OrderItems`.`unitPrice` * `OrderItems`.`quantity`) as totalCost, SUM(`OrderItems`.`shippingPrice` * `OrderItems`.`quantity`) as totalShipping, SUM(`OrderItems`.`tax` * `OrderItems`.`quantity`) as totalTax" + 
									"	FROM `Orders` LEFT JOIN (`OrderItems`,`Products`,`Users`) ON (`OrderItems`.`orderID`=`Orders`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID` AND `Orders`.`buyerID`=`Users`.`userID`)" +
										((acctType.equals("S"))?("	WHERE `Products`.`sellerID` = '"+usr.GetUserID()+"'"):"") +
									"	GROUP BY `Orders`.`orderID`";
							System.out.println(sql);
							pst = conn.prepareStatement(sql);
							rs = pst.executeQuery();
							
							while (rs.next()) {				
								orderID = rs.getString("orderID");
								orderTimestamp = rs.getString("orderTimestamp").substring(0, rs.getString("orderTimestamp").length()-2); //Remove the ".0" millisecond from the end of the timestamp string
								buyerID = rs.getString("buyerID");
								buyerEmail = rs.getString("email");
								itemCount = rs.getInt("itemCount");
								numItemsShipped = rs.getInt("numItemsShipped");
								numItemsCanceled = rs.getInt("numItemsCanceled");
								orderTotal = String.format("%.2f", rs.getFloat("totalCost") + rs.getFloat("totalShipping") + rs.getFloat("totalTax"));
								if (itemCount == numItemsShipped){
									shippingStatus = "All Shipped";
								}else if (numItemsShipped > 0){
									shippingStatus = "Some Shipped";
								}else{
									shippingStatus = "None Shipped";
								}
								
								if (itemCount == numItemsCanceled){
									cancelStatus = "All Canceled";
								}else if (numItemsCanceled > 0){
									cancelStatus = "Some Canceled";
								}else{
									cancelStatus = "None Canceled";
								}
		        	
			        		%>
			        		<tr>
			        			<td><a href="view_order.jsp?orderID=<%=orderID%>"><%=orderID%></a></td>
			        			<td><%=orderTimestamp%></td>
			        			<%= ((acctType.equals("A"))?("<td>"+buyerID+"</td>"):"") %>
			        			<td><%=buyerEmail%></td>
			        			<td><%=itemCount%></td>
			        			<td>$<%=orderTotal%></td>
			        			<td><%=shippingStatus%></td>
			        			<td><%=cancelStatus%></td>
			        			<td><a href="view_order.jsp?orderID=<%=orderID%>">View Order</a></td>
			        		</tr>
			        		<%
							}
							break;
					}
				} catch (Exception e) {
					out.print(e);
				}
				%>
			</table>
        </div>
	</body>
</html>