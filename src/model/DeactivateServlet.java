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
import model.LogoutServlet;

/**
 * Servlet implementation class DeactivateServlet
 */
@WebServlet("/DeactivateServlet")
public class DeactivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeactivateServlet() {
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
		RequestDispatcher rd = request.getRequestDispatcher("deactivate.jsp");
		User usr = (User) session.getAttribute("user"); //Obtain the user object from the session (if there is one)
		String loggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
		//int productID = Integer.parseInt(request.getParameter("productID"));
		String deactivate = request.getParameter("deactivate");
		boolean verified = false;
		String accountype=usr.getAccountType();
		//System.out.println(accountype+"mine");
		int username=usr.GetUserID();
		
		if (loggedIn == null) { //Prevent null pointer exception
			loggedIn = "";
		}
		
		if (deactivate == null) {
			deactivate = "";
		}		
		
		if (loggedIn == "")
		{
			request.setAttribute("deactivatemessage", "You must logged in to deactivate your account");
		}
		else
		{
			
					verified = true;
					
			}
          System.out.println(verified);
          
			if (verified){
				
					
						try {
							if (AuthDAO.deactivateaccount(accountype, username)){
								request.setAttribute("loginMessage", "The account has been deactivated successfully");
								LogoutServlet.logout(request,response,true);
							}else{
								request.setAttribute("deactivatemessage", "Account deactivation failed");
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}else{
					request.setAttribute("deactivatemessage", "Invalid request");
				}
			
		

		rd.forward(request, response);
	}
	

}
