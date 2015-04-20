package model;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import controller.User;
import controller.WishList;

/**
 * Servlet implementation class AddWishListServlet
 */
@WebServlet("/Add2WishlistServlet")
public class Add2WishlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add2WishlistServlet() {
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
		    	request.setAttribute("indexMessage","You must be logged in as a buyer to add items to the wishlist");
	    	}else{
		    	rd = request.getRequestDispatcher("view_wishlist.jsp");
	    		wishlist = (WishList) session.getAttribute("wishList"); 
	    		wishlist.AddItem(productID);
	    		session.setAttribute("WishList",wishlist); //
		    	request.setAttribute("wishlistmessage","Item added successfully to wishlist");
	    	}
	    	
		}    	
        rd.forward(request, response);
	}

}
