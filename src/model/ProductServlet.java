package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import controller.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    	RequestDispatcher rd = request.getRequestDispatcher("add_product.jsp");
        
		int productID;
		 String sellerID, name, description, specs, price, numInStock, insertbt;
		String categoryID;
		String inputMessage = "";
		boolean insertproduct=true;
		name=request.getParameter("productname");
		description=request.getParameter("description");
		specs=request.getParameter("specs");
		price=request.getParameter("price");
		categoryID=request.getParameter("categoryID");
		numInStock=request.getParameter("numinstock");
		insertbt=request.getParameter("insertbt");
		Part filePart=request.getPart("product_image");
		String fileName=filePart.getSubmittedFileName();
		InputStream fileInStream=filePart.getInputStream();
		byte[] fileByte = new byte[1];
		String extension="";
		FileOutputStream outFile;
		
		sellerID="4";
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
            
            	
           
            if (insertproduct) {
                productID = AuthDAO.InsertProductDetails(sellerID, name, description, specs, price, categoryID, numInStock);
                if (productID == -1) {
                	inputMessage = "Product insert failed.";
                }else{
                	inputMessage = "Product inserted successfully";
                }
                
                int i=fileName.lastIndexOf('.');
        		if(i>0)
        		{
        			extension=fileName.substring(i+1);
        		}
        		
        		outFile = new FileOutputStream((productID)+"."+extension);
        		while (fileInStream.read(fileByte) == 1){
        			outFile.write(fileByte[0]);
        		}
        		fileInStream.close();
        		outFile.close();
            }
        } else if (insertbt.length() == 0) { //If the check username button was not pressed
            inputMessage += "An enexpected error has occured"; //There was an error in http request
        }

        request.setAttribute("addProductMessage", inputMessage);
        rd.forward(request, response);
        
	}

}
