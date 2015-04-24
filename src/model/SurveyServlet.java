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
		 String color,price, category, use, manufacturer;
		String categoryID;
		String inputMessage = "";
	
		color=request.getParameter("color");
		price=request.getParameter("price");
		category=request.getParameter("category");
		manufacturer=request.getParameter("manufacturer");
		if(color== null)
			color="";
		else AuthDAO.getProductByColor(color);
		if(price == null)
			price="";
		else AuthDAO.getProductByPrice(price);
		if(category== null)
			category="";
		else AuthDAO.getProductByCategory(category);
		if(manufacturer== null)
			manufacturer="";
		else AuthDAO.getProductByManufacturer(manufacturer);
			
		
	}

}

    