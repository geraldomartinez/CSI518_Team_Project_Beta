import controller.CartItem;
import controller.Cart;

public class WishList extends Cart{

	private String name;
	private int wishlistID;
	
	WishList() {
		super(); //Call Cart's constructor
		this.name = "";
		this.wishlistID = -1;
	}
	
	WishList(int id, String name) {
		super(); //Call Cart's constructor
		if (name == null){
			name = "";
		}
		
		this.name = name;
		this.wishlistID = id;
	}
	
	public String GetName(){
		return this.name;
	}
	
	public void SetName(String x){
		if (x == null){
			x = "";
		}
		this.name = x;
	}
	
	public int GetWishlistID(){
		return this.wishlistID;
	}
	
	public void SetName(int x){
		this.wishlistID = x;
	}
	
	public void AddItems2CartObj(Cart c){
		CartItem tempItem;
		for (int i=0; i < this.items.size(); i++){
			tempItem = this.items.get(i);
			c.AddItem(tempItem.GetProductID(), tempItem.GetQuantity());
		}
	}

}
