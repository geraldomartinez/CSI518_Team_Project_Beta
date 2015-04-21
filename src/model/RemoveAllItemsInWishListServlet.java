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
import controller.WishList;
import controller.User;
import controller.CartItem;

/**
 * Servlet implementation class RemoveAllItemsInWishListServlet
 */
@WebServlet("/RemoveAllItemsInWishListServlet")
public class RemoveAllItemsInWishListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveAllItemsInWishListServlet() {
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
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		User usr = (User) session.getAttribute("user"); //Get the user object from the session	
    	loggedIn = (String) session.getAttribute("loggedIn");
    	
    	if (loggedIn == null){
    		loggedIn = "";
    	}
    	
    	if (loggedIn != "true"){
			request.setAttribute("indexMessage", "You must logged in to perform this request");
    	}else if (!usr.getAccountType().equals("B")){
	    	request.setAttribute("indexMessage","Only buyers are allowed to access wish list features");
    	}else{
    		rd = request.getRequestDispatcher("view_wishlist.jsp");
			if (AuthDAO.ClearWishlist(usr.GetUserID())){
				request.setAttribute("wishListMessage","All items removed successfully");
			}else{
				request.setAttribute("wishListMessage","Failed to delete all items from the wish list");
			}
			session.setAttribute("wishlist", AuthDAO.ReturnUserWishlist(usr.GetUserID()));
    	}
        rd.forward(request, response);
	}

}
