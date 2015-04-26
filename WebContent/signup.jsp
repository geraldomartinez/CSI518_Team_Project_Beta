<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Signup - Great Danes Electronics</title>
		
       	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
       	
        <script type="text/javascript">
            $(document).ready(function () { //When the document loads
                $("#register_button").hide(); //Hide the register button
                $("#page_wrapper").fadeIn(500); //Make the page wrapper fade in over a half-second period
                

                
                $("input[name=accountType]:radio").on("change", function() {
                	if ($(this).val() == "seller"){
                		$("input[name=account_number]").fadeIn();
                		$("input[name=routing_number]").fadeIn();
                		$("input[name=company_name]").fadeIn();
                	}else{
                		$("input[name=account_number]").val('').fadeOut();
                		$("input[name=routing_number]").val('').fadeOut();
                		$("input[name=company_name]").val('').fadeOut();
                	}
                });
            });
        </script>
       	
       	<style type="text/css">
       		#page_content_wrapper{
       			text-align: center;
       		}
       		
       		#signup_form input:not([type="radio"]), #signup_form button{
       			width: 200px;
       			margin-top: 5px;
       		}
       		
       		#signup_form button[name="submit"]{
       			margin-top: 20px;
       		}
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
	        <h1 id="header">REGISTRATION FORM</h1>   
	        <h3 id="sub_header">Create a new account to buy or sell products</h3>
	        <div id="signup_form_wrapper">
		        <%
		            String registerMessage = (String) request.getAttribute("registerMessage"); //Obtain the message to be displayed for the register page (if there is one)
		            if (registerMessage == null) { //Prevent null pointer exception
		                registerMessage = "";
		            }
		        %>
	        	<div id="register_message" class="message"><%=registerMessage%></div>
        	<br />
                <form id="signup_form" action="SignupServlet" method="POST">
                 	<span style="color:white;">Account Type:</span> 
                 	<input type="radio" name ="accountType" value="buyer" checked="checked"><font color="White"> Buyer</font>
                  	<input type="radio" name ="accountType" value="seller"><font color="White">Seller </font>
                  	<br />
                  	<br />
                  	<input name="email" type="email" placeholder="Email Address"/>
                    <br />
                    <button type="submit" class="gold_button" name="check_email" value="check_email"><span>Check Email Availability</span></button>
                    <br />
                    <br />
                    <input name="password" type="password" placeholder="Password"/>
                    <br />
                    <input name="password_confirm" type="password" placeholder="Confirm Password"/>
                    <br />
                    <input name="fname" type="text" placeholder="First Name"/>
                    <br />
                    <input name="mname" type="text" placeholder="Middle Name"/>
                    <br />
                    <input name="lname" type="text" placeholder="Last Name"/>
                    <br />
                    <input name="phone" type="text" maxlength="10" placeholder="Phone"/>
                    <br />
                    <input name="address" type="text" placeholder="Address"/>
                    <br />
                    <input name="city" type="text" placeholder="City"/>
                    <br />
                    <input name="state" type="text" maxlength="2" placeholder="State"/>
                    <br />
                    <input name="zip" type="text" placeholder="Zip"/>
                    <br />
                    <input name="company_name" type="text" style="display: none;" placeholder="Company Name"/>
                    <br />
                    <input name="account_number" type="text" style="display: none;" placeholder="Bank Account Number"/>
                    <br />
                    <input name="routing_number" type="text" style="display: none;" placeholder="Routing Number"/>
                    <br />
                    <button type="submit" class="gold_button" name="submit" value="submit"><span>Submit</span></button>
                </form>
        	</div>
        </div>
	</body>
</html>
