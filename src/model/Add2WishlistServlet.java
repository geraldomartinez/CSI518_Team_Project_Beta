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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		RequestDispatcher rd = request
				.getRequestDispatcher("view_wishlist.jsp");
		User usr = (User) session.getAttribute("user"); // Obtain the user
		WishList wl=(WishList)session.getAttribute("wishlistID");												// object from the
														// session (if there is
														// one)
		String loggedIn = (String) session.getAttribute("loggedIn"); // Obtain
																		// the
																		// "logged in"
																		// attribute
																		// from
																		// the
																		// session
		int productID = Integer.parseInt(request.getParameter("productID"));
		String addBtn = request.getParameter("addToWishBtn");
		boolean verified = false;

		if (loggedIn == null) { // Prevent null pointer exception
			loggedIn = "";
		}

		if (addBtn == null) {
			addBtn = "";
		}

		if (loggedIn == "") {
			request.setAttribute("wishListMessage",
					"You must logged in to perform this request");
		} else {
			if (usr.getAccountType()=="B") {
			}
			else
			{
				request.setAttribute("wishListMessage",
						"Only buyers are  allowed to insert a product to wishlist");  
			
			}

			if (verified) {
				if (addBtn.length() != 0) {
					if(wl.GetWishlistID()!=0)
					if (AuthDAO.addItem2Wishlist(wl.GetWishlistID(),productID,wl.NumItemsInCart())) { // how to get number of items?
						request.setAttribute("wishListMessage",
								"Product with ID " + productID
										+ " has been added");
					} else {
						request.setAttribute("wishListMessage",
								"Insertion of product with ID " + productID
										+ " failed");
					}
				} else {
					request.setAttribute("wishListMessage",
							"Invalid request");
				}
			}
		}

		rd.forward(request, response);
	}

}
