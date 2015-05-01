<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit a Product</title>
</head>

<%@ page
		import="controller.AuthDAO,controller.Utilities,java.util.*, java.sql.*"%>
		
        <%@include file="top_menu.jsp"%>
        <div id="page_content_wrapper">
        

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
   
    
    <%@ page import="controller.Product" %>
    <%
    String productName = "";
	String pDescription = "";
	String pSpecs = "";
	float pPrice = 0;
	int productID=33;
	String picture="";
	Connection conn = null;
	ResultSet rs = null;
	Product prod = null;
	int sellerID=0,pQuantity=0;

	try {			
	
		
		//productID=prod.GetProductID();
			String sql = "SELECT * FROM Products WHERE productID='"+productID+"';";
			conn = AuthDAO.createConn();
			
			
			PreparedStatement pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				productName = rs.getString("productName");
				pDescription = rs.getString("description");
				pPrice = rs.getFloat("unitPrice");
				pSpecs = rs.getString("specs");
				productID = rs.getInt("productID");
				pQuantity=rs.getInt("quantity");
			    prod = AuthDAO.getProductById(productID);
				picture = prod.getPicture();
			 
	
					}
					conn.close();
				} catch (Exception e) {
					out.print(e);
				}
	
		%>
		  
	
		<h4><a href="view_product.jsp?productID=<%=productID %>" style="color: white;"><%=productName%></a></h4>
		<div ><a href="view_product.jsp?productID=<%=productID %>"><img src="<%=picture%>" style="max-width: 300px; max-height: 300px;"></a></div>
		<br />
		 <div>Product Name: 
		 
		 <div id="myDiv" contentEditable="true"><%=productName%>
		 </div>
		 </div>
		 <div>Description:
		 <div id="myDiv" contentEditable="true"><%=pDescription%></div>
		 </div>
		 <div>Specifications:
		 <div id="myDiv" contentEditable="true"><%=pSpecs%></div>
		 </div>
		 <div>Price:
		 <div id="myDiv" contentEditable="true"><%=pPrice%></div>
		 </div>
		 <div>Quantity:
		 <div id="myDiv" contentEditable="true"><%=pQuantity%></div>
		 </div>
		 <input type="submit" value="Submit" name="insertbt">
					
    </div>
<br/>
<button onclick="ToggleEditable (this);">Edit!</button>
    
</body>
</html>
