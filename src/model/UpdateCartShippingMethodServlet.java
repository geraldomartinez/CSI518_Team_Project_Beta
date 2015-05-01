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
import controller.WishList;

/**
 * Servlet implementation class UpdateCartShippingMethodServlet
 */
@WebServlet("/UpdateCartShippingMethodServlet")
public class UpdateCartShippingMethodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCartShippingMethodServlet() {
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
		User usr = (User) session.getAttribute("user"); //Get the user object from the session
		String strNewShippingMethod = request.getParameter("shipping_method");
		
    	loggedIn = (String) session.getAttribute("loggedIn");
    	if (loggedIn == null){
    		loggedIn = "";
    	}
    	if (strNewShippingMethod == null || strNewShippingMethod == ""){
    		strNewShippingMethod = "0";
    	}
    	if (loggedIn == "true" && !usr.getAccountType().equals("B")){
	    	request.setAttribute("indexMessage","You must be logged in as a buyer to access cart features");
    	}else{

	    	rd = request.getRequestDispatcher("view_cart.jsp");
    		cart = (Cart) session.getAttribute("cart"); //Get the cart from the session
	    	request.setAttribute("cartMessage","Shipping method updated successfully");
    		System.out.println("New Shipping Method: ");
    		System.out.println(strNewShippingMethod.charAt(0));
    		switch(strNewShippingMethod.charAt(0)){
    			case '1':
    	    		cart.SetShippingMethod((byte) 1);
    	    		break;
    			case '2':
    	    		cart.SetShippingMethod((byte) 2);
    	    		break;
    			case '3':
    	    		cart.SetShippingMethod((byte) 3);
    	    		break;
    			default:
    		    	request.setAttribute("cartMessage","Shipping method update failed");
    				break;
    		}
    		session.setAttribute("cart",cart); //Store the new shipping method value in the session
    	}	
        rd.forward(request, response);
		
	}

}
