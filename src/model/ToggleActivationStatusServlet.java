package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.AuthDAO;
import controller.User;

/**
 * Servlet implementation class ToggleActivationStatusServlet
 */
@WebServlet("/ToggleActivationStatusServlet")
public class ToggleActivationStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToggleActivationStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		RequestDispatcher rd = request.getRequestDispatcher("view_sellers.jsp");
		User usr = (User) session.getAttribute("user"); //Obtain the user object from the session (if there is one)
		
		String loggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
		int userID = Integer.parseInt(request.getParameter("userID"));
		String approveBtn = request.getParameter("approve");
		String viewPage = request.getParameter("page");
		boolean verified = false;
		
		if (loggedIn == null) { //Prevent null pointer exception
			loggedIn = "";
		}
		
		if (approveBtn == null) {
			approveBtn = "";
		}		
		
		if (viewPage != null && viewPage.equals("buyer")){
			rd = request.getRequestDispatcher("view_buyers.jsp");
		}
		
		if (loggedIn == "")
		{
			request.setAttribute("adminMessage", "You must logged in to perform this request");
		}
		else
		{
			switch(usr.getAccountType()){
				case "B":
				case "S":
					request.setAttribute("adminMessage", "Only admins are allowed to toggle account status"); // Verify that the seller is actually sellerID == seller who is logged in
					break;
				case "A":
					System.out.println("A");
					verified = true;
					break;
			}
			if (verified){
				if (AuthDAO.ToggleAccountStatus(userID)){
					request.setAttribute("adminMessage", "Account activation status for account "+userID+" has been swapped successfully");
				}else{
					request.setAttribute("adminMessage", "Account activation status swap for account "+userID+" failed");
				}
			}
		}

		rd.forward(request, response);
	}

}
