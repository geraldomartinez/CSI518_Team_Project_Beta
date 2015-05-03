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
import controller.Product;
import controller.User;

/**
 * Servlet implementation class AddReviewServlet
 */
@WebServlet("/AddReviewServlet")
public class AddReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReviewServlet() {
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
    	RequestDispatcher rd = request.getRequestDispatcher("view_product.jsp");
    	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
    	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		int productID = -1;
		int rating=0;
		String review="";
		String categoryID;
	
		int userID=usr.GetUserID();
		
		boolean insertproduct=true;
		review=request.getParameter("review");
			rating=Integer.parseInt(request.getParameter("Rating"));
			User user = (User) session.getAttribute("user");
			
			
			if(navLoggedIn=="")
			{
				navLoggedIn=null;
			}
			
			if(review==null)
			{
				review="";
			}
			
			
			//userID=user.GetUserID();
		//Product p =(Product) session.getAttribute("Product");

		//productID=Integer.parseInt(request.getParameter("ProductID"));
			productID=Integer.parseInt(request.getParameter("productID"));
			
			System.out.println("entered insert"+productID);
			
			
			if (navLoggedIn != "true") {
				request.setAttribute("productMessage", "You must logged in to perform this request");
			}else
		
		if(AuthDAO.insertreview(userID, productID, rating, review))
		{
			request.setAttribute("productMessage", "Product with ID " + productID + " has been reviewed");
			
			System.out.println("the prod id"+ productID);
			rd = request.getRequestDispatcher("/view_product.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("productMessage", "Review " + productID + " failed");
		}
		
	}

}
