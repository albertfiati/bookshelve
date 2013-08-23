package models;

//import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

//import org.joda.time.DateTime;

import java.util.*;
import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class User extends Model{
	@Required(message="Username is required!")
	public String username;
	@Required(message="First name is required")
	public String firstname;
	@Required(message="Last name is required")
	public String lastname;
	@Required(message="Email is required")
	public String email;
	@Required(message="Password is required")
	public String password;
	@Required(message="Type of user is required")
	public boolean isAdmin;
	public Date created_at;
	public Date updated_at;
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
	public Library library;
	public String status;
	
	public User(String username,String firstname,String lastname, String email, String password,Boolean isAdmin){
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
//		TODO:fix date
//		DateTime now=new DateTime().now();
		this.created_at = new Date();
		this.library = null;
		this.status = "active";
	}
	
	public void addLibrary(){
		library = new Library(this.username, this).save();		
	}
		
	public void blockUser(){
		this.status = "blocked";
		this.save();
	}
	
	public void activateUser(){
		this.status = "active";
		this.save();
	}
	
	public void makeUserAdmin() {
		this.isAdmin = true;
		this.save();
	}
	
	public void unmakeUserAdmin() {
		this.isAdmin = false;
		this.save();
	}
	
}

