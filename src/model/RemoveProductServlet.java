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
@WebServlet("/RemoveProduct")
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

		int sellerID;
		int productID;
		String checkDelBtn;

		sellerID = Integer.parseInt(request.getParameter("sellerID"));
		productID = Integer.parseInt(request.getParameter("productID"));
		checkDelBtn = request.getParameter("check_deletion");
		//TODO: Verify that the seller is actually sellerID == seller who is logged in
	
	
		try {
			if(usr.getAccountType()=="S")// To check If the logged in user is a seller
			{
				AuthDAO.VerifySellerID(sellerID);// Verify that the seller is actually sellerID == seller who is logged in
			}
			else if(usr.getAccountType()=="B")//To check If the logged in user is a buyer
			{
				System.out.println("Buyers are not allowed to delete a product");// buyers are not allowed to remove a product
			}
			else// if the logged in user is an Admin
			{
				AuthDAO.VerifySellerID(sellerID); // Admin is allowed to remove a product
			}
			if (checkDelBtn == null) {
				checkDelBtn = "";
			}
			else if (checkDelBtn.length() != 0) {
				AuthDAO.removeProduct(sellerID, productID);
	    	System.out.println("Product with productID:"+productID+"has been deleted");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
