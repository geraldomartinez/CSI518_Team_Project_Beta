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
 * Servlet implementation class ApproveSellerServlet
 */
@WebServlet("/ApproveSellerServlet")
public class ApproveSellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveSellerServlet() {
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
		int sellerID = Integer.parseInt(request.getParameter("sellerID"));
		String approveBtn = request.getParameter("approve");
		boolean verified = false;
		
		if (loggedIn == null) { //Prevent null pointer exception
			loggedIn = "";
		}
		
		if (approveBtn == null) {
			approveBtn = "";
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
					request.setAttribute("adminMessage", "Only admin accounts are allowed to approve a seller"); // Verify that the seller is actually sellerID == seller who is logged in
					break;
				case "A":
					verified = true;
					break;
			}
			if (verified){
				if (approveBtn.length() != 0){
					if (AuthDAO.verifySeller(sellerID)){
						request.setAttribute("adminMessage", "Seller with ID " + sellerID + " has been verified");
					}else{
						request.setAttribute("adminMessage", "Seller with ID  " + sellerID + " has failed");
					}
				}else{
					request.setAttribute("adminMessage", "Invalid request");
				}
			}
		}

		rd.forward(request, response);
	}
}
