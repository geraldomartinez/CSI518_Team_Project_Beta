<%-- 
    Document   : login
    Created on : Mar 5, 2015, 3:06:07 PM
    Author     : Samuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pellino Financial, LLC.</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        <link rel="stylesheet" type="text/css" href="css/index.css" /> <!-- Style sheet -->
        <link rel="stylesheet" type="text/css" href="css/goldbutton.css" /> <!-- Style sheet for gold buttons -->
        <script type="text/javascript">
            $(document).ready(function () {
                $("#login_button, #logout_button").hide(); //Hide the login/logout buttons
                $("#page_wrapper").fadeIn(500); //Make the page wrapper fade in over a half-second period
            });
        </script>
    </head>
    <body class="skyscraper_bg">
        <%
            //If the user is already logged in, just forward them to the account page
            //(note, this will only happen if the user deliberately accesses login.jsp while logged in via the browser's address bar. The buttons to get here are hidden to the user)
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Setup the request dispatcher for the index page
            String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
            if (loggedIn == null) { //Prevent null pointer exception
                loggedIn = "false";
            }

            if (loggedIn == "true") { //If the user is logged in
                //Alert the user that they are already logged in
                request.setAttribute("indexMessage", "<span style='color: red;'>You are already logged in</span>");
                rd.forward(request, response); //Forward the user with the response above
            }
        %>
        <div id="page_wrapper">
            <div id="header">PELLINO FINANCIAL, LLC.</div>   
            <div id="sub_header">Please log in to view your account</div>
            <%
                String loginMessage = (String) request.getAttribute("loginMessage"); //Obtain the login message from the session
                if (loginMessage == null) { //Prevent null pointer exception
                    loginMessage = "";
                }
                if (loginMessage != "") { //If there is a login message, display it
            %>
            <div id="login_message"><%=loginMessage%></div>
            <%
                }
            %>
            <div id="login_form_wrapper">
                <form id="login_form" action="LoginServlet" method="POST">
                    <input name="email" type="text" placeholder="Email"/>
                    <br />
                    <input name="password" type="password" placeholder="Password"/>
                    <br />
                    <button type="submit" class="gold_button" name="login"><span>Log in</span></button>
                </form>
                <%@include file="nav.jsp"%>
            </div>
        </div>
    </body>
</html>
