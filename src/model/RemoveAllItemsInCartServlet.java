package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Cart;
import controller.User;

/**
 * Servlet implementation class RemoveAllItemsInCartServlet
 */
@WebServlet("/RemoveAllItemsInCartServlet")
public class RemoveAllItemsInCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveAllItemsInCartServlet() {
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
		String loggedIn;
		Cart cart;
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		
    	rd = request.getRequestDispatcher("view_cart.jsp");
    	loggedIn = (String) session.getAttribute("loggedIn");
    	if (loggedIn == null){
    		loggedIn = "";
    	}
    	if (loggedIn != "true" || !usr.getAccountType().equals("B")){
	    	request.setAttribute("indexMessage","You must be logged in as a buyer to perform actions on the cart");
    	}else{
    		session.setAttribute("cart",new Cart()); //Get the user object from the session	
	    	request.setAttribute("cartMessage","All items removed successfully");
    	}
        rd.forward(request, response);
	}

}
