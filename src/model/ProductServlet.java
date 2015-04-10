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


    	RequestDispatcher rd = request.getRequestDispatcher("add_product.jsp");
        
		String productID, sellerID, name, description, specs, price, categoryid, numInStock, insertbt;
		String inputMessage = "";
		boolean insertproduct=true;
		name=request.getParameter("productname");
		description=request.getParameter("description");
		specs=request.getParameter("specs");
		price=request.getParameter("price");
		categoryid=request.getParameter("categoryid");
		numInStock=request.getParameter("numinstock");
		insertbt=request.getParameter("insertbt");
		sellerID="4";
		
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
            if (categoryid.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter  Category ID";
                insertproduct = false;
            }
            if (name.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the product name";
                insertproduct = false;
            }
            if (description.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the description";
                insertproduct = false;
            }
            if (specs.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter specs";
                insertproduct = false;
            }
            if (price.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the price of product";
                insertproduct = false;
            }
            if (numInStock.equals("")) {
                if (!inputMessage.equals("")) {
                	inputMessage += "<br />";
                }
                inputMessage += "You did not enter the number of items in stock";
                insertproduct = false;
            }            
           
            if (insertproduct) {
                productID = AuthDAO.InsertProductDetails(sellerID, name, description, specs, price, categoryid, numInStock);
                if (productID == "-1") {
                	inputMessage = "Product  Insert Failed.";
                }
            }
        } else if (insertbt.length() == 0) { //If the check username button was not pressed
            inputMessage += "An enexpected error has occured"; //There was an error in http request
        }

        request.setAttribute("addProductMessage", inputMessage);
        rd.forward(request, response);
        
	}

}
