package model;

import java.io.IOException;

import controller.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AuthDAO;

/**
 * Servlet implementation class ProductServlet
 */
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		String productID,sellerID, name, description,specs, price, categoryid, numInStock,insertbt;
		String inputmessage = "";
		boolean insertproduct=true;
		productID=request.getParameter("productid");
		name=request.getParameter("productname");
		description=request.getParameter("description");
		specs=request.getParameter("specs");
		price=request.getParameter("price");
		categoryid=request.getParameter("categoryid");
		numInStock=request.getParameter("numinstock");
		insertbt=request.getParameter("insertbt");
		sellerID="1";
		
		if (productID == null) {
			productID = "";
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
        if (categoryid == null) {
        	categoryid = "";
        }
       
        if (numInStock == null) {
        	numInStock = "";
        }
        
        if(insertbt==null)
        {
        	insertbt="";
        }
        
        if (insertbt.length() != 0) { //If the submit button was pressed
            if (productID.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter the Product ID";
                insertproduct = false;
            }
            if (categoryid.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter  Category ID";
                insertproduct = false;
            }
            if (name.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter the product name";
                insertproduct = false;
            }
            if (description.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter the description";
                insertproduct = false;
            }
            if (specs.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter specs";
                insertproduct = false;
            }
            if (price.equals("")) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "You did not enter the price of product";
                insertproduct = false;
            }
            
           
            if (numInStock.length()!=10) {
                if (!inputmessage.equals("")) {
                	inputmessage += "<br />";
                }
                inputmessage += "The phone number you entered was not valid. Please enter a 10-digit phone number.";
                insertproduct = false;
            }
            
           
            if (insertproduct) {
                productID = AuthDAO.InsertProductDetails( sellerID, name, description, specs, price, categoryid, numInStock);
                if (productID == "-1") {
                	inputmessage = "Product  Insert Failed.";
               
                } else {
                	RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                    rd = request.getRequestDispatcher("index.jsp");
                    request.setAttribute("indexMessage", "<span style='color: green'>Registration for [" + name + "] succesful. Log in to view your account.</span>");
                    rd.forward(request, response);
                }
            }
        } else if (insertbt.length() == 0) { //If the check username button was not pressed
            inputmessage += "An enexpected error has occured"; //There was an error in http request
        }

        
        
        
        
        
        
        
        
        
        
        
        
	}

}
