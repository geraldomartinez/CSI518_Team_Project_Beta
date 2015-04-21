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
import controller.User;
import controller.WishList;
import controller.CartItem;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		User usr = (User) session.getAttribute("user"); // Obtain the user
		String loggedIn = (String) session.getAttribute("loggedIn"); // Obtain the "logged in" attribute from the session
		WishList wishlist = (WishList) session.getAttribute("wishlist"); // Obtain the wishlist object from the session
		int productID = Integer.parseInt(request.getParameter("productID")); // Obtain the product ID from the page that called the servlet
		int addQuantity = Integer.parseInt(request.getParameter("quantity")); // Obtain the item quantity from the page that called the servlet
		RequestDispatcher rd = request.getRequestDispatcher("view_product.jsp?productID="+productID);
		List<CartItem> itemList;
		int currItemQuantity = 0;

		if (loggedIn == null) { // Prevent null pointer exception
			loggedIn = "";
		}

		if (loggedIn != "true") {
			request.setAttribute("productMessage", "You must logged in to perform this request");
		} else {
			if (!usr.getAccountType().equals("B")) {
				request.setAttribute("productMessage", "Only buyers are allowed to insert a product to cart");
			}else{
				//Find the item's current quantity in the wish list object
				itemList = wishlist.GetAllItems();
				if (!itemList.isEmpty()){
					for (int i=0; i < itemList.size(); i++){
						if (itemList.get(i).GetProductID() == productID){
							currItemQuantity = itemList.get(i).GetQuantity();
						}
					}
				}
				if (AuthDAO.addItem2Wishlist(usr.GetUserID(), productID, (currItemQuantity+addQuantity))) {
					
					wishlist.AddItem(productID);
	            	if (addQuantity > 1){
	                	wishlist.UpdateQuantity(productID, (currItemQuantity+addQuantity));
	            	}
	            	
	            	session.setAttribute("wishlist", wishlist);	            	
					request.setAttribute("wishListMessage", "Product with ID " + productID + " has been added");
					rd = request.getRequestDispatcher("view_wishlist.jsp");
				} else {
					request.setAttribute("productMessage", "Insertion of product with ID " + productID + " failed");
				}
			}
		}

		rd.forward(request, response);
	}

}
