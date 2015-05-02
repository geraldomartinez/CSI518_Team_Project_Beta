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
import controller.Notification;
import controller.User;
import controller.WishList;
import controller.Product;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
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
		String loggedIn = (String) session.getAttribute("loggedIn");
		RequestDispatcher rd = request.getRequestDispatcher("checkout.jsp");
		User usr = (User) session.getAttribute("user"); //Get the user object from the session
    	Cart cart = null;

    	String checkoutMessage = "";
    	boolean shippingInfoIsValid = true;
    	String shippingName, shippingAddr, shippingCity, shippingState, shippingZip, paypalEmail;
    	shippingName = request.getParameter("shipping_name");
    	shippingAddr = request.getParameter("shipping_address");
    	shippingCity = request.getParameter("shipping_city");
    	shippingState = request.getParameter("shipping_state");
    	shippingZip = request.getParameter("shipping_zip");
    	paypalEmail = request.getParameter("paypal_email");
        int atpostn,dotpostn;
        int orderID = -1;
    	
    	if (loggedIn == null){
    		loggedIn = "";
    	}
    	if (shippingName == null){
    		shippingName = "";
    	}
    	if (shippingAddr == null){
    		shippingAddr = "";
    	}
    	if (shippingState == null){
    		shippingState = "";
    	}
    	if (shippingZip == null){
    		shippingZip = "";
    	}
    	if (paypalEmail == null){
    		paypalEmail = "";
    	}
    	
    	
    	if (loggedIn != "true" || !usr.getAccountType().equals("B")){
	    	request.setAttribute("indexMessage","You must be logged in as a buyer to checkout");
    	}else{

            if (shippingName.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a recipient name";
                shippingInfoIsValid = false;
            }

            if (shippingAddr.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a shipping address";
                shippingInfoIsValid = false;
            }

            if (shippingCity.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a shipping city";
                shippingInfoIsValid = false;
            }

            if (shippingState.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a shipping state";
                shippingInfoIsValid = false;
            }else if (shippingState.length() != 2){
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "Shipping state must be two letters";
                shippingInfoIsValid = false;
            }

            if (shippingZip.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a shipping zip code";
                shippingInfoIsValid = false;
            }else if (shippingZip.length() != 5){
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "Shipping zip code must be 5 numbers";
                shippingInfoIsValid = false;
            }else{
            	try{
            		Integer.parseInt(shippingZip);
            	}catch(Exception e){
                    if (!checkoutMessage.equals("")) {
                    	checkoutMessage += "<br />";
                    }
                    checkoutMessage += "Shipping zip code must be numeric";
                    shippingInfoIsValid = false;
            	}
            }

            if (paypalEmail.equals("")) {
                if (!checkoutMessage.equals("")) {
                	checkoutMessage += "<br />";
                }
                checkoutMessage += "You did not enter a paypal email address";
                shippingInfoIsValid = false;
            }else{
                atpostn=paypalEmail.indexOf("@");
                dotpostn=paypalEmail.lastIndexOf(".");
                if(atpostn<1 || dotpostn<atpostn+2 || dotpostn+2>=paypalEmail.length()){
                	 if (!checkoutMessage.equals("")){
                		 checkoutMessage += "<br />";
                     }
                	 checkoutMessage += "Paypal email address is not in a valid format.";
                     shippingInfoIsValid = false;
                }
            }
    		
            request.setAttribute("checkoutMessage",checkoutMessage);
            
            if (shippingInfoIsValid){
            	cart = (Cart) session.getAttribute("cart"); //Get the cart from the session
            	if ((orderID = AuthDAO.checkout(usr.GetUserID(),cart,shippingName,shippingAddr,shippingCity,shippingState,shippingZip,paypalEmail)) != -1){
            		
            		//Notify sellers for each product sold
            		List<CartItem> items = cart.GetAllItems();
            		for(int i = 0; i < items.size(); i++){
            			
            			int toUserID = AuthDAO.getProductById(items.get(i).GetProductID()).GetSellerID();
            			Notification notification = new Notification(-1, toUserID, 'O', 
            					"One of your products has been purchased", usr.GetUserID(), orderID, null);
            			if(AuthDAO.notifyUser(notification)){
            				System.out.println("Notification inserted successfully");
            			}
            			else{
            				System.out.println("Notification insertion failed");
            			}
            		}
            		
            		
            		
		    		rd = request.getRequestDispatcher("index.jsp");
		    		session.setAttribute("cart",new Cart()); //Empty the cart
			    	request.setAttribute("indexMessage","Checkout complete! Thank you for your purchase. Your order confirmation ID is: "+Integer.toString(orderID));
            	}else{
    		    	request.setAttribute("checkoutMessage","Checkout failed. Please try again, or contact the store for assistance.");
                	
                }
            }
    	}
    	
        rd.forward(request, response);
	}

}
