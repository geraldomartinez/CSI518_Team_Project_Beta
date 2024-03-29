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
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
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
    	RequestDispatcher rd = request.getRequestDispatcher("add_product.jsp");
    	String navLoggedIn = (String) session.getAttribute("loggedIn"); //Obtain the "logged in" attribute from the session
    	User usr = (User) session.getAttribute("user"); //Get the user object from the session	
		int productID = -1;
		int sellerID;
		String strProductID, name, description, specs, price, numInStock, insertbt, groundCost, twoCost, nextCost;
		String categoryID;
		String inputMessage = "", messageAttribute = "addProductMessage";
		boolean insertproduct=true;
		strProductID=request.getParameter("productID");
		name=request.getParameter("productname");
		description=request.getParameter("description");
		specs=request.getParameter("specs");
		price=request.getParameter("price");
		categoryID=request.getParameter("categoryID");
		numInStock=request.getParameter("numinstock");
		insertbt=request.getParameter("insertbt");
		
		groundCost=request.getParameter("ground_cost");
		twoCost=request.getParameter("two_cost");
		nextCost=request.getParameter("next_cost");
		
		sellerID = usr.GetUserID();

		//Image
		Part filePart=request.getPart("product_image");
		if (filePart == null){
			System.out.println(filePart.getName());
		}else{
			System.out.println("filePart is null");
		}
		
		if (strProductID == null) {
			strProductID = "";
        }
		
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
        
        if(insertbt==null)
        {
        	insertbt="";
        }
        
        if (insertbt.length() != 0) { //If the submit button was pressed
            
            if (name.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the product name";
                insertproduct = false;
            }else{
            	name = name.replace("'","\\'");
            } 
            
            if (description.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the description";
                insertproduct = false;
            }else{
            	description = description.replace("'","\\'");
            }
            
            if (specs.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter specs";
                insertproduct = false;
            }else{
            	specs = specs.replace("'","\\'");
            }
            
            if (price.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the price of product";
                insertproduct = false;
            }else{
            	specs = specs.replace("'","\\'");
            }
            
            if (numInStock.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the number of items in stock";
                insertproduct = false;
            }else{
            	numInStock = numInStock.replace("'","\\'");
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
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter at least one valid shipping option cost";
                insertproduct = false;
            }else{
            	
            	groundCost = groundCost.replace("'","\\'");
            	twoCost = twoCost.replace("'","\\'");
            	nextCost = nextCost.replace("'","\\'");
            }         
            
            	
           
            if (insertproduct) {        		
                try {
                	if (!strProductID.equals("")){ //If updating an existing product
                		productID = AuthDAO.UpdateProductDetails(Integer.parseInt(strProductID), ((usr.getAccountType().equals("A"))?true:false), Integer.toString(sellerID), name, description, specs, price, categoryID, numInStock, filePart, groundCost, twoCost, nextCost);
                		rd = request.getRequestDispatcher("view_product.jsp?productID="+productID);
                		messageAttribute = "viewProductMessage";
                	}else{
                		productID = AuthDAO.InsertProductDetails(Integer.toString(sellerID), name, description, specs, price, categoryID, numInStock, filePart, groundCost, twoCost, nextCost);
                	}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (productID == -1) {
                	inputMessage = "Product "+((!strProductID.equals(""))?"insert":"update")+" failed.";
                }else{
                	inputMessage = "Product "+((!strProductID.equals(""))?"inserted":"updated")+" successfully";
                }
            }
        } else if (insertbt.length() == 0) { //If the check username button was not pressed
            inputMessage += "An enexpected error has occured"; //There was an error in http request
        }

        request.setAttribute(messageAttribute, inputMessage);
        rd.forward(request, response);
        
	}

}
