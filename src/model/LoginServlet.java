package model;

import controller.AuthDAO;
import controller.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel
 */
@WebServlet(urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		User usr = new User();

		String email;
		String password;
		int checkResponse;
		boolean sellerIsVerified = false;

		email = request.getParameter("email");
		password = request.getParameter("password");

		// Prevent null pointer exception
		if (email == null) {
			email = "";
		}
		if (password == null) {
			password = "";
		}

		if (email.equals("") && password.equals("")) {
			request.setAttribute("loginMessage", "No email or password given");
		} else if (email.equals("")) {
			request.setAttribute("loginMessage", "No email given");
		} else if (password.equals("")) {
			request.setAttribute("loginMessage", "No password given");
		} else {
			checkResponse = AuthDAO.checkEmailPass(email, password);			
			if (checkResponse > -1) {
				session = request.getSession(true);
				session.setAttribute("loggedIn", "true");
				usr = AuthDAO.getUserById(checkResponse);
				session.setAttribute("user", usr);
				rd = request.getRequestDispatcher("index.jsp");
				request.setAttribute("indexMessage", "<span style='color: green;'>Login Successful</span>");
			} else if (checkResponse == -2) {
				request.setAttribute("loginMessage", "Database Connection Error");
			} else if (checkResponse == -3) {
				request.setAttribute("loginMessage", "Your seller account has not been verified yet. You will receive an email when an admin has verified your account.");
			} else { // Invalid email or password
				request.setAttribute("loginMessage", "Invalid email or password");
			}
		}

		try {
			AuthDAO.DB_Close();
		} catch (Throwable e) {
			request.setAttribute(
					"loginMessage",
					request.getAttribute("loginMessage") + "<br />"
							+ e.toString());
		}

		rd.forward(request, response);
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * • Start Session • Add Code to get form parameter values • Validate
		 * form • Check values against stored data (“hard coded for now in a
		 * variable”) • Set values to session attributes • Forward (request,
		 * response) • *Handle for errors within all of these steps
		 */
		processRequest(request, response);
	}
}
