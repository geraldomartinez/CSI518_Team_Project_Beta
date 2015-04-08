<%-- 
    Document   : nav
    Created on : Mar 5, 2015, 3:06:22 PM
    Author     : Samuel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/goldbutton.css" /> <!-- Style sheet for gold buttons -->
<button class="gold_button" id="home_button" onclick="location.href = 'index.jsp';"><span>Home</span></button>&nbsp;
<%
    String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session

    if (navLoggedIn == null) { //Prevent null pointer exception
        navLoggedIn = "";
    }

    if (navLoggedIn != "true") { //If the user is not logged in
        //Display the "login" and "register" buttons
        out.print("<button class=\"gold_button\" id=\"login_button\" onclick=\"location.href = 'login.jsp';\"><span>Log In</span></button>&nbsp;&nbsp;");
        out.print("<button class=\"gold_button\" id=\"register_button\" onclick=\"location.href = 'signup.jsp';\"><span>Register</span></button>");
    } else { //The user is logged in
        //Display the "view account" button
        out.print("<button class=\"gold_button\" id=\"account_button\" onclick=\"location.href = 'account.jsp';\"><span>View Account</span></button>");
    }

%>
&nbsp;