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
@WebServlet("/UpdateBuyerInfo")
public class UpdateBuyerInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBuyerInfo() {
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
		 RequestDispatcher rd = request.getRequestDispatcher("edit_buyer_account.jsp");
		
	        String updateMessage1 = "";
	        String email, password, passwordConfirm, firstName="", lastName,middleName,phone,address,city,state,zip,accountType,accountNum,routingNum,companyName,url,shipping;
	        boolean newUserID = false;
	        String updatebt=request.getParameter("updatebt");
	        String checkEmailBtn = request.getParameter("check_email");
	        boolean insertNewUser = true;
	        // String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	      
	        accountType=request.getParameter("accountType");
	        password = request.getParameter("password");
	       
	        firstName = request.getParameter("fname");
	        lastName = request.getParameter("lname");
	        middleName = request.getParameter("mname");
	        phone = request.getParameter("phone");
	        address = request.getParameter("address");
	        city = request.getParameter("city");
	        state = request.getParameter("state");
	        zip = request.getParameter("zip");
	      int userID=User.GetUserID();
	        String updateMessage11 = null;
	     
	        if (password == null) {
	            password = "";
	        }
	       
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
	     
	       
	        
	       
	        
	        if (updatebt == null) {
	            updatebt = "";
	        }

	        

	        if (updatebt.length() != 0) { //If the submit button was pressed
	            if (firstName.equals("")) {
	                if (!updateMessage11.equals("")) {
	                    updateMessage11 += "<br />";
	                }
	                updateMessage11 += "You did not enter a first name";
	                insertNewUser = false;
	            }
	        /*    if (middleName.equals("")) {
	                if (!updateMessage.equals("")) {
	                    updateMessage1 += "<br />";
	                }
	                updateMessage1 += "You did not enter  middle name";
	                insertNewUser = false;
	            }*/
	            if (lastName.equals("")) {
	                if (!updateMessage11.equals("")) {
	                    updateMessage11 += "<br />";
	                }
	                updateMessage11 += "You did not enter a last name";
	                insertNewUser = false;
	            }
	            if (password.equals("")) {
	                if (!updateMessage11.equals("")) {
	                    updateMessage11 += "<br />";
	                }
	                updateMessage11 += "You did not enter a password";
	                insertNewUser = false;
	            }
	           
	            }
	            if (phone.equals("")) {
	                if (!updateMessage11.equals("")) {
	                    updateMessage11 += "<br />";
	                }
	                updateMessage11 += "You did not enter a phone number";
	                insertNewUser = false;
	            }
	            
	           
	            if (phone.length()!=10) {
	                if (!updateMessage11.equals("")) {
	                    updateMessage11 += "<br />";
	                }
	                updateMessage11 += "The phone number you entered was not valid. Please enter a 10-digit phone number.";
	                insertNewUser = false;
	            }
	            
	            if(zip.length()!=5)
	            {
	            	 if (!updateMessage11.equals("")) {
	                     updateMessage11 += "<br />";
	                 }
	                 updateMessage11 += "The zip code you entered was in an invalid format. Please enter a 5-digit zip code.";
	                 insertNewUser = false;	
	            }
	         
	            
	            
	            if(state.length()!=2)
	            {
	            	 if (!updateMessage11.equals("")) {
	                     updateMessage11 += "<br />";
	                 }
	                 updateMessage11 += "The state must be in 2-letter format.";
	                 insertNewUser = false;
	            }
	            if(accountType.equals("seller"))
	            {
	            	accountType="S";
	            }
	            else
	            {
	            	accountType="B";
	            }
	            
	          
	            

	            if (insertNewUser) {
	                
	                if (newUserID == false) {
	                    updateMessage11 = "New User Insert Failed.";
	                } else if (!AuthDAO.UpdateUserDetails(userID,firstName,middleName,lastName,phone,address,city,state,zip)) {
	                    updateMessage11 = "Create Account Failed, Please Try Again.";
	                }
	            }
	        

	        try {
	            AuthDAO.DB_Close();
	        } catch (Throwable e) {
	            request.setAttribute("loginMessage", request.getAttribute("loginMessage") + "<br />" + e.toString());
	        }

	        request.setAttribute("updateMessage", updateMessage11);
	        rd.forward(request, response);
	       

	    }

	}


