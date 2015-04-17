package controller;

public class CartItem {
	private int productID;
	private int quantity;
	
	CartItem(){
		this.productID = -1;
		this.quantity = -1;
	}
	
	CartItem(int id, int qty){
		this.productID = id;
		this.quantity = qty;
	}
	
	public int GetProductID(){
		return this.productID;
	}
	
	public void SetProductID(int x){
		this.productID = x;
	}
	
	public int GetQuantity(){
		return this.quantity;
	}
	
	public void SetQuantity(int x){
		this.quantity = x;
	}
}
