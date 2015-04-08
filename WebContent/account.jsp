<%-- 
    Document   : account
    Created on : Mar 5, 2015, 5:42:43 PM
    Author     : Samuel
--%>

<%@page import="controller.User"%>
<%
    //Verify that the user is logged in before accessing this page
    RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //Start a request dispatcher for the index page
    String loggedIn = (String) session.getAttribute("loggedIn"); //Get the "logged in" attribute from the session
    User usr = null; //Initalize an empty user object

    if (loggedIn == null) { //Prevent null pointer error
        loggedIn = "";
    }

    if (loggedIn != "true") { //If the "logged in" attribute is not set in the session
        //The user is not logged in; Tell them they must log in first
        request.setAttribute("loginMessage", "You must log in first before attempting to view your account.");
        rd.forward(request, response); //Redirect the user
    } else { //The user is logged in
        //Obtain the user object from the session
        usr = (User) session.getAttribute("user");
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script> <!-- jQuery Library -->
        <link rel="stylesheet" type="text/css" href="css/account.css" /> <!-- Style sheet -->
        <link rel="stylesheet" type="text/css" href="css/goldbutton.css" /> <!-- Style sheet for gold buttons -->
        <script type="text/javascript">
            $(document).ready(function () { //When the page loads
                $("#account_button").hide(); //Hide the account button
                setTimeout("HideVaultVideo()", 5000); //Show the valut video for 5 seconds
            });
            function HideVaultVideo() { //Hides the vault video
                $("#vault_outside_vid").fadeOut(250).promise().always(function () { //Make the vault video fade out
                    setTimeout("ShowMoneyVideo()", 1000); //Show the falling moey video after the vault video disappears
                });
            }
            function ShowMoneyVideo() { //Show the falling money backgound video
                money_fall_vid.play(); //Play the video
                $("#money_fall_vid").fadeIn(2000); //Make the video fade in over a 2 second period
                $("#account_info").fadeIn(2000); //Make the account information fade in over a 2 second period
            }
        </script>
    </head>
    <body>
        <div id="page_wrapper">
            <video id="vault_outside_vid" autoplay>
                <source src="vid/vault.mp4" type="video/mp4">
            </video>
            <video id="money_fall_vid" loop>
                <source src="vid/money_fall.mp4" type="video/mp4">
            </video>
            <div id="account_info">
                <div id="header">Account Information for <%= usr.GetFirstName() + " " + usr.GetLastName()%></div>
                <table>
                    <tr>
                        <td>
                            <div id="account_balance">Checking Balance: $</div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="account_balance">Savings Balance: $</div>
                        </td>
                    </tr>
                </table>
                <br />
                <br />
                <%@include file="nav.jsp"%>
                <%@include file="logout.jsp"%>
            </div>
        </div>
    </body>
</html>
