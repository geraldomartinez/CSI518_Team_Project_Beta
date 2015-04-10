<%-- 
    Document   : signup
    Created on : Mar 5, 2015, 3:06:15 PM
    Author     : Samuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pellino Financial, LLC.</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        <link rel="stylesheet" type="text/css" href="css/index.css" /> <!-- Style sheet -->
        <link rel="stylesheet" type="text/css" href="css/goldbutton.css" /> <!-- Style sheet for gold buttons -->
        <script type="text/javascript">
            $(document).ready(function () { //When the document loads
                $("#register_button").hide(); //Hide the register button
                $("#page_wrapper").fadeIn(500); //Make the page wrapper fade in over a half-second period
                

                
                $("input[name=accountType]:radio").on("change", function() {
                	if ($(this).val() == "seller"){
                		$("input[name=account_number]").show();
                		$("input[name=routing_number]").show();
                	}else{
                		$("input[name=account_number]").val('').hide();
                		$("input[name=routing_number]").val('').hide();
                	}
                });
            });
        </script>
        <style type="text/css">
            #page_wrapper{
                position: fixed; /* or absolute */
                width: 100%;
                height: 70%;
                top: 15%;  /* center object vertically */
                background-color: rgba(0, 0, 0, 0.8); /* Black at 80% opacity */
                text-align: center;
                display: none;
            }
            
            #register_message{
                color: red;
                font-weight: bold;
                font-size: 1em;
                padding-bottom: 0.2em;
                font-family: arial;
            }
        </style>
    </head>

    <body class="money_bg">
        <div id="page_wrapper" style="top:0; height: 100%; overflow-y: scroll;">
            <div id="header">REGISTER HERE TO PROCEED </div>   
            <div id="sub_header">Let us know how we can help</div>
            <div id="signup_form_wrapper">
                <%
                    String registerMessage = (String) request.getAttribute("registerMessage"); //Obtain the message to be displayed for the register page (if there is one)
                    if (registerMessage == null) { //Prevent null pointer exception
                        registerMessage = "";
                    }
                %>

                <div id="register_message"><%=registerMessage%></div>
                <form id="signup_form" action="SignupServlet" method="POST">
                 	<span style="color:white;">Account Type:</span> 
                 	<input type="radio" name ="accountType" value="buyer" checked="checked"><font color="White"> Buyer</font>
                  	<input type="radio" name ="accountType" value="seller"><font color="White">Seller </font>
                  	<br />
                  	<input name="email" type="text" placeholder="Email Address"/>
                    <br />
                    <button type="submit" class="gold_button" name="check_email" value="check_email" style="width: 18em;"><span>Check Email Availability</span></button>
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
                    <input name="account_number" type="text" style="display: none;" placeholder="Bank Account Number"/>
                    <br />
                    <input name="routing_number" type="text" style="display: none;" placeholder="Routing Number"/>
                    <br />
                    <button type="submit" class="gold_button" name="submit" value="submit"><span>Submit</span></button>
                </form>
                <br />
                <br />
                <%@include file="nav.jsp"%>
            </div>
        </div>
    </body>
</html>
