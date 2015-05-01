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
 * Servlet implementation class UpdateWishListShippingMethodServlet
 */
@WebServlet("/UpdateWishListShippingMethodServlet")
public class UpdateWishListShippingMethodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateWishListShippingMethodServlet() {
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
		WishList wishlist;
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
	    	request.setAttribute("indexMessage","You must be logged in as a buyer to access wish list features");
    	}else{

	    	rd = request.getRequestDispatcher("view_wishlist.jsp");
	    	wishlist = (WishList) session.getAttribute("wishlist"); //Get the cart from the session
	    	request.setAttribute("cartMessage","Shipping method updated successfully");
    		System.out.println("New Shipping Method: ");
    		System.out.println(strNewShippingMethod.charAt(0));
    		switch(strNewShippingMethod.charAt(0)){
    			case '1':
    				wishlist.SetShippingMethod((byte) 1);
    	    		break;
    			case '2':
    				wishlist.SetShippingMethod((byte) 2);
    	    		break;
    			case '3':
    				wishlist.SetShippingMethod((byte) 3);
    	    		break;
    			default:
    		    	request.setAttribute("cartMessage","Shipping method update failed");
    				break;
    		}
    		session.setAttribute("wishlist",wishlist); //Store the new shipping method value in the session
    	}	
        rd.forward(request, response);
		
	}

}
