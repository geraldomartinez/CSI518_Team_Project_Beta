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
       		
       		#login_form_wrapper{
       			margin-top: 10px;
       		}
       		
       		#login_form input{
       			margin-top: 5px;
       		}
       		
       		#login_form button{
       			margin-top: 10px;
       		}
       	</style>
	</head>
	<body>
        <%@include file="top_menu.jsp"%>
        <br />
        <div id="page_content_wrapper">
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
                request.setAttribute("indexMessage", "You are already logged in");
                rd.forward(request, response); //Forward the user with the response above
            }
        %>
            <h1 id="header">Great Danes Electronics</h1>   
            <h3 id="sub_header">Please log in to view your account</h3>
            <%
                String loginMessage = (String) request.getAttribute("loginMessage"); //Obtain the login message from the session
                if (loginMessage == null) { //Prevent null pointer exception
                    loginMessage = "";
                }
            %>
            <div id="login_message" class="message"><%=loginMessage%></div>
        	<br />
            <div id="login_form_wrapper">
                <form id="login_form" action="LoginServlet" method="POST">
                    <input name="email" type="text" placeholder="Email"/>
                    <br />
                    <input name="password" type="password" placeholder="Password"/>
                    <br />
                    <button type="submit" class="gold_button" name="login"><span>Log in</span></button>
                </form>
            </div>
        </div>
	</body>
</html>