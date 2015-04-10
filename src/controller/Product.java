package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
	private static String productID;
	private static int sellerID;
	private static  String name;
	private static String description;
	private static String specs;
	private static float price;
	private static float shippingCost;
	private static float rating;
	private static int numInStock;
	private static int categoryID;
	
	Product(){
		productID = "-1";
		sellerID = -1;
		name = "";
		description = "";
		specs = "";
		price = 0.0f;
		//shippingCost = 0.0f;
		//rating = 0.0f;
		categoryID=0;
		numInStock = 0;
	}
	
	
	
	public static String insertproductdetails( String sellerID, String name,  String description,String specs,  String price, String categoryID, String numInStock) {
	   	 
        Statement stmt;
        String sql;
        ResultSet rs;
        Connection conn = AuthDAO.createConn();
        productID="-1";
 
        //Execute query to insert seller details
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO `Products` (`sellerID`,`categoryID',`productName`,`unitPrice`,'quantity','description','specs') VALUES ( '" + sellerID +"','" +categoryID+"','"+ name + "','"+price+"','"+numInStock+"','"+description+"','"+specs+"');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            s
            sql = "SELECT `productID` FROM `Products` WHERE `sellerID`='" + sellerID + "' ";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) { //Get newly created user ID,
                //Retrieve by column name
                productID = (rs.getString("productID"));
            }
        } catch (SQLException | NumberFormatException ex) { //An error occurred
            //Log the exception
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
 
        return productID;
    }
 
 
	
	
	public String GetProductID(){
		return productID;
	}
	
	public void SetProductID(String x /*in, product ID*/){
		productID = x;
	}
	
	public int GetSellerID(){
		return sellerID;
	}
	
	public void SetSellerID(int x /*in, seller ID*/){
		sellerID = x;
	}
	
	public String GetName(){
		return name;
	}
	
	public void SetSellerID(String x /*in, product name*/){
		name = x;
	}
	
	public String GetDescription(){
		return description;
	}
	
	public void SetDescription(String x /*in, product description*/){
		description = x;
	}
	
	public String GetSpecs(){
		return specs;
	}
	
	public void SetSpecs(String x /*in, product specs*/){
		specs = x;
	}
	
	public float GetPrice(){
		return price;
	}
	
	public void SetPrice(float x /*in, product price*/){
		price = x;
	}
	
	public float GetShippingCost(){
		return shippingCost;
	}
	
	public void SetShippingCost(float x /*in, product shipping cost*/){
		shippingCost = x;
	}
	
	public float GetRating(){
		return rating;
	}
	
	public void SetRating(float x /*in, product rating*/){
		rating = x;
	}
	
	public int GetNumInStock(){
		return numInStock;
	}
	
	public void SetNumInStock(int x /*in, number of products in stock*/){
		numInStock = x;
	}
}