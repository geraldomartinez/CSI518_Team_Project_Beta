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
		int categoryID=0,sellerID=0;
		 String SubmitSurveyBtn = request.getParameter("SubmitSurveyBtn");
		 String responseText="";
		 String questionText="";
			int use=0;
		color=request.getParameter("color");
		priceRange = request.getParameter( "price" );
		categoryID= Integer.parseInt(request.getParameter( "categoryID" ) );
		 use= Integer.parseInt(request.getParameter("use"));
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
		if(color.equals(""))
		{
			if (!SurveyMessage.equals("")) {
            	SurveyMessage += "<br />";
            }
            SurveyMessage += "You did not select a color ";
		}
	}
	else  
		{
		int questionID=1;
		questionText="(color)";
		responseText=color;
		AuthDAO.getProductByColor(color);
		try {
			AuthDAO.InsertSurveyResponses(userID,questionID,responseText,questionText);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	if(categoryID!=0)
		{ 
		int questionID=2;
		AuthDAO.getProductByCategory(categoryID);
		questionText=" (category with categoryID)";
		responseText=""+categoryID+"";
		try {
		AuthDAO.InsertSurveyResponses(userID,questionID,responseText,questionText);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(priceRange==null)
			priceRange="";
		if(priceRange.equals(""))
		{
			if (!SurveyMessage.equals("")) {
            	SurveyMessage += "<br />";
            }
            SurveyMessage += "You did not select a price range ";
		}
		else 
			{
			int questionID=3;
			AuthDAO.getProductByPrice(priceRange);
			questionText="(priceRange  value)";
			responseText=""+priceRange+"";
			try {
				AuthDAO.InsertSurveyResponses(userID,questionID,responseText,questionText);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		if(use==0)
			
		{
			if (!SurveyMessage.equals("")) {
            	SurveyMessage += "<br />";
            }
            SurveyMessage += "You did not select a urpose of use ";
		}
		else
			{
				int questionID=4;
		 AuthDAO.getProductByPurpose(use);
		 questionText="(Purpose of Use value)";
		 responseText=""+use+"";
			try {
				AuthDAO.InsertSurveyResponses(userID,questionID,responseText,questionText);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			if(sellerID==0)
			{
				if (!SurveyMessage.equals("")) {
	            	SurveyMessage += "<br />";
	            }
	            SurveyMessage += "You did not select a Manufacturer ";
			}
			else
			{
				int questionID=5;
				questionText="(manufacturer with sellerID)";
		 AuthDAO.getProductByManufacturer(sellerID);
		 responseText=""+sellerID+"";
		 try {
			 AuthDAO.InsertSurveyResponses(userID,questionID,responseText,questionText);
				
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
 
 
	 request.setAttribute("SurveyMessage",SurveyMessage);
     rd.forward(request, response);

			
		}
	}
}


    