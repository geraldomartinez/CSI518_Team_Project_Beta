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
       		#order_table, #items_table{
       			border-collapse: collapse;
				width: 100%;
       		}
       		#order_table tr:nth-child(2) td, #items_table tr:nth-child(2) td{
				padding-top: 10px;
       		}
       		#order_table th, #items_table th{
				vertical-align: text-top;
				border-bottom: 1px solid white;
				padding-bottom: 10px;
       		}
       		.tinytext{
       			font-size: 10px;
       		}
			h3{
        		background: #2C193B;
				padding-top: 5px;
				padding-bottom: 5px;
			}
			.quantity{
				width: 70px;
			}
       	</style>
	</head>
	<body style="width: 1400px;">
		<%@ page import="controller.AuthDAO,controller.Utilities,controller.Product,java.util.*, java.sql.*"%>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
        <%
        	String orderID = request.getParameter("orderID");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
			if (!navLoggedIn.equals("true")){
                request.setAttribute("indexMessage", "Please log in to view orders");
                rd.forward(request, response); //Forward the user with the response above
			}

            if (orderID == null){
            	orderID = "";
            }
            
        %>
            <h3>Order Summary</h3>
            <%
                String orderMessage = (String) request.getAttribute("orderMessage"); //Obtain the login message from the session
                if (orderMessage == null) { //Prevent null pointer exception
                	orderMessage = "";
                }
            %>
            <div id="order_message" class="message"><%=orderMessage%></div>
            <div>
	        	<table id="order_table">
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
	        		</tr>
	        	<%
	        		String sql = "";
		        	Connection conn = null;
					ResultSet rs = null;
					conn = AuthDAO.createConn();
					HttpSession ss = request.getSession();
					PreparedStatement pst;
					Product prd;
					String sellerID = "";
					String orderTimestamp = "", orderTotal = "", shippingStatus = "", cancelStatus = "", buyerID = "", buyerEmail = "";
					int itemCount = -1, numItemsShipped = -1, numItemsCanceled = -1,  productID = -1;
					try {
						switch (acctType){
							case "B":
								sql = "SELECT *, COUNT(`OrderItems`.`productID`) as itemCount, COUNT(CASE WHEN `OrderItems`.`hasShipped`=1 THEN 0 END) as numItemsShipped, COUNT(CASE WHEN `OrderItems`.`canceled`=1 THEN 0 END) as numItemsCanceled, SUM(`OrderItems`.`unitPrice`) as totalCost, SUM(`OrderItems`.`shippingPrice`) as totalShipping, SUM(`OrderItems`.`tax`) as totalTax "+
										"FROM `Orders` LEFT JOIN (`OrderItems`) ON (`OrderItems`.`orderID`=`Orders`.`orderID`)" + 
										"WHERE `buyerID` = '"+usr.GetUserID()+"' AND `Orders`.`OrderID`='"+orderID+"'"+
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
				        			<td><%=orderID%></td>
				        			<td><%=orderTimestamp%></td>
				        			<td><%=itemCount%></td>
				        			<td>$<%=orderTotal%></td>
				        			<td><%=shippingStatus%></td>
				        			<td><%=cancelStatus%></td>
				        		</tr>
				        		<%
								}
								break;
							case "A":
							case "S":
								sql = "		SELECT `Orders`.`orderID`,`Orders`.`buyerID`,`Orders`.`orderTimestamp`,`Users`.`email`, COUNT(`OrderItems`.`productID`) as itemCount, COUNT(CASE WHEN `OrderItems`.`hasShipped`=1 THEN 0 END) as numItemsShipped, COUNT(CASE WHEN `OrderItems`.`canceled`=1 THEN 0 END) as numItemsCanceled, SUM(`OrderItems`.`unitPrice`) as totalCost, SUM(`OrderItems`.`shippingPrice`) as totalShipping, SUM(`OrderItems`.`tax`) as totalTax" + 
										"	FROM `Orders` LEFT JOIN (`OrderItems`,`Products`,`Users`) ON (`OrderItems`.`orderID`=`Orders`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID` AND `Orders`.`buyerID`=`Users`.`userID`)" +
											"WHERE `Orders`.`orderID` = '"+orderID+"' "+((acctType.equals("S"))?("	AND `Products`.`sellerID` = '"+usr.GetUserID()+"'"):"");
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
				        			<td><%=orderID%></td>
				        			<td><%=orderTimestamp%></td>
				        			<%= ((acctType.equals("A"))?("<td>"+buyerID+"</td>"):"") %>
				        			<td><%=buyerEmail%></td>
				        			<td><%=itemCount%></td>
				        			<td>$<%=orderTotal%></td>
				        			<td><%=shippingStatus%></td>
				        			<td><%=cancelStatus%></td>
				        		</tr>
				        		<%
								}
								break;
						}
					%>
					</table>
					<br />
					<br />
            		<h3>Items</h3>
					<br />
					<table id="items_table">
						<tr>
							<th colspan="2">
								Product
							</th>
							<th>
								Price
							</th>
							<th>
								Shipping Cost
							</th>
							<th>
								Tax
								<br />
								<span class="tinytext">Tax is on price + shipping</span>
							</th>
							<%=((acctType.equals("B"))?"<th>Update Quantity</th>":"<th>Quantity</th>")%>
							<th>
								Cancel Item
								<br />
								<span class="tinytext">Cancellation available if item <br /> has not been shipped or canceled</span>
							</th>
							<th>
								Shipped
							</th>
							<%=((acctType.equals("S"))?"<th>Mark as Shipped</th>":"")%>
							<th>
								Canceled
							</th>
						</tr>
					<%
						String itemPrice = "", shippingPrice = "", tax = "", quantity = "", shipped = "", canceled = "";
						sql = "SELECT * FROM `Orders` "+
								"LEFT JOIN (`OrderItems`,`Products`) on (`Orders`.`orderID` = `OrderItems`.`orderID` AND `OrderItems`.`productID` = `Products`.`productID`) "+
								"WHERE `Orders`.`orderID` = '"+orderID+"'" + 
								((acctType.equals("S"))?"AND `Products`.`sellerID` = '"+usr.GetUserID()+"'":"");
						System.out.println(sql);
						pst = conn.prepareStatement(sql);
						rs = pst.executeQuery();
						
						while (rs.next()) {	
							productID = rs.getInt("productID");
							prd = AuthDAO.getProductById(productID);
							itemPrice = rs.getString("unitPrice");
							shippingPrice = rs.getString("shippingPrice");
							tax = rs.getString("tax");
							quantity = rs.getString("quantity");
							shipped = ((rs.getBoolean("hasShipped"))?"Yes":"No");
							canceled = ((rs.getBoolean("canceled"))?"Yes":"No");
							
							out.print("<tr>");
								out.print("<td><img src='"+prd.getPicture()+"' style='max-height: 100px; max-width: 100px; padding-bottom: 25px;'></td>");
								out.print("<td><a href='view_product.jsp?productID="+productID+"' style='color: white;'>"+prd.GetProductName()+"</a></td>");
								out.print("<td>"+itemPrice+"</td>");
								out.print("<td>"+shippingPrice+"</td>");
								out.print("<td>"+tax+"</td>");
								out.print(((acctType.equals("B"))?"<td><input type='number' value='"+quantity+"' class='quantity' /> &nbsp; <input type='button' value='Update' /></td>":"<td>"+quantity+"</td>"));
								out.print("<td><input type='button' value='Cancel' "+((rs.getBoolean("hasShipped") || (rs.getBoolean("canceled"))?"disabled":""))+" /></td>");
								out.print("<td>"+shipped+"</td>");
								out.print(((acctType.equals("S"))?"<td><input type='button' value='Mark as Shipped' /></td>":""));
								out.print("<td>"+canceled+"</td>");
							out.print("</tr>");
							
						}
					%>
					</table>
					<%
					} catch (Exception e) {
						out.print(e);
					}
					%>
            </div>
        </div>
	</body>
</html>