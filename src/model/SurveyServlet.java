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
        
		int productID;
		 String color,price="", category="", manufacturer;
		 String SubmitSurveyBtn = request.getParameter("submit");
	
		color=request.getParameter("color");
		String priceRange = request.getParameter( "price" );
		int categoryVal =Integer.parseInt( request.getParameter( "category" ) );
		int use= Integer.parseInt(request.getParameter("use"));
		manufacturer=request.getParameter("manufacturer");
		
		if (SubmitSurveyBtn.length() != 0){
		if(color== null)
			color="";
		else AuthDAO.getProductByColor(color);
		if(category== null)
			category="";
		else AuthDAO.getProductByCategory(categoryVal);
		if(price == null)
			price="";
		else AuthDAO.getProductByPrice(priceRange);
		if(use==0)
			use=0;
		else AuthDAO.getProductByPurpose(use);
		
		if(manufacturer== null)
			manufacturer="";
		else AuthDAO.getProductByManufacturer(manufacturer);
			
		}
	}

}

    