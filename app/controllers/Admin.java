package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Controller {
	@Before
	public static void checkAuthentication() {
	 if (!session.contains("userid")) {
	 	Application.index();
	 }
	}
	
	public static void resources(){
		if(session.get("userid")!=null){
			List<Book> books = Book.findAll();
			List<Category> categories = Category.findAll();
			Long userid = Long.parseLong(session.get("userid"));
			User user = User.findById(userid);
			renderArgs.put("books",books);
			renderArgs.put("categories",categories);
			renderArgs.put("user",user);
			render();
		}

		Application.index();
	}
	
	public static void users(){
		if(session.get("userid")!=null){
			List<User> users = User.findAll();
			renderArgs.put("users", users);
			render();
		}
		
		Application.index();
	}
	
	public static void blockUser(Long userid){
		User user = User.findById(userid);
		user.blockUser();
		users();
	}
	
	public static void activateUser(Long userid){
		User user = User.findById(userid);
		user.activateUser();
		users();
	}
	
	public static void makeUserAdmin(Long userid) {
		User user = User.findById(userid);
		if (user.isAdmin == false) {
			user.makeUserAdmin();
			users();
		} else {
			user.unmakeUserAdmin();
			users();
		}
		
	}
	
	public static void deleteResource(Book book){
		book.delete();
		List<Book> books = Book.findAll();
		renderArgs.put("books",books);
		render();
	}
	
	public static void confirmEdits(){
		renderText("Confirm resource edits");
	}
}

