package model;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.AuthDAO;
import controller.User;

/**
 * Servlet implementation class RemoveProduct
 */
@WebServlet("/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveProductServlet() {
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
		
		HttpSession session = request.getSession(true);
		RequestDispatcher rd = request.getRequestDispatcher("view_product_list.jsp");
		User usr = (User) session.getAttribute("user"); //Obtain the user object from the session (if there is one)
		String loggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
		int productID = Integer.parseInt(request.getParameter("productID"));
		String delBtn = request.getParameter("delete");
		boolean verified = false;
		
		if (loggedIn == null) { //Prevent null pointer exception
			loggedIn = "";
		}
		
		if (delBtn == null) {
			delBtn = "";
		}		
		
		if (loggedIn == "")
		{
			request.setAttribute("productListMessage", "You must be logged in to perform this request");
		}
		else
		{
			switch(usr.getAccountType()){
				case "B":
					System.out.println("B");
					request.setAttribute("productListMessage", "Buyers are not allowed to delete a product"); // buyers are not allowed to remove a product
					break;
				case "S":
					System.out.println("S");
					verified = AuthDAO.VerifySellerID(usr.GetUserID()); // Verify that the seller is actually sellerID == seller who is logged in
					break;
				case "A":
					System.out.println("A");
					verified = true;
					break;
			}

			if (verified){
				if (delBtn.length() != 0){
					if (AuthDAO.removeProduct(usr.GetUserID(), productID)){
						request.setAttribute("productListMessage", "Product with ID " + productID + " has been deleted");
					}else{
						request.setAttribute("productListMessage", "Deletion of product with ID " + productID + " failed");
					}
				}else{
					request.setAttribute("productListMessage", "Invalid request");
				}
			}
		}

		rd.forward(request, response);
	}

}
