package controller;
import controller.CartItem;
import controller.Cart;

public class WishList extends Cart{
	int quantity=0;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public WishList() {
		super(); //Call Cart's constructor
	}
	
	public void AddItems2CartObj(Cart c){
		CartItem tempItem;
		for (int i=0; i < this.items.size(); i++){
			tempItem = this.items.get(i);
			for (int j = 0; j < tempItem.GetQuantity(); j++){
				c.AddItem(tempItem.GetProductID());
			}
		}
	}
	
}
