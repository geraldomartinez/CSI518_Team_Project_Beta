/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
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
    
    private static Connection createConn(){
    	
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
            sql = "SELECT * FROM `user` JOIN `user_profile` ON `user_profile`.`userID`=`user`.`userID` WHERE `user`.`userID`='" + userID + "';";
            rs = stmt.executeQuery(sql);
 
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("username");
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
 
        usr = new User(userID, username, password, firstName, middleName, lastName,phone,address,city,state,zip);
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
}