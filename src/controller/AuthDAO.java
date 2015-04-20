/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class AuthDAO {

    private static Connection conn; //Connection object
    //public static final String DB_URL = "jdbc:mysql://localhost:3306/lab3"; //Database URL
    //public static final String DB_USER = "root"; //Database username
    //public static final String DB_PW = ""; //Database password
    public static final String DB_URL = "jdbc:mysql://GreatDanes.db.6936824.hostedresource.com:3306/GreatDanes"; //Database URL
    public static final String DB_USER = "GreatDanes"; //Database username
    public static final String DB_PW = "Csi518!!"; //Database password
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver"; //Database driver
    
    public static Connection createConn(){
    	
    	Connection conn = null;
    	
        //Register JDBC Driver
        System.out.println("Register JDBC Driver...");
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open connection to DB
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (Exception ex) {//An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }

    /*Verifies a email/password combo*/
    public static int checkEmailPass(String email /*in, email to verify*/, String password /*in, password to verify*/) {
        Statement stmt = null; //SQL statement object
        ResultSet rs = null; //Result set object obtained from database
        String sql; //SQL statement string
        int id = -1; //User's unique ID in database 
        String accountType = "";
        boolean isVerified = false;
        Connection conn = createConn(); //Create DB connection

        //Execute query to check email and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT `userID`,`accountType` FROM `Users` WHERE `email`='" + email + "' AND `password`='" + password + "' LIMIT 1";
            System.out.println("SQL Statement:");
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                id = rs.getInt("userID");
                accountType = rs.getString("accountType");
            }
        } catch (Exception ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (id != -1 && accountType.toUpperCase().equals("S")){ //If the account credentials are valid, and it is a seller account
            System.out.println("Creating statement...");
            try {
                stmt = conn.createStatement();
                sql = "SELECT `isVerified` FROM `SellerDetails` WHERE `sellerID`='"+id+"' LIMIT 1";
                System.out.println("SQL Statement:");
                System.out.println(sql);
                rs = stmt.executeQuery(sql);

                //Extract data from result set
                while (rs.next()) {
                    //Retrieve by column name
                    isVerified = rs.getBoolean("isVerified");
                }
                
                if (!isVerified){ //If the seller has not been verified by an admin 
                	id = -3; //Return -3, indicating that the seller is not verified
                }
            } catch (Exception ex) { //An error occurred
                //Log the exception
                Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Clean-up
        try {
            rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) {
            //If it fails to close, just leave it.
        }

        return id;

    }
    public static User getUserById(int userID) {
    	 
        Statement stmt;
        ResultSet rs;
        String sql;
        User usr;
   
        String username = null;
        String password = null;
        String accountType=null;
        String email=null;
        String firstName = null;
        String lastName = null;
        String middleName = null;
        String phone = null;
        String city = null;
        String address = null;
        String state = null;
        String zip = null;

        Connection conn = createConn(); //Create DB connection
 
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM `Users` JOIN `UserProfile` ON `UserProfile`.`userID`=`Users`.`userID` WHERE `Users`.`userID`='" + userID + "';";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
 
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                accountType=rs.getString("accountType");
                email=rs.getString("email");
                password = rs.getString("password");
                firstName = rs.getString("firstName");
                middleName=rs.getString("middleName");
                lastName = rs.getString("lastName");
                phone=rs.getString("phone");
                address=rs.getString("address");
                city=rs.getString("city");
                state=rs.getString("state");
                zip=rs.getString("zip");
            }            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get user by ID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new User();
        }
 
        //Clean-up
        try {
            rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
 
        usr = new User(userID, accountType, email, password, firstName, middleName, lastName, phone, address, city, state, zip);
        return usr;
    }
 
    public static int enterNewUserProfile(String accountType,String email,String password) {
 
        Statement stmt;
        ResultSet rs;
        String sql;
        int newUserID = -1;
        Connection conn = createConn();
 
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `Users` (`accountType`,`email`,`password`) VALUES ('"+accountType +"','" + email +"','" + password + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
 
            //Get ID of newly created user
            sql = "SELECT `userID` FROM `Users` WHERE `email`='" + email + "' AND `password`='" + password + "'";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
                newUserID = Integer.parseInt(rs.getString("userID"));
            }
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return newUserID;
    }
    
    public static boolean enterNewSellerDetails(int sellerID, String acctNum, String routingNum) {
    	 
        Statement stmt;
        String sql;
        Connection conn = createConn();
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `SellerDetails` (`sellerID`,`isVerified`,`accountNumber`,`routingNumber`) VALUES ('" + sellerID + "', 0, '" + acctNum +"','" + routingNum + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
 
        return true;
    }
 
    public static boolean enterUserName(int userID, String firstName,String middleName, String lastName,String phone,String address,String city,String state,String zip) {
 
        Statement stmt;
        String sql;
        boolean insertSuccess = false;
        Connection conn = createConn();
 
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `UserProfile` (`userID`,`firstName`,`middleName`,`lastName`,`phone`,`address`,`city`,`state`,`zip`)"
                        + " VALUES ('" + userID + "','" + firstName + "','" + middleName + "','"+ lastName + "','"+phone+"','"+address+"','"+
                        city+"','"+state+"','"+zip+"');";
        System.out.println("["+sql+"]");
            insertSuccess = stmt.executeUpdate(sql) > 0;
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return insertSuccess;
    }
 
    public static boolean isEmailAvailable(String email) {
 
        Statement stmt;
        ResultSet rs;
        String sql;
        String emailFromQry = null;
        Connection conn = createConn();
 
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT `email` FROM `Users` WHERE `email`='" + email + "';";
            rs = stmt.executeQuery(sql);
 
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
            	emailFromQry = rs.getString("email");
            }
        } catch (Exception ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return (emailFromQry == null);
    }
    //function to remove product
    public static boolean removeProduct(int sellerID, int productID){
    	Statement stmt;
        String sql;
        
        Connection conn = createConn();
        System.out.println("Creating Statement..");
        try {
            stmt = conn.createStatement();
            //sql query to delete a product by matching productID and sellerID
            sql = "DELETE FROM Products WHERE productID='"+productID +"'AND sellerID='"+sellerID+"'";
            System.out.println("removeProduct Query:");
            System.out.println(sql);
           //updating tables Products and ProductReviews
            stmt.executeUpdate(sql);
            return true;
        } 
        catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean VerifySellerID(int sellerID)
    {
    	Statement stmt;
        String sql;
      
        Connection conn = createConn();
        System.out.println("Creating Statement..");
        try {
            stmt = conn.createStatement();
            //sql query to verify that the seller is actually sellerID == seller who is logged in
         
            sql = "SELECT sellerID FROM `SellerDetails` "
            		+ "JOIN `Products` ON `SellerDetails`.`sellerID`=`Products`.`sellerID`"
            		+ " WHERE `sellerDetails`.`sellerID`='" + sellerID + "';";
            stmt.executeQuery(sql);
            
        } 
        catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return true;
    }
	
    public static int InsertProductDetails( String sellerID, String name,  String description,String specs,  String price, String categoryID, String numInStock) {
	  	 
        Statement stmt;
        String sql;
        ResultSet rs;
        Connection conn = AuthDAO.createConn();
        int productID=-1;
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `Products` (`sellerID`,`categoryID`,`productName`,`unitPrice`,`quantity`,`description`,`specs`) VALUES ( '" + sellerID +"','" +categoryID+"','"+ name + "','"+price+"','"+numInStock+"','"+description+"','"+specs+"');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            sql = "SELECT max(`productID`) FROM `Products` WHERE `sellerID`='" + sellerID + "' ";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
                productID = (rs.getInt("max(`productID`)"));
            }
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return productID;
    }   
    
    public static Product getProductById(int productID) {
      	 
        Statement stmt;
        ResultSet prd_rs;
        String prd_sql;
        Product prd;
        
        int sellerID = 0;
        int categoryID = 0;
        String productName = null;
        float unitPrice = (float) 0.00;
        float rating = (float) 0.00;
        float shippingCost = (float) 0.00;
        int quantity= 0;
        String description=null;
        String specs = null;
 
        Connection conn = createConn(); //Create DB connection
 
        //Execute query to check for matching product
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            prd_sql = "SELECT * FROM `Products` WHERE `Products`.`productID`='" + productID + "';";
            System.out.println(prd_sql);
            prd_rs = stmt.executeQuery(prd_sql);
 
            //Extract data from result set
            while (prd_rs.next()) {
                //Retrieve by column name
                sellerID=prd_rs.getInt("sellerID");
                categoryID=prd_rs.getInt("categoryID");
                productName = prd_rs.getString("productName");
                unitPrice = prd_rs.getFloat("unitPrice");
               
                //shippingCost = prd_rs.getString("lastName");
                quantity=prd_rs.getInt("quantity");
                description=prd_rs.getString("description");
                specs=prd_rs.getString("specs");
            }       
            
           
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get Product by ID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Product();
        }
 
        //Clean-up
        try {
            prd_rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
 
        prd = new Product(productID, sellerID, productName, description, specs, unitPrice, quantity, categoryID);   
        return prd;
    }
 
    public static void DB_Close() throws Throwable {
        try { //Attempt to close the database connection
            if (conn != null) { //If the connection object is set
                conn.close(); //Close the connection object
            }
        } catch (SQLException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public static boolean addItem2Wishlist(int wishListID, int productID,int productQuantity)
   {
	   Statement stmt;
       String sql;
       Connection conn = createConn();

       //Execute query to insert seller details
       try {
           stmt = conn.createStatement();
           sql = "INSERT INTO `WishListItems`(`wishListID`,`productID`,`e`, `productQuantity`) VALUES ('" + wishListID + "','" +productID +"','"+productQuantity+"');";
           System.out.println(sql);
           stmt.executeUpdate(sql);
       } catch (SQLException | NumberFormatException ex) { //An error occurred
           //Log the exception
           Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
           return false;
       }

       return true;
   }
   }

