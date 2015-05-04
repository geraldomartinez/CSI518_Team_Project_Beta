/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

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
    	try {
			if (conn == null || !conn.isValid(5)){				
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		    //Open connection to DB
		    System.out.println("Connecting to database...");
		    try {
		        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
		    } catch (Exception ex) {//An error occurred
		        //Log the exception
		        Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
		    }
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
        boolean isActive = false;
        Connection conn = createConn(); //Create DB connection

        //Execute query to check email and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "SELECT `userID`,`accountType`,`active` FROM `Users` WHERE `email`='" + email + "' AND `password`='" + password + "' LIMIT 1";
            System.out.println("SQL Statement:");
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                id = rs.getInt("userID");
                accountType = rs.getString("accountType");
                isActive = rs.getBoolean("active");
                System.out.print("isActive: ");
                System.out.print(isActive);
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
                
                if (!isActive){ //If the account has been deactivated
                	id = -4; //Return -4, indicating that the user may not log in because their account is deactivated
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
    
	public static boolean okayToLeaveReview(int userID, int productID) {
		Statement stmt = null;

		ResultSet rs = null;
		boolean order = false;

		Connection conn = createConn();
		try {
			stmt = conn.createStatement();
			String sql = "SELECT `OrderItems`.`productID` FROM `OrderItems` LEFT JOIN (`Orders`, `Products`) ON (`OrderItems`.`orderID`=`Orders`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID`) WHERE `Orders`.`buyerID` = '" + userID + "' AND `OrderItems`.`productID` = '" + productID + "' AND `OrderItems`.`hasShipped` = '1' AND `OrderItems`.`canceled` = '0'";

			System.out.println("SQL Statement:");
			System.out.println(sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				rs.close();
				
				sql = "SELECT `reviewID` from `ProductReviews` WHERE `userID`='"+userID+"' AND `productID`='"+productID+"'";

				System.out.println("SQL Statement:");
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				order = true;
				while (rs.next()) {
					order = false;
					break;
				}
				break;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
	
	public static int insertreview(int userID, int productID, int rating, String review) {

		Statement stmt;
		String sql;
		Connection conn = createConn();
		  ResultSet rs;
		  int reviewID = -1;
		// Execute query to insert seller details
		System.out.println("Creating statement...");
		try {
			stmt = conn.createStatement();
			sql = "INSERT INTO `ProductReviews` (`userID`,`productID`,`ranking`,`review`) VALUES ('" + userID + "','" + productID + "','" + rating + "','" + review + "');";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			sql = "SELECT MAX(`reviewID`) FROM `ProductReviews`";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
            	reviewID = Integer.parseInt(rs.getString("MAX(`reviewID`)"));
            }
            
            return reviewID;
		} catch (SQLException | NumberFormatException ex) { // An error occurred
			// Log the exception
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			//return 0;
		}

		return -1;
	}
    
    public static User getUserById(int userID) {
    	 
        Statement stmt;
        ResultSet rs;
        String sql;
        User usr;
   
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
    
    public static boolean enterNewSellerDetails(int sellerID, String acctNum, String routingNum, String companyName, String url) {
    	 
        Statement stmt;
        String sql;
        Connection conn = createConn();
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `SellerDetails` (`sellerID`,`isVerified`,`accountNumber`,`routingNumber`, `companyName`,`url`) VALUES ('" + sellerID + "', 0, '" + acctNum +"','" + routingNum + "','" + companyName + "','" + url + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
 
        return true;
    }
    
    public static boolean UpdateSellerDetails(int userID, String acctNum, String routingNum, String companyName,String url) {
        
        Statement stmt;
        String sql;
        Connection conn = createConn();
        
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "UPDATE `SellerDetails` SET `accountNumber`='" + acctNum +"',`routingNumber`='"+routingNum + "', `companyName`='" +companyName + "',`url`='"+url+"'WHERE `sellerID`='" + userID + "';";
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
 public static boolean UpdateUserDetails(int userID, String firstName,String middleName, String lastName,String phone,String address,String city,String state,String zip) {
        
        Statement stmt;
        String sql;
        boolean insertSuccess = false;
        Connection conn = createConn();
        
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "UPDATE `UserProfile` SET `firstName`='"+ firstName +"',`middleName`='"+ middleName +"',`lastName`='"+lastName
            + "',`phone`='"+phone+"',`address`='"+address+"',`city`='"+city+"',`state`='" +state+"',`zip`='"+zip+"'WHERE userID='"+userID+"';";
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
            sql = "update Products  set removed=1 WHERE productID='"+productID +"'AND sellerID='"+sellerID+"'";
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
            		+ " WHERE `SellerDetails`.`sellerID`='" + sellerID + "';";
            stmt.executeQuery(sql);
            
        } 
        catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return true;
    }
	
    public static int InsertProductDetails( String sellerID, String name,  String description,String specs,  String price, String categoryID, String numInStock, Part filePart, String groundCost, String twoCost, String nextCost) throws IOException, ClassNotFoundException {
	  	 
    	PreparedStatement ps = null;
        String sql;
        ResultSet rs;
        Connection conn = AuthDAO.createConn();
        int productID=-1;
        int fileSize = (int)filePart.getSize();
        InputStream inputStream = filePart.getInputStream();
        System.out.println("Filesize: "+Integer.toString(fileSize)+" bytes");
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
          
        	//Insert the new product
            sql = "INSERT INTO `Products` (`sellerID`,`categoryID`,`productName`,`unitPrice`,`quantity`,`description`,`specs`,`groundCost`,`twoCost`,`nextCost`,`pictureBlob`) "
            		+ "VALUES ('" + sellerID + "','" + categoryID + "','" + name + "','"+ price + "','"+numInStock+"','"+description+"','"+specs+"', '"+groundCost+"', '"+twoCost+"', '"+nextCost+"', ?);";
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);  //Prepare the statement
            ps.setBinaryStream(1, inputStream, fileSize); //Add the binary stream to the statement
            System.out.println(ps);
            ps.executeUpdate(); //Execute the insert query
            conn.commit();
            inputStream.close();
            
            sql = "SELECT max(`productID`) FROM `Products` WHERE `sellerID`='" + sellerID + "' ";
            System.out.println(sql);
            rs = ps.executeQuery(sql);
            
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
                productID = (rs.getInt("max(`productID`)"));
            }
            ps.close();
            
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return productID;
    }   
    
    public static int UpdateProductDetails(int productID, boolean isAdmin, String sellerID, String name,  String description,String specs,  String price, String categoryID, String numInStock, Part filePart, String groundCost, String twoCost, String nextCost) throws IOException, ClassNotFoundException {
	  	 
    	PreparedStatement ps = null;
        String sql;
        ResultSet rs;
        Connection conn = AuthDAO.createConn();
        int fileSize = (int)filePart.getSize();
        InputStream inputStream = filePart.getInputStream();
        System.out.println("Filesize: "+Integer.toString(fileSize)+" bytes");
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
          
        	//Insert the new product
            sql = "UPDATE `Products` "
        		+ "SET `sellerID` = '" + sellerID + "',"
				+ "`categoryID` = '" + categoryID + "',"
				+ "`productName` = '" + name + "',"
				+ "`unitPrice` = '"+ price + "',"
				+ "`quantity` = '"+numInStock+"',"
				+ "`description` = '"+description+"',"
				+ "`specs` = '"+specs+"',"
				+ "`groundCost` = '"+groundCost+"',"
				+ "`twoCost`='"+twoCost+"',"
				+ "`nextCost`='"+nextCost+"'";
			if (filePart != null){
				sql += ",`pictureBlob` = ? ";
			}
			sql += "WHERE `productID` LIKE '"+productID+"'";
            
            if (!isAdmin){
            	sql += " AND `sellerID` = '"+sellerID+"'";
            }
            	
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);  //Prepare the statement
			if (filePart != null){
	            ps.setBinaryStream(1, inputStream, fileSize); //Add the binary stream to the statement
			}
            System.out.println(ps);
            ps.executeUpdate(); //Execute the insert query
            conn.commit();
            inputStream.close();
            ps.close();
            return productID;
            
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }   

     
    public static boolean deactivateaccount( String accounttype, int username) throws IOException, ClassNotFoundException {
	  	 
        Statement stmt;
        String sql;
       // ResultSet rs;
        Connection conn = AuthDAO.createConn();
        //int productID=-1;
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
          
            sql = "update  `Users` set `active`=0 where `userID`= '" + username + "' and  accounttype='" + accounttype + "';";
            		
            System.out.println(sql);
            stmt.executeUpdate(sql);
            System.out.println(sql);
           
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
 
        return true;
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
        //float rating = (float) 0.00;
        float groundCost = (float) -1.00f;
        float twoCost = (float) -1.00f;
        float nextCost = (float) -1.00f;
        int quantity= 0;
        String description=null;
        String specs = null;
        Blob pictureBlob = null;
        byte[] blobAsBytes = null;
        int ratingAvg=0, ratingCount =0;
        Connection conn = createConn(); //Create DB connection
 
        //Execute query to check for matching product
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            prd_sql = "SELECT p . * , pr.rankingAVG, pr.count FROM Products p "+
			"LEFT JOIN (SELECT productID, AVG( ranking ) AS rankingAVG, COUNT( * ) AS count "+
			"FROM ProductReviews "+
			"GROUP BY productID "+
			")pr ON p.productID = pr.productID WHERE p.productID='" 
            + productID + "';";
            System.out.println(prd_sql);
            prd_rs = stmt.executeQuery(prd_sql);
 
            //Extract data from result set
            while (prd_rs.next()) {
                //Retrieve by column name
                sellerID=prd_rs.getInt("sellerID");
                categoryID=prd_rs.getInt("categoryID");
                productName = prd_rs.getString("productName");
                unitPrice = prd_rs.getFloat("unitPrice");
                quantity=prd_rs.getInt("quantity");
                description=prd_rs.getString("description");
                specs=prd_rs.getString("specs");
                float rating = prd_rs.getFloat("rankingAVG");
            	//prod.SetRating(Math.round(rating));
            	ratingAvg = Math.round(rating);
            	ratingCount = prd_rs.getInt("count");
            	
            	//Get shipping costs
            	groundCost = prd_rs.getFloat("groundCost");
            	twoCost = prd_rs.getFloat("twoCost");
            	nextCost = prd_rs.getFloat("nextCost");
            	
                pictureBlob = prd_rs.getBlob("pictureBlob");
                if (pictureBlob != null){
                	blobAsBytes = pictureBlob.getBytes(1,(int)pictureBlob.length());
                }else{
                	blobAsBytes = new byte[0];
                }
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
 
        prd = new Product(productID, sellerID, productName, description, specs, unitPrice, quantity, categoryID, blobAsBytes, ratingAvg, ratingCount, groundCost, twoCost, nextCost);   
        return prd;
    }

    public static WishList ReturnUserWishlist(int userID)
    {
 	   	Statement stmt;
 	   	ResultSet rs;
        String sql;
        Connection conn = createConn();
        WishList wishlist = new WishList();

        //Execute query to insert seller details
        try {
            stmt = conn.createStatement();            
            sql = "SELECT * FROM `WishListItems` WHERE `userID` = '"+userID+"'";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
            	wishlist.AddItem(rs.getInt("productID"));
            	if (rs.getInt("quantity") > 1){
                	wishlist.UpdateQuantity(rs.getInt("productID"), rs.getInt("quantity"));
            	}
            }    
            
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wishlist;
    }   

    public static boolean addItem2Wishlist(int userID, int productID, int quantity)
    {
 	   	Statement stmt;
        String sql;
        Connection conn = createConn();

        //Execute query to insert seller details
        try {
            stmt = conn.createStatement();
            
            sql = "DELETE IGNORE FROM `WishListItems` WHERE `userID`='"+userID+"' AND `productID`='"+productID+"'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            
            if (quantity > 0){
	            sql = "INSERT INTO `WishListItems`(`userID`, `productID`, `quantity`) VALUES ('" + userID + "','" +productID +"','"+quantity+"');";
	            System.out.println(sql);
	            stmt.executeUpdate(sql);
            }
            
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }   
    
    //function to remove product
    public static boolean ClearWishlist(int userID){
    	Statement stmt;
        String sql;
        
        Connection conn = createConn();
        System.out.println("Creating Statement..");
        try {
            stmt = conn.createStatement();
            //sql query to delete a product by matching productID and sellerID
            sql = "DELETE FROM `WishListItems` WHERE `userID`='"+userID+"'";
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
   
    public static Product getProductByColor(String color) {
     	 
		// String sql;
		Connection conn = createConn();
		Statement stmt = null;
		ResultSet prd_rs = null;
		String prd_sql;
		Product prd;
		int productID = 0;

         //Execute query to check for matching product
         System.out.println("Creating statement...");
         try {
             stmt = conn.createStatement();
             prd_sql = "SELECT `productID` FROM `Products` WHERE `Products`.`color`='" + color + "' AND `removed` <> '1' ORDER BY RAND() LIMIT 1;";
             System.out.println(prd_sql);
             prd_rs = stmt.executeQuery(prd_sql);
  
             //Extract data from result set
             while (prd_rs.next()) {
                 //Retrieve by column name
            	 productID=prd_rs.getInt("productID");
             }       
            
            
             
         } catch (Exception ex) { //An error occurred
             //Log the exception
         	System.out.println("Failed to get Product by color");
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
  
         prd = AuthDAO.getProductById(productID);
         return prd;
        
         
     }
    	
    public static Product getProductByCategory(int categoryID) {

		Connection conn = createConn();
    	Statement stmt;
        ResultSet prd_rs;
        String prd_sql;
        Product prd;
        int productID=0;
        
        //Execute query to check for matching product
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            prd_sql = "SELECT  `productID` FROM `Products` WHERE `Products`.`categoryID`='" + categoryID + "' AND `removed` <> '1'  ORDER BY RAND() LIMIT 1;";
            System.out.println(prd_sql);
            prd_rs = stmt.executeQuery(prd_sql);
 
            //Extract data from result set
            while (prd_rs.next()) {
                //Retrieve by column name
           	 	productID=prd_rs.getInt("productID");
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
 
        prd = AuthDAO.getProductById(productID);
        return prd;
    }
   
    public static Product getProductByPrice(String priceRange) {
   	 
        Statement stmt;
      //  ResultSet rs;
      //  String sql;
        double val1=0,val2=0;
       
        ResultSet prd_rs;
        String prd_sql;
        Product prd;
        int productID=0;
       
        switch(priceRange)//To select price range
        {
	        case "1":
	        	val1=000.00;
	        	val2=100.00;
	        	break;
	        case "2":
	        	val1=100.00;
	        	val2=200.00;
	        	break;
	        case "3":
	        	val1=200.00;
	        	val2=300.00;
	        	break;
	        case "4":
	        	val1=300.00;
	        	val2=400.00;
	        	break;
	        case "5":
	        	val1=400.00;
	        	val2=500.00;
	        	break;
	        case "6":
	        	val1=500.00;
	        	val2=600.00;
	        	break;
	        case "7":
	        	val1=600.00;
	        	val2=700.00;
	        	break;
	        case "8":
	        	val1=700.00;
	        	val2=800.00;
	        	break;
	        case "9":
	        	val1=800.00;
	        	val2=900.00;
	        	break;
	        case "10":
	        	val1=900.00;
	        	val2=1000.00;
	        	break;
	        default:
	        	
	        	break;
        	
        }
    	Connection conn = createConn(); //Create DB connection
        
        //Execute query to check for matching product
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            prd_sql = "SELECT `productID` FROM  `Products` WHERE  `Products`.`unitPrice` BETWEEN'"+val1+ "'AND'"+val2+"' AND `removed` <> '1'  ORDER BY RAND() LIMIT 1;";  
            System.out.println(prd_sql);
            prd_rs = stmt.executeQuery(prd_sql);
 
            //Extract data from result set
            while (prd_rs.next()) {
                //Retrieve by column name
           	 	productID=prd_rs.getInt("productID");
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
 
        prd = AuthDAO.getProductById(productID);
        return prd;
    }

    public static Product getProductByManufacturer(int sellerID) {
   	 Statement stmt;
     ResultSet prd_rs;
     String prd_sql;
     Product prd;
     int productID=0;
     
     Connection conn = createConn(); //Create DB connection
     
     //Execute query to check for matching product
     System.out.println("Creating statement...");
     try {
         stmt = conn.createStatement();
         prd_sql = "SELECT `productID` FROM `Products` WHERE `Products`.`sellerID`='" + sellerID + "' AND `removed` <> '1'  ORDER BY RAND() LIMIT 1;";
         System.out.println(prd_sql);
         prd_rs = stmt.executeQuery(prd_sql);

         //Extract data from result set
         while (prd_rs.next()) {
             //Retrieve by column name
        	 productID=prd_rs.getInt("productID");
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

     prd = AuthDAO.getProductById(productID);   
     return prd;
    }
     
    public static Product getProductByPurpose(int  use) {

		Connection conn = createConn();
		Statement stmt = null;
		ResultSet prd_rs = null;
		String prd_sql = "";
		Product prd;
		int productID = 0;
         
         //Execute query to check for matching product
        
        
        if(use == 1)// if purpose of use is Student
        {
            prd_sql = "SELECT `productID` FROM `Products` WHERE `Products`.`categoryID` <> 5  AND `removed` <> '1' ORDER BY RAND() LIMIT 1;";
        } 
                     
        if(use == 2)// if purpose of use is Commercial
        {
        	prd_sql ="SELECT `productID` FROM  `Products` WHERE  `Products`.`categoryID` =1 OR  `Products`.`categoryID` =2 OR  `Products`.`categoryID` =4 ORDER BY RAND() LIMIT 1;";
        }
        
        if(use == 3)// if purpose of use is Personal
        {
            prd_sql = "SELECT `productID` FROM `Products` ORDER BY RAND() LIMIT 1;";
        }//Clean-up
   	 
    	System.out.println("Creating statement...");
    	
		try {
			stmt = conn.createStatement();
			System.out.println(prd_sql);
			prd_rs = stmt.executeQuery(prd_sql);

			// Extract data from result set
			while (prd_rs.next()) {
				// Retrieve by column name
				productID = prd_rs.getInt("productID");
			}

		} catch (Exception ex) { // An error occurred
			// Log the exception
			System.out.println("Failed to get Product by purpose");
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			return new Product();
		}

        try {
            prd_rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
 
        prd = AuthDAO.getProductById(productID);
        return prd;
      
                  
        }
    public static Product[] getRecommendedProducts(int userID)
    {
    		Product[] prd = null;
    		
    		//Code goes here
    		Statement stmt;
            ResultSet rs;
            String sql;
            String color="";
            String priceRange="";
            int sellerID=0;
            int use=0;
            int categoryID=0;
            int rowCt = 0;
            System.out.println("Creating statement...");
            Connection conn = createConn();
            try {
                stmt = conn.createStatement();
            
	            sql = "SELECT * FROM  `SurveyResponses` WHERE userID ='"+userID+"';";
	            System.out.println(sql);
	            rs = stmt.executeQuery(sql);
	            //Extract data from result set
	            while (rs.next()) {
	                //Retrieve by column name
	            	int questionID = rs.getInt("questionID");
	
	            	switch(questionID){
	            		case 1:
	            			color = rs.getString("responseText");
	            			break;
	            		case 2:
	            			categoryID = rs.getInt("responseText");
	            			break;
	            		case 3:
	            			priceRange = rs.getString("responseText");
	            			break;
	            		case 4:
	            			use= rs.getInt("responseText");
	            			break;
	            		case 5:
	            			sellerID = rs.getInt("responseText");
	            			break;
	            	}
	            	rowCt++;
	            }
            }
            catch (Exception ex) { //An error occurred
                //Log the exception
            	System.out.println("Failed to get Product by color");
                Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
         if (rowCt > 0){
        	 prd = new Product[5];
	         prd[0]=getProductByColor(color);
	    	 prd[1]=getProductByCategory(categoryID);
	    	 prd[2]=getProductByPrice(priceRange);
	    	 prd[3]=getProductByPurpose(use);
	    	 prd[4]=getProductByManufacturer(sellerID);
         }
    	return prd;
    	
    }
    public static void InsertSurveyResponses(int userID, int questionID,  String responseText, String questionText) throws IOException, ClassNotFoundException {
            Statement stmt;
            String sql1,sql2;
            //ResultSet rs;
            Connection conn = AuthDAO.createConn();
		    //Execute query to insert seller details
            System.out.println("Creating statement...");
		
		    try {
		    stmt = conn.createStatement();
		   
		    sql1 = "INSERT INTO `SurveyResponses` (`userID`,`questionID`,`responseText`,`questionText`) "
		    + "VALUES ('" +userID+ "','" +questionID+"','"  + responseText + "','"  + questionText +"');";
		                System.out.println(sql1);
		    stmt.executeUpdate(sql1);
		    //System.out.println(sql2);
		    //stmt.executeUpdate(sql2);
            
          
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        //return responseID;
    }   
    
    public static void DeleteOldSurveyResponses(int userID) throws IOException, ClassNotFoundException {		
   	 Statement stmt;		
        String sql;		
       		
        Connection conn = AuthDAO.createConn();		
		    //Execute query to insert seller details		
        System.out.println("Creating statement...");		
				
		    try {		
		    stmt = conn.createStatement();		
		   		
		               		
		    sql="DELETE IGNORE FROM `SurveyResponses` WHERE `userID`='"+userID+"'";		
		                System.out.println(sql);		
		    stmt.executeUpdate(sql);		
		    		
        		
      		
    } catch (SQLException | NumberFormatException ex) { //An error occurred		
        //Log the exception		
        Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);		
    }		
   		
} 
    
    public static ArrayList<Integer> getProductAverageRating(int productID){
    	float rating = 0;
    	int avgRating = 0;
    	//int count = 0;
    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
        ArrayList<Integer> RatingAndCount = new ArrayList<Integer>();
         
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT productID, AVG( ranking ) AS avg_rating, COUNT( * ) AS count FROM ProductReviews WHERE productID='" + productID + "' group by productID;";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
           	 rating=rs.getFloat("avg_rating");
           	RatingAndCount.add(rs.getInt("count"));
           	avgRating = Math.round(rating);
            RatingAndCount.add(avgRating);
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get Rating average by productID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return new Product();
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
        
    	return RatingAndCount;
    	
    }
    
    public static List<Product> getProductsbyCategory(int categoryID){
		List<Product> productList = new ArrayList<Product>();
		

    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT p . * , pr.rankingAVG, pr.count FROM Products" + 
            " p LEFT JOIN (SELECT productID, AVG( ranking ) AS rankingAVG, COUNT( * ) AS count "+
            		"FROM ProductReviews GROUP BY productID)pr ON p.productID = pr.productID WHERE categoryID ='"
					+ categoryID + "' AND removed = 0 order by productName;";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
            	Product prod = new Product();
            	prod.SetProductName(rs.getString("productName"));
            	prod.SetDescription(rs.getString("description"));
            	prod.SetNumInStock(rs.getInt("quantity"));            	
            	prod.SetSpecs(rs.getString("specs"));
            	prod.setPicture(rs.getBytes("pictureBlob"));
            	prod.SetProductID(rs.getInt("productID"));
            	prod.SetPrice(rs.getFloat("unitPrice"));
            	float rating = rs.getFloat("rankingAVG");
            	prod.SetRating(Math.round(rating));
            	prod.setReviewCount(rs.getInt("count"));
                //Retrieve by column name
            	productList.add(prod); 
            	System.out.println(rs.getString("productName"));
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get Rating average by productID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return new Product();
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
        
    	return productList;
    	
    }
    
    public static int checkout(int userID, Cart cart, String shippingName, String shippingAddr, String shippingCity, String shippingState, String shippingZip, String paypalEmail){
    	boolean returnVal = false;
    	
    	Statement stmt;
        ResultSet rs;
        String sql;
        int orderID = -1;
        Connection conn = createConn();
        List<CartItem> itemList;
 
        //Execute query to check username and password
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `Orders` "
            		+ "(`buyerID`,`receiverName`,`receiverAddress`,`receiverCity`,`receiverState`,`receiverZip`,`buyerPaypalEmail`,`shippingMethod`,`orderTimestamp`) "
            		+ "VALUES ('"+userID+"','"+shippingName+"','"+shippingAddr+"','"+shippingCity+"','"+shippingState+"','"+shippingZip+"','"+paypalEmail+"','"+cart.GetShippingMethod()+"',NOW());";
            System.out.println(sql);
            stmt.executeUpdate(sql);
 
            //Get ID of newly created user
            sql = "SELECT MAX(`orderID`) FROM `Orders`";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
            	orderID = Integer.parseInt(rs.getString("MAX(`orderID`)"));
            }
            
            if (orderID != -1){
            	itemList = cart.GetAllItems();
            	Product prd;
            	float shippingPrice;
            	
				if (!itemList.isEmpty()){
					for (int i=0; i < itemList.size(); i++){
						shippingPrice = -1.0f;
						prd = AuthDAO.getProductById(itemList.get(i).GetProductID());
						switch(cart.GetShippingMethod()){
							case 1:
								shippingPrice = prd.GetGroundShippingCost();
								break;
							case 2:
								shippingPrice = prd.GetTwoDayShippingCost();
								break;
							case 3:
								shippingPrice = prd.GetNextDayShippingCost();
								break;
						}
						
			            sql = "INSERT INTO `OrderItems` "
			            		+ "(`orderID`,`productID`,`quantity`,`unitPrice`,`shippingPrice`,`tax`) "
			            		+ "VALUES ('"+orderID+"','"+prd.GetProductID()+"','"+itemList.get(i).GetQuantity()+"','"+prd.GetPrice()+"','"+shippingPrice+"','"+((prd.GetPrice()+shippingPrice)*0.07)+"');";
			            
			            System.out.println(sql);
			            stmt.executeUpdate(sql);
			            
			            //Reduce the item quantity from the Products table
						sql = "UPDATE `Products` SET `quantity`=(`quantity` - "+itemList.get(i).GetQuantity()+") WHERE `productID` = '"+prd.GetProductID()+"'";
			            stmt.executeUpdate(sql);
						System.out.println(sql);
					}
				}
	            return orderID;
            }
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	return -1;
    }
    
    public static boolean notifyUser(Notification notification){
    	Statement stmt;
        //ResultSet rs;
        String sql;
       // int orderID = -1;
        Connection conn = createConn();
        int toUserID = notification.getToUserID(); 
        char notificationType = notification.getNotificationType();
        String notificationText = notification.getNotificationMessage();
		int aboutUserID = notification.getAboutUserID();
		int typeID = notification.getTypeID();
        
        try {
		    stmt = conn.createStatement();
		   
		    sql = "INSERT INTO Notifications (toUserID, type, text, aboutUserID, typeID) "
		    + "VALUES ('" +toUserID+ "','" +notificationType+"','"  + notificationText + "','"  + aboutUserID +"','" +typeID +"');";
		    
		    System.out.println(sql);
		    stmt.executeUpdate(sql);
		    //System.out.println(sql2);
		    //stmt.executeUpdate(sql2);
            
          return true;
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    	
    	return false;
    }

	public static boolean CancelItem(int userID, int orderID, int productID) {
		Statement stmt;
		String sql;
		Connection conn = createConn();

		System.out.println("Creating statement...");

		try {
			stmt = conn.createStatement();
			sql = "UPDATE `OrderItems` INNER JOIN (`Orders`,`Products`) ON (`Orders`.`orderID`=`OrderItems`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID`) SET `OrderItems`.`canceled`='1', `Products`.`quantity` = (`Products`.`quantity` + `OrderItems`.`quantity`) WHERE (`Orders`.`buyerID`='" + userID + "' OR `Products`.`sellerID`='" + userID + "') AND `Orders`.`orderID`='" + orderID + "' AND `OrderItems`.`productID`='" + productID + "' AND `OrderItems`.`hasShipped`='0'";
			System.out.println(sql);
			if (stmt.executeUpdate(sql) == 0){ //If there were no rows updated
				return false; //The item was not canceled
			}else{ //There were rows updated
				return true; //The item was canceled
			}
		} catch (SQLException | NumberFormatException ex) { // An error occurred
			// Log the exception
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public static boolean UpdateOrderQuantity(int userID, int orderID, int productID, int quantity) {
		Statement stmt;
		String sql;
		Connection conn = createConn();

		System.out.println("Creating statement...");

		try {
			stmt = conn.createStatement();
			sql = "UPDATE `OrderItems` INNER JOIN (`Orders`,`Products`) ON (`Orders`.`orderID`=`OrderItems`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID`) SET `OrderItems`.`quantity`='"+quantity+"', `Products`.`quantity`=(`Products`.`quantity` + `OrderItems`.`quantity` - "+quantity+")  WHERE `Orders`.`buyerID`='" + userID + "' AND `Orders`.`orderID`='" + orderID + "' AND `OrderItems`.`productID`='" + productID + "' AND `OrderItems`.`hasShipped`='0'";
			System.out.println(sql);
			if (stmt.executeUpdate(sql) == 0){ //If there were no rows updated
				return false; //The item was not canceled
			}else{ //There were rows updated
				return true; //The item was canceled
			}
		} catch (SQLException | NumberFormatException ex) { // An error occurred
			// Log the exception
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public static boolean MarkItemAsShipped(int sellerID, int orderID, int productID) {
		Statement stmt;
		String sql;
		Connection conn = createConn();

		System.out.println("Creating statement...");

		try {
			stmt = conn.createStatement();
			sql = "UPDATE `OrderItems` INNER JOIN (`Orders`,`Products`) ON (`Orders`.`orderID`=`OrderItems`.`orderID` AND `OrderItems`.`productID`=`Products`.`productID`) SET `OrderItems`.`hasShipped`='1'  WHERE `Products`.`sellerID`='" + sellerID + "' AND `Orders`.`orderID`='" + orderID + "' AND `OrderItems`.`productID`='" + productID + "'";
			System.out.println(sql);
			if (stmt.executeUpdate(sql) == 0){ //If there were no rows updated
				return false; //The item was not canceled
			}else{ //There were rows updated
				return true; //The item was canceled
			}
		} catch (SQLException | NumberFormatException ex) { // An error occurred
			// Log the exception
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	


    public static int GetQtyOfItemInOrder(int productID, int orderID) {
   	 Statement stmt;
     ResultSet rs;
     String prd_sql;
     
     Connection conn = createConn(); //Create DB connection
     
     //Execute query to check for matching product
     System.out.println("Creating statement...");
     try {
         stmt = conn.createStatement();
         prd_sql = "SELECT `quantity` FROM `OrderItems` WHERE `productID`='"+productID+"' and `orderID`='"+orderID+"'";
         System.out.println(prd_sql);
         rs = stmt.executeQuery(prd_sql);

         //Extract data from result set
         while (rs.next()) {
             //Retrieve by column name
        	 return rs.getInt("quantity");
         }       
         
        
         
     } catch (Exception ex) { //An error occurred
         //Log the exception
     	System.out.println("Failed to get Product by ID");
         Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
         return -1;
     }

     //Clean-up
     try {
    	 rs.close(); //Close result set
         stmt.close(); //Close statement object
     } catch (Exception ex) { //An error occurred
         //Log the exception
         //If it fails to close, just leave it.
     }

     return -1;
     
    }
    
    public static List<Notification> getUserNotifications(int userID){
    	List<Notification> notifications = new ArrayList<Notification>();
    	
    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM `Notifications` WHERE toUserID = '"
					+ userID + "' order by insertTime desc;";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
            	Notification notification = new Notification(
            			rs.getInt("notificationID"),
            			rs.getInt("toUserID"),
            			rs.getString("type").charAt(0),
            			rs.getString("text"),
            			rs.getInt("aboutUserID"),
            			rs.getInt("typeID"),
            			rs.getString("insertTime")
            			);
            	
            	notifications.add(notification); 
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get notifications by userID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return new Product();
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
    	
    	return notifications;
    }
    
    public static Notification getNotificationByID(int notificationID){
    	Notification notification = null;
    	
    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM `Notifications` WHERE notificationID = '"
					+ notificationID + "';";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
            	 notification = new Notification(
            			rs.getInt("notificationID"),
            			rs.getInt("toUserID"),
            			rs.getString("type").charAt(0),
            			rs.getString("text"),
            			rs.getInt("aboutUserID"),
            			rs.getInt("typeID"),
            			rs.getString("insertTime")
            			);
            	
            	
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get notifications by userID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return new Product();
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
    	
    	return notification;
    }
    
    public static int getOrderBuyer(int orderID){
    	int buyerID = -1;
    	
    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT buyerID FROM `Orders` WHERE orderID = '"
					+ orderID + "';";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
            	buyerID = rs.getInt("buyerID");
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get buyerID by orderID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return new Product();
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
    	
    	
    	return buyerID;
    }
	
	 public static String GetCompanyName(int sellerID){
    	String companyName = "";
    	Statement stmt = null;
        String sql;
        ResultSet rs = null;
        Connection conn = AuthDAO.createConn();
    	
        try {
            stmt = conn.createStatement();
            sql = "SELECT `companyName` FROM `SellerDetails` WHERE `sellerID` = '"+ sellerID + "';";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            //Extract data from result set
            while (rs.next()) {
            	companyName = rs.getString("companyName");
            }       
            
        } catch (Exception ex) { //An error occurred
            //Log the exception
        	System.out.println("Failed to get buyerID by orderID");
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Clean-up
        try {
        	rs.close(); //Close result set
            stmt.close(); //Close statement object
        } catch (Exception ex) { //An error occurred
            //Log the exception
            //If it fails to close, just leave it.
        }
    	
    	
    	return companyName;
    }
	 
	public static boolean verifySeller(int sellerID){
		Statement stmt;
		String sql;
		Connection conn = createConn();

		System.out.println("Creating statement...");

		try {
			stmt = conn.createStatement();
			sql = "UPDATE `SellerDetails` SET `SellerDetails`.`isVerified`='1'  WHERE `SellerDetails`.`sellerID`='" + sellerID + "'";
			System.out.println(sql);
			if (stmt.executeUpdate(sql) == 0){ //If there were no rows updated
				return false; //The item was not canceled
			}else{ //There were rows updated
				return true; //The item was canceled
			}
		} catch (SQLException | NumberFormatException ex) { // An error occurred
			// Log the exception
			Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
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

