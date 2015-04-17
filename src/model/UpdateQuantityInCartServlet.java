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
 * Servlet implementation class UpdateQuantityInCartServlet
 */
@WebServlet("/UpdateQuantityInCartServlet")
public class UpdateQuantityInCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuantityInCartServlet() {
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
		int newQty = 0;
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		String strProductID = request.getParameter("productID");
		String strNewQty = request.getParameter("quantity");
		User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		if (strProductID == null){
	    	request.setAttribute("indexMessage","No product ID given");
		}else{
	    	productID = Integer.parseInt(strProductID);
	    	newQty = Integer.parseInt(strNewQty);
	    	loggedIn = (String) session.getAttribute("loggedIn");
	    	if (loggedIn == null){
	    		loggedIn = "";
	    	}
	    	if (loggedIn == "true" && !usr.getAccountType().equals("B")){
		    	request.setAttribute("indexMessage","Only buyers and guests are allowed to access cart features");
	    	}else{
		    	rd = request.getRequestDispatcher("view_cart.jsp");
	    		cart = (Cart) session.getAttribute("cart"); //Get the cart from the session
	    		cart.UpdateQuantity(productID,newQty);
	    		session.setAttribute("cart",cart); //Set the cart in the session with the new item added
	    		if (newQty > 0){
	    			request.setAttribute("cartMessage","Item quantity successfully updated");
	    		}else{
			    	request.setAttribute("cartMessage","Item successfully removed from cart");
	    		}
	    	}
	    	
		}    	
        rd.forward(request, response);
		
	}

}
