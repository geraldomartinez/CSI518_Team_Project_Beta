<%-- 
    Document   : index
    Created on : Mar 5, 2015, 12:39:38 PM
    Author     : Samuel Pellino
--%>

<%@page import="controller.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Great Danes Electronics</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        <link rel="stylesheet" type="text/css" href="css/index.css" /> <!-- Style sheet -->
        <link rel="stylesheet" type="text/css" href="css/goldbutton.css" /> <!-- Style sheet for gold buttons -->
        <script type="text/javascript">
            $(document).ready(function () { //When the page loads
                $("#page_wrapper").fadeIn(500); //Make the page wrapper fade in over a half-second period
            });
        </script>
    </head>
    <body class="city_bg">
        <div id="page_wrapper" style="height: 22em; overflow-y: visible;">
            <div id="header">GREAT DANES ELECTRONICS</div>
            <div id="sub_header"><span id="sub_header_pt_1">Sell and buy electronics</span></div>
            <%
                //If the user is already logged in, just forward them to the account page
                String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
                String indexMessage = (String) request.getAttribute("indexMessage"); //Get the "index message" to be displayed (if there is one)
                String welcomeMsg = ""; //Initialize the welcome message
                User usr = (User) session.getAttribute("user"); //Obtain the user object from the session (if there is one)

                if (loggedIn == null) { //Prevent null pointer exception
                    loggedIn = "false";
                }

                if (indexMessage == null) { //Prevent null pointer exception
                    indexMessage = "";
                }

                if (!loggedIn.equals("true")) { //If the user is not logged in
                    if (indexMessage == "") { //If the index message is not set
                        indexMessage = "Please log in to view your account"; //Tell the user they need to log in to view their account
                    }
                } else { //The user is logged in
                    welcomeMsg = "Welcome " + usr.GetFirstName() + " " + usr.getMiddleName() + " " + usr.GetLastName(); //Welcome the user with their full name
                }
            %>
            <div id="index_message"><%=indexMessage%></div>
            <div id="welcome_message"><%= welcomeMsg%></div>
            <%@include file="nav.jsp"%>
            <%                
            if (loggedIn.equals("true")) { //If the user is logged , show the logout button
            %>
            <%@include file="logout.jsp"%>
            <%
                }
            %>
        </div>
    </body>
</html>
