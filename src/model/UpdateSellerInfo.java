package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AuthDAO;
import controller.User;


/**
 * Servlet implementation class UpdateBuyerInfo
 */
@WebServlet(urlPatterns ={"/UpdateSellerInfo"})
public class UpdateSellerInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSellerInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // TODO Auto-generated method stub
        RequestDispatcher rd = request.getRequestDispatcher("edit_seller_account.jsp");
        
	       
        String   firstName, lastName,middleName,phone,address,city,state,zip,accountNum,routingNum,companyName,url,shipping;
      
        String updatebt=request.getParameter("updatebt");
        
        boolean insertNewUser = true;
        // String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
       
        
	       
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        middleName = request.getParameter("middleName");
        phone = request.getParameter("phone");
        address = request.getParameter("address");
        city = request.getParameter("city");
        state = request.getParameter("state");
        zip = request.getParameter("zip");
        int userID=User.GetUserID();
        String updateMessage = null;
        url=request.getParameter("url");
        companyName = request.getParameter("companyName");
        accountNum = request.getParameter("accountNumber");
        routingNum = request.getParameter("routingNumber");
	       
	       
        
	       
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (middleName == null) {
            middleName = "";
        }
        
        if (phone == null) {
            phone = "";
        }
        if (city == null) {
            city = "";
        }
        if (address == null) {
            address = "";
        }
        if (state == null) {
            state = "";
        }
        if (zip == null) {
            zip = "";
        }
        if (url == null) {
            url = "";
        }
        if (companyName == null) {
            companyName = "";
        }
	       
        
	       
        
        if (updatebt == null) {
            updatebt = "";
        }
        
        
        
        if (updatebt.length() != 0) { //If the submit button was pressed
            if (firstName.equals("")) {
                
                updateMessage += "You did not enter a first name";
                insertNewUser = false;
            }
            if (middleName.equals("")) {
	               
                updateMessage += "You did not enter  middle name";
                insertNewUser = false;
            }
            if (lastName.equals("")) {
                
                updateMessage += "You did not enter a last name";
                insertNewUser = false;
            }
	           
            if (phone.equals("")) {
                
                updateMessage += "You did not enter a phone number";
                insertNewUser = false;
            }
            
	           
            if (phone.length()!=10) {
                
                updateMessage += "The phone number you entered was not valid. Please enter a 10-digit phone number.";
                insertNewUser = false;
            }
            
            if(zip.length()!=5)
            {
                
                updateMessage += "The zip code you entered was in an invalid format. Please enter a 5-digit zip code.";
                insertNewUser = false;
            }
            
            
            
            if(state.length()!=2)
            {
                
                updateMessage += "The state must be in 2-letter format.";
                insertNewUser = false;
            }
            if (url.equals("")) {
                
                updateMessage += "You did not enter url";
                insertNewUser = false;
            }
            
            
            if(state.length()!=2)
            {
                
                updateMessage += "The state must be in 2-letter format.";
                insertNewUser = false;
            }
            
            if (insertNewUser) {
                
               
             if (!AuthDAO.UpdateUserDetails(userID,firstName,middleName,lastName,phone,address,city,state,zip)) {
                updateMessage = "Update Account Failed, Please Try Again.";
             }
            if (!AuthDAO.UpdateSellerDetails(userID, accountNum, routingNum, companyName,url)) {
                updateMessage = "Update Account Failed, Please Try Again.";
            }
            else {
                updateMessage="Seller Account Information updated";
            }
        }
        
            
        try {
            AuthDAO.DB_Close();
        } catch (Throwable e) {
            request.setAttribute("loginMessage", request.getAttribute("loginMessage") + "<br />" + e.toString());
        }
        
	       
        request.setAttribute("updateMessage", updateMessage);
        rd.forward(request, response);
    }
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}

