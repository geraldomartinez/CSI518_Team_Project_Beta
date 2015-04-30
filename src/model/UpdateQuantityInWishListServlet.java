package model;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.AuthDAO;
import controller.Cart;
import controller.CartItem;
import controller.WishList;
import controller.User;

/**
 * Servlet implementation class UpdateQuantityInWishListServlet
 */
@WebServlet("/UpdateQuantityInWishListServlet")
public class UpdateQuantityInWishListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuantityInWishListServlet() {
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
		User usr = (User) session.getAttribute("user"); // Obtain the user
		String loggedIn = (String) session.getAttribute("loggedIn"); // Obtain the "logged in" attribute from the session
		WishList wishlist = (WishList) session.getAttribute("wishlist"); // Obtain the wishlist object from the session
		int productID = Integer.parseInt(request.getParameter("productID")); // Obtain the product ID from the page that called the servlet
		String delFromWish = request.getParameter("delFromWish");
		Cart cart = (Cart) session.getAttribute("cart"); 
		int newQuantity = Integer.parseInt(request.getParameter("quantity")); // Obtain the item quantity from the page that called the servlet
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");

		if (loggedIn == null) { // Prevent null pointer exception
			loggedIn = "";
		}
		if(delFromWish==null)
    	{
    		delFromWish="";
    	}
		if (loggedIn != "true") {
			request.setAttribute("indexMessage", "You must logged in to perform this request");
		} else {
			if (!usr.getAccountType().equals("B")) {
				request.setAttribute("indexMessage", "Only buyers are allowed to insert a product to cart");
			}else{
				
				if (AuthDAO.addItem2Wishlist(usr.GetUserID(), productID, newQuantity)) {
					
					wishlist.AddItem(productID);
	                wishlist.UpdateQuantity(productID, newQuantity);
	            	
	            	session.setAttribute("wishlist", wishlist);	 
	            	if (delFromWish.equals("true")){
	            		cart.AddItem(productID);
	            		session.setAttribute("cart",cart);
	            		rd = request.getRequestDispatcher("view_cart.jsp");
	            		request.setAttribute("cartMessage", "Product with ID " + productID + " has been added to cart");
	            	}else
	            	{
					request.setAttribute("wishListMessage", "Product with ID " + productID + " has been updated");
					rd = request.getRequestDispatcher("view_wishlist.jsp");
	            	}
				} else {
					request.setAttribute("wishListMessage", "Update of product with ID " + productID + " failed");
				}
			}
		}  	
        rd.forward(request, response);
		
	}

}
