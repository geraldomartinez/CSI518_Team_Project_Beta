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
        
		int  productID;
		 String color,priceRange;
		int categoryID,sellerID;
		 String SubmitSurveyBtn = request.getParameter("SubmitSurveyBtn");

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
		
		
		if (SubmitSurveyBtn.length() != 0){
	if(color==null)
		color="";
	else  AuthDAO.getProductByColor(color);
	AuthDAO.getProductByCategory(categoryID);
		
	 AuthDAO.getProductByPrice(priceRange);
		 AuthDAO.getProductByPurpose(use);
		 AuthDAO.getProductByManufacturer(sellerID);
	 request.setAttribute("SurveyMessage", "Thank you for taking our survey!");
     rd.forward(request, response);

			
		}
	}

}

    