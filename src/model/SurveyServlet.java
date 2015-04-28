package model;

import java.io.IOException;
import java.sql.SQLException;
import controller.User;

import controller.Product;
import controller.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	 
	 int userID= User.GetUserID();
		
		 String color,priceRange, SurveyMessage="";
		int categoryID,sellerID;
		 String SubmitSurveyBtn = request.getParameter("SubmitSurveyBtn");
		 String responseText="";
			
		color=request.getParameter("color");
		priceRange = request.getParameter( "price" );
		categoryID= Integer.parseInt(request.getParameter( "categoryID" ) );
		int use= Integer.parseInt(request.getParameter("use"));
		 sellerID=Integer.parseInt(request.getParameter("sellerID"));
	
		System.out.println(priceRange);
		System.out.println(categoryID);
		System.out.println(sellerID);
		System.out.println(color);
		System.out.println(use);
		System.out.println(responseText);
		
		if (SubmitSurveyBtn.length() != 0){
	if(color==null)
	{
		color="";
	}
	else  
		{
		int questionID=1;
		responseText="user entered color:"+color+"";
		AuthDAO.getProductByColor(color);
		try {
			AuthDAO.InsertSurveyResponses(userID,questionID,responseText);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	if(categoryID!=0)
		{ 
		int questionID=2;
		AuthDAO.getProductByCategory(categoryID);
		responseText="user entered categoryID:"+categoryID+"";
		try {
		AuthDAO.InsertSurveyResponses(userID,questionID,responseText);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(priceRange==null)
			priceRange="";
		else 
			{
			int questionID=3;
			AuthDAO.getProductByPrice(priceRange);
			responseText="user entered priceRange:"+priceRange+"";
			try {
				AuthDAO.InsertSurveyResponses(userID,questionID,responseText);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			if(use!=0)
			{
				int questionID=4;
		 AuthDAO.getProductByPurpose(use);
		 responseText="user entered purpose of use with value:"+use+"";
			try {
				AuthDAO.InsertSurveyResponses(userID,questionID,responseText);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			if(sellerID!=0)
			{
				int questionID=5;
		 AuthDAO.getProductByManufacturer(sellerID);
		 responseText="user entered manufacturer with sellerID:"+sellerID+"";
		 try {
			 AuthDAO.InsertSurveyResponses(userID,questionID,responseText);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         if (userID ==0) {
         	SurveyMessage = "Response insert failed.";
         }else{
        	 SurveyMessage = "Thank you for taking our survey!";
         }
     }
  else  { //If the check username button was not pressed
	 SurveyMessage += "An enexpected error has occured"; //There was an error in http request
 }
 
 
	 request.setAttribute("SurveyMessage",SurveyMessage);
     rd.forward(request, response);

			
		}
	}
}


    