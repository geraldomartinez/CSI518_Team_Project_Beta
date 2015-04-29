package controller;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class User implements Serializable {

    private String firstName; //User's first name
    private String middleName;//User's middle name
    private String lastName; //User's last name
    private String phone;//User's phone number 
    private String address;//User's address
    private String city;//User's city
    private String state;//User's state
    private String zip;//User's zip
    private String email;
    private  String accountType;
    private String password; //User's password to the website
    private static int userID; //User's unique ID
    private int active;

    public User() {
        //If no information is given, just initialize everything to default [empty] values
        this.firstName = "";
        this.middleName="";
        this.lastName = "";
        this.phone="";
        this.address="";
        this.city="";
        this.state="";
        this.zip="";
        this.accountType="";
        this.email="";
        this.password = "";
        this.userID = -1;
    }

    public User(int userID /*in, User's unique id*/,
            String accountType /*in, user's account type*/,
            String email /*in, user's email*/,
            String password /*in, user's password*/,
            String firstName /*in, user's first name*/,
            String middleName,
            String lastName /*in, user's last name*/,
            String phone,//User's phone number 
             String address,//User's address
             String city,//User's city
             String state,//User's state
             String zip) 
             {
        this.userID = userID; //Store the user's user ID
        this.firstName = ((firstName == null) ? "" : firstName); //Store the user's first name, Prevent null pointer exception
        this.lastName = ((lastName == null) ? "" : lastName); //Store the user's last name, Prevent null pointer exception
        this.phone=((phone == null) ? "" : phone);
        this.address=((address == null) ? "" : address);
        this.city=((city == null) ? "" : city);
        this.state=((state == null) ? "" : state);
        this.zip=((zip == null) ? "" : zip);
        this.email=((email == null) ? "" : email);
        this.accountType=((accountType == null) ? "" : accountType);
        this.password = ((password == null) ? "" : password); //Store the user's password, Prevent null pointer exception
    }

    /*Set the user's unique ID*/
    public void SetUserID(int id /*in, user's unique ID*/) {
        this.userID = id; //Set the user's unique ID
    }

    /*Return the user's unique ID*/
    public static int GetUserID() {
        return userID; //Return the user's unique ID
    }

    /*Set the user's password*/
    public void SetPassword(String pwd /*in, password*/) {
        this.password = pwd; //Set the user's password
    }

    /*Return the user's password*/
    public String GetPassword() {
        return password; //Return the user's password
    }

    /*Set the user's first name*/
    public void SetFirstName(String name /*in, user's first name*/) {
        this.firstName = name; //Set the user's first name
    }

    /*Return the user's first name*/
    public String GetFirstName() {
        return firstName; //Return the user's first name
    }

    /*Set the user's last name*/
    public void SetLastName(String name /*in, user's last name*/) {
        this.lastName = name; //Set the user's last name
    }

    /*Return the user's last name*/
    public String GetLastName() {
        return lastName; //Return the user's last name
    }

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public  String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
}
