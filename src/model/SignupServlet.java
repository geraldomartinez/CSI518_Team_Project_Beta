package model;

import controller.AuthDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel
 */
@WebServlet(urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");

        String registerMessage = "";
        String email, password, passwordConfirm, firstName, lastName,middleName,phone,address,city,state,zip,accountType,accountNum,routingNum,companyName;
        int newUserID;
        boolean insertNewUser = true;
        // String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        email = request.getParameter("email");
        accountType=request.getParameter("accountType");
        password = request.getParameter("password");
        passwordConfirm = request.getParameter("password_confirm");
        firstName = request.getParameter("fname");
        lastName = request.getParameter("lname");
        middleName = request.getParameter("mname");
        phone = request.getParameter("phone");
        address = request.getParameter("address");
        city = request.getParameter("city");
        state = request.getParameter("state");
        zip = request.getParameter("zip");
        companyName = request.getParameter("company_name");
        accountNum = request.getParameter("account_number");
        routingNum = request.getParameter("routing_number");
        String checkEmailBtn = request.getParameter("check_email");
        String submitBtn = request.getParameter("submit");
        int atpostn=email.indexOf("@");
        int dotpostn=email.lastIndexOf(".");
        
        //Prevent null pointer exception
        if (email == null) {
        	email = "";
        }
        if (password == null) {
            password = "";
        }
        if (passwordConfirm == null) {
            passwordConfirm = "";
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
        if (companyName == null) {
        	companyName = "";
        }
        
        
        if (checkEmailBtn == null) {
            checkEmailBtn = "";
        }
        if (submitBtn == null) {
            submitBtn = "";
        }

        if (email.equals("")) {
            registerMessage += "You did not enter an email";
            insertNewUser = false;
        } else if (!AuthDAO.isEmailAvailable(email)){
			registerMessage += "Email [" + email + "] is NOT available to use for registration. Please choose a different email.";
			insertNewUser = false;
		} else if (checkEmailBtn.length() != 0) {
    		registerMessage += "<span style='color: green'>Email [" + email + "] is not associated with another account. You may use it for registration.</span>";
		}

        if (submitBtn.length() != 0) { //If the submit button was pressed
            if (firstName.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a first name";
                insertNewUser = false;
            }
            if (middleName.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter  middle name";
                insertNewUser = false;
            }
            if (lastName.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a last name";
                insertNewUser = false;
            }
            if (password.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a password";
                insertNewUser = false;
            }
            if (passwordConfirm.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not confirm your password";
                insertNewUser = false;
            }
            if (phone.equals("")) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "You did not enter a phone number";
                insertNewUser = false;
            }
            
           
            if (phone.length()!=10) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "The phone number you entered was not valid. Please enter a 10-digit phone number.";
                insertNewUser = false;
            }
            
            if(zip.length()!=5)
            {
            	 if (!registerMessage.equals("")) {
                     registerMessage += "<br />";
                 }
                 registerMessage += "The zip code you entered was in an invalid format. Please enter a 5-digit zip code.";
                 insertNewUser = false;	
            }
            if(state.length()!=2)
            {
            	 if (!registerMessage.equals("")) {
                     registerMessage += "<br />";
                 }
                 registerMessage += "The state must be in 2-letter format.";
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
            
            if(atpostn<1 || dotpostn<atpostn+2 || dotpostn+2>=email.length())
            {
            	 if (!registerMessage.equals("")) {
                     registerMessage += "<br />";
                 }
            	 registerMessage += "email is not in a valid format.";
            }
            
            if (!password.equals("") && !passwordConfirm.equals("") && !password.equals(passwordConfirm)) {
                if (!registerMessage.equals("")) {
                    registerMessage += "<br />";
                }
                registerMessage += "Passwords did not match";
                insertNewUser = false;
            }

            if (insertNewUser) {
                newUserID = AuthDAO.enterNewUserProfile(accountType, email, password);
                if (newUserID == -1) {
                    registerMessage = "New User Insert Failed.";
                } else if (!AuthDAO.enterUserName(newUserID,firstName,middleName,lastName,phone,address,city,state,zip)) {
                    registerMessage = "Create Account Failed, Please Try Again.";
                } else if (accountType == "S" && !AuthDAO.enterNewSellerDetails(newUserID, accountNum, routingNum, companyName)) {
                    registerMessage = "Create Account Failed, Please Try Again.";
                } else {
                    rd = request.getRequestDispatcher("index.jsp");
                    request.setAttribute("indexMessage", "<span style='color: green'>Registration for [" + email + "] succesful. Log in to view your account.</span>");
                    rd.forward(request, response);
                }
            }
        } else if (checkEmailBtn.length() == 0) { //If the check username button was not pressed
            registerMessage += "An enexpected error has occured"; //There was an error in http request
        }

        try {
            AuthDAO.DB_Close();
        } catch (Throwable e) {
            request.setAttribute("loginMessage", request.getAttribute("loginMessage") + "<br />" + e.toString());
        }

        request.setAttribute("registerMessage", registerMessage);
        rd.forward(request, response);

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
