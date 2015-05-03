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
import controller.Product;
import controller.User;

/**
 * Servlet implementation class UpdateOrderQuantityServlet
 */
@WebServlet("/UpdateOrderQuantityServlet")
public class UpdateOrderQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOrderQuantityServlet() {
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
		String quantity = request.getParameter("quantity");
    	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
    	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
    	int oldQty = -1;
		
		if (orderID ==  null){
			orderID = "";
		}
		if (productID ==  null){
			productID = "";
		}
		if (quantity ==  null){
			quantity = "";
		}

		if (navLoggedIn != "true"){
			rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("indexMessage", "You must be logged in to update items in an order");
		}else{
			rd = request.getRequestDispatcher("view_order.jsp?orderID="+orderID);
			try{
				oldQty = AuthDAO.GetQtyOfItemInOrder(Integer.parseInt(productID),Integer.parseInt(orderID));
	    		Product prd = AuthDAO.getProductById(Integer.parseInt(productID));
				if (Integer.parseInt(quantity) <= 0){
					request.setAttribute("orderMessage", "Number entered must be greater than zero");
				}else if (prd.GetNumInStock() < (Integer.parseInt(quantity)- oldQty)){
			    	request.setAttribute("orderMessage","Failed to update quantity to "+quantity+" - only "+Integer.toString(prd.GetNumInStock())+" more currently left in stock");
	    		}else if (AuthDAO.UpdateOrderQuantity(usr.GetUserID(), Integer.parseInt(orderID), Integer.parseInt(productID), Integer.parseInt(quantity))){
					request.setAttribute("orderMessage", "Quantity update successful");
				}else{
					request.setAttribute("orderMessage", "Quantity update failed");
	    		}
			}catch (Exception ex){
				// Log the exception
				Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
				request.setAttribute("orderMessage", "Quantity update failed");
			}
		}
		rd.forward(request, response);
	}

}
