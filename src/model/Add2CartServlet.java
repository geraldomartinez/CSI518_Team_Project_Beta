package model;

import java.io.IOException;

import controller.Cart;
import controller.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Add2CartServlet
 */
@WebServlet("/Add2CartServlet")
public class Add2CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add2CartServlet() {
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
		int productID = -1;
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		String strProductID = request.getParameter("productID");
		User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		if (strProductID == null){
	    	request.setAttribute("indexMessage","No product ID given");
		}else{
	    	productID = Integer.parseInt(strProductID);
	    	loggedIn = (String) session.getAttribute("loggedIn");
	    	if (loggedIn == null){
	    		loggedIn = "";
	    	}
	    	if (loggedIn == "true" && !usr.getAccountType().equals("B")){
		    	request.setAttribute("indexMessage","You must be logged in as a buyer to add items to the cart");
	    	}else{
		    	rd = request.getRequestDispatcher("view_cart.jsp");
	    		cart = (Cart) session.getAttribute("cart"); //Get the cart from the session
	    		cart.AddItem(productID);
	    		session.setAttribute("cart",cart); //Set the cart in the session with the new item added
		    	request.setAttribute("cartMessage","Item added successfully to cart");
	    	}
	    	
		}    	
        rd.forward(request, response);
	}

}
