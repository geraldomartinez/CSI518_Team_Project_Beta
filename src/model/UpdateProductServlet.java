package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import controller.Product;
import controller.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controller.AuthDAO;

/**
 * Servlet implementation class ProductServlet
 */
@MultipartConfig(location="/", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProductServlet() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
    	RequestDispatcher rd = request.getRequestDispatcher("edit_product.jsp");
    	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
    	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		int productID = -1;
		int sellerID;
		String name, description, specs, price, numInStock, updatebt, groundCost, twoCost, nextCost;
		String categoryID;
		String updateMessage = "";
		boolean updateproduct=true;
		name=request.getParameter("productName");
		description=request.getParameter("description");
		specs=request.getParameter("specs");
		price=request.getParameter("price");
		categoryID=request.getParameter("categoryID");
		numInStock=request.getParameter("quantity");
		updatebt=request.getParameter("updatebt");
		
		groundCost=request.getParameter("ground_cost");
		twoCost=request.getParameter("two_cost");
		nextCost=request.getParameter("next_cost");
		
		sellerID = usr.GetUserID();

		//Image
		//Part filePart=request.getPart("product_image");

		System.out.println(categoryID);
		if (name == null) {
        	name = "";
        }
		
        if (description == null) {
        	description = "";
        }
        
        if (specs == null) {
        	specs = "";
        }
        
        if (price == null) {
        	price = "";
        }
        
        if (numInStock == null) {
        	numInStock = "";
        }
        
        if(updatebt==null)
        {
        	updatebt="";
        }
        
        if (updatebt.length() != 0) { //If the submit button was pressed
            
            if (name.equals("")) {
                if (!updateMessage.equals("")) {
                	updateMessage += "<br />";
                }
                updateMessage += "You did not enter the product name";
                updateproduct = false;
            }else{
            	name = name.replace("'","\\'");
            } 
            
            if (description.equals("")) {
                if (!updateMessage.equals("")) {
                	updateMessage += "<br />";
                }
                updateMessage += "You did not enter the description";
                updateproduct = false;
            }else{
            	description = description.replace("'","\\'");
            }
            
            if (specs.equals("")) {
                if (!updateMessage.equals("")) {
                	updateMessage += "<br />";
                }
                updateMessage += "You did not enter specs";
                updateproduct = false;
            }else{
            	specs = specs.replace("'","\\'");
            }
            
            if (price.equals("")) {
                if (!updateMessage.equals("")) {
                	updateMessage += "<br />";
                }
                updateMessage += "You did not enter the price of product";
                updateproduct = false;
            }else{
            	specs = specs.replace("'","\\'");
            }
            
          
            
        	//Try to parse a number out of the given shipping costs. If the parse fails, set the string to empty string
        	try{
        		Float.parseFloat(groundCost);
        	}catch(Exception e){
        		groundCost = "";
        	}
        	
        	try{
        		Float.parseFloat(twoCost);
        	}catch(Exception e){
        		twoCost = "";
        	}
        	
        	try{
        		Float.parseFloat(nextCost);
        	}catch(Exception e){
        		nextCost = "";
        	}
        	
            if (groundCost.equals("") && twoCost.equals("") && nextCost.equals("")) {
                if (!updateMessage.equals("")) {
                	updateMessage += "<br />";
                }
                updateMessage += "You did not enter at least one valid shipping option cost";
                updateproduct = false;
            }else{
            	
            	groundCost = groundCost.replace("'","\\'");
            	twoCost = twoCost.replace("'","\\'");
            	nextCost = nextCost.replace("'","\\'");
            }         
            
            	
           
            if (updateproduct) {        		
                try {
					productID = AuthDAO.UpdateProductDetails(Integer.toString(sellerID), name, description, specs, price, categoryID, numInStock,  groundCost, twoCost, nextCost);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (productID == -1) {
                	updateMessage = "Product update failed.";
                }else{
                	updateMessage = "Product updated successfully";
                }
            }
        } else if (updatebt.length() == 0) { //If the check username button was not pressed
            updateMessage += "An unexpected error has occured"; //There was an error in http request
        }

        request.setAttribute("updateProductMessage", updateMessage);
        rd.forward(request, response);
        
	}

}
