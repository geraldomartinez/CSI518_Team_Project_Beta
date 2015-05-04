package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.AuthDAO;
import controller.Notification;
import controller.Product;
import controller.User;

/**
 * Servlet implementation class CancelItemServlet
 */
@WebServlet("/CancelItemServlet")
public class CancelItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelItemServlet() {
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
		RequestDispatcher rd;
		String orderID = request.getParameter("orderID");
		String productID = request.getParameter("productID");
    	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
    	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		
		if (orderID ==  null){
			orderID = "";
		}
		if (productID ==  null){
			productID = "";
		}

		if (navLoggedIn != "true"){
			rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("indexMessage", "You must be logged in to cancel items from an order");
		}else{
			rd = request.getRequestDispatcher("view_order.jsp?orderID="+orderID);
			try{
				if (AuthDAO.CancelItem(usr.GetUserID(), Integer.parseInt(orderID), Integer.parseInt(productID))){
					Product prd = AuthDAO.getProductById(Integer.parseInt(productID));
					
					//if the user cancelling the item is a seller or an admin...
					if(usr.getAccountType().equals("S") || (usr.getAccountType().equals("A"))){
						int buyerID = AuthDAO.getOrderBuyer(Integer.parseInt(orderID));
						Notification notification = new Notification(-1, buyerID, 'C', 
	        					"The order of item - " + prd.GetProductName() + " has been cancelled by the seller.", 
	        					usr.GetUserID(), Integer.parseInt(orderID), null);
	        			if(AuthDAO.notifyUser(notification)){
	        				System.out.println("Notification inserted successfully");
	        			}
	        			else{
	        				System.out.println("Notification insertion failed");
	        			}
						
						
						
					}else { //Buyer is cancelling the order...
						int sellerID = prd.GetSellerID();
						Notification notification = new Notification(-1, sellerID, 'C', 
	        					"The order of item - " + prd.GetProductName() + " has been cancelled by the buyer.", 
	        					usr.GetUserID(), Integer.parseInt(orderID), null);
	        			if(AuthDAO.notifyUser(notification)){
	        				System.out.println("Notification inserted successfully");
	        			}
	        			else{
	        				System.out.println("Notification insertion failed");
	        			}
					}
					
					
        			request.setAttribute("orderMessage", "Cancellation successful");
        			
				}else{
					request.setAttribute("orderMessage", "Cancellation failed");
				}
			}catch (Exception ex){
				// Log the exception
				Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
				request.setAttribute("orderMessage", "Cancellation failed");
			}
		}
		rd.forward(request, response);
	}

}
