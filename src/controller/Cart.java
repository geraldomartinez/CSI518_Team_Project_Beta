package controller;

import java.util.*;
import controller.CartItem;

public class Cart {
	
	private final float TAX_PERCENTAGE = 0.07f;
	private List<CartItem> items;
	
	Cart(){
		items = new ArrayList<CartItem>();
	}
	
	public List<CartItem> GetAllItems(){
		return this.items;
	}
	
	public void AddItem(int productID, int qty){
		//First check to see if the item already exists in the cart
		CartItem tempItem;
		Boolean itemFound = false;
		
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){
				tempItem = this.items.get(i);
				tempItem.SetQuantity(this.items.get(i).GetQuantity() + qty);
				this.items.set(i, tempItem);
				itemFound = true;
				break;
			}
		}
		
		if (!itemFound){ //If the item didn't already exist in the cart
			tempItem = new CartItem(productID,qty); //Build the cart item object
			this.items.add(tempItem); //Add the cart item object to the cart
		}
	}
	
	public void AddItem(CartItem newItem){
		//First check to see if the item already exists in the cart
		Boolean itemFound = false;
		
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == newItem.GetProductID()){
				this.items.get(i).SetQuantity(this.items.get(i).GetQuantity() + newItem.GetQuantity()); //Increase the quantity
				itemFound = true;
				break;
			}
		}
		
		if (!itemFound){ //If the item didn't already exist in the cart
			this.items.add(newItem); //Add the cart item object to the cart
		}
	}
	
	public void RemoveItem(int productID){
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){
				this.items.remove(i);
			}
		}
	}
	
	public void UpdateQuantity(int productID, int qty){ 
		CartItem tempItem;
		
		if (qty <= 0){
			this.RemoveItem(productID);
			return;
		}
		
		for (int i=0; i < this.items.size(); i++){
			if (this.items.get(i).GetProductID() == productID){
				tempItem = this.items.get(i);
				tempItem.SetQuantity(qty);
				this.items.set(i, tempItem);
			}
		}
	}
	
	public float GetCost(){
		float cost = 0.0f;
		Product prod = new Product(); //Prevent null exception
		
		for (int i=0; i < this.items.size(); i++){
			prod = AuthDAO.getProductById(this.items.get(i).GetProductID());
			if (prod.GetPrice() == -1.0f){
				return -1.0f; //Return -1.0f to indicate error
			}else{
				cost += prod.GetPrice();
			}
		}
		
		return cost;
	}
	
	public float GetTax(){		
		return (this.GetCost() * this.TAX_PERCENTAGE);
	}
	
	public float GetShippingCost(){
		float cost = 0.0f;
		Product prod = new Product(); //Prevent null exception
		
		for (int i=0; i < this.items.size(); i++){
			prod = AuthDAO.getProductById(this.items.get(i).GetProductID());
			if (prod.GetShippingCost() == -1.0f){
				return -1.0f; //Return -1.0f to indicate error
			}else{
				cost += prod.GetShippingCost();
			}
		}
		
		return cost;
	}
	
	public float GetTotal(){
		return (this.GetCost() + this.GetTax() + this.GetShippingCost());
	}
	
}