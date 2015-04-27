package model;

import java.io.IOException;

import controller.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.AuthDAO;

/**
 * Servlet implementation class SurveyServlet
 */
@WebServlet("/SurveyServlet")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SurveyServlet() {
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
	RequestDispatcher rd = request.getRequestDispatcher("survey.jsp");
        
		int  productID,responseID = 1, questionID ;
		 String color,priceRange, SurveyMessage="";
		int categoryID,sellerID;
		 String SubmitSurveyBtn = request.getParameter("SubmitSurveyBtn");
		 String responseText="";
		color=request.getParameter("color");
		priceRange = request.getParameter( "price" );
		categoryID= Integer.parseInt(request.getParameter( "categoryID" ) );
		int use= Integer.parseInt(request.getParameter("use"));
		 sellerID=Integer.parseInt(request.getParameter("sellerID"));
		responseText="user entered color:"+color+",priceRange:"+priceRange+",purpose of use option is:"+use+",and manufacturer with sellerID:"+sellerID;
		System.out.println(priceRange);
		System.out.println(categoryID);
		System.out.println(sellerID);
		System.out.println(color);
		System.out.println(use);
		System.out.println(responseText);
		
		if (SubmitSurveyBtn.length() != 0){
	if(color==null)
		color="";
	else  AuthDAO.getProductByColor(color);
	AuthDAO.getProductByCategory(categoryID);
		if(priceRange==null)
			priceRange="";
		else AuthDAO.getProductByPrice(priceRange);
		 AuthDAO.getProductByPurpose(use);
		 AuthDAO.getProductByManufacturer(sellerID);
		 try {
				productID = AuthDAO.InsertSurveyResponses(responseID,responseText);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         if (responseID == -1) {
         	SurveyMessage = "Product insert failed.";
         }else{
        	 SurveyMessage = "Product inserted successfully";
         }
     }
  else  { //If the check username button was not pressed
	 SurveyMessage += "An enexpected error has occured"; //There was an error in http request
 }

 
 
	 request.setAttribute("SurveyMessage", "Thank you for taking our survey!");
     rd.forward(request, response);

			
		}
	}



    