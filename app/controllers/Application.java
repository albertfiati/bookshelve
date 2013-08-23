package controllers;

import play.*;
import play.data.validation.Equals;
import play.data.validation.Required;
import play.mvc.*;

import java.io.File;
import java.util.*;

import net.sf.oval.constraint.Email;

import models.*;

@With(Secure.class)
public class Application extends Controller {
	public static void index() {
		if (session.contains("userid")) {
			LibraryUser.library();
		}
		render();
	}	
	
	public static void createAccount(
		@Required(message="Username is required") String username,
		@Required(message="Firstname is required") String firstname,
		@Required(message="Lastname is required") String lastname,
		@Required(message="Email is required") @Email(message="Invalid email address") String email,
		@Required(message="Password is required") String password,
		@Equals("password") String confirm
		){
			if(validation.hasErrors()){
				validation.keep();
				params.flash();
				renderArgs.put("form_error", "signup error");
				index();
			}
			
			if(password.equals(confirm)){
				//TODO: sort out flash
				if(User.find("byUsername", username).first()!=null){
					flash.error("%s has been registered already",username);
					renderArgs.put("form_error", "signup error");
					params.flash();
					index();
				}
				
				if(User.find("byEmail", email).first()!=null){
					//flash.discard();
					flash.error("%s has already been registered",email);
					renderArgs.put("form_error", "signup error");
					params.flash();
					index();
				} 			
				
				User user = new User(username,firstname,lastname,email,password,false).save();
				session.put("userid", user.id);
				
				if(user.isAdmin){
					Admin.resources();
				}
				
				user.addLibrary();	
				LibraryUser.library();
			}
	}

	public static void login(@Required String login_username, @Required String login_password){
		if(validation.hasErrors()){
			validation.keep();
			params.flash();
			index();
		}
		
		User user = User.find("byUsernameAndPassword",login_username,login_password).first();
		
		if (user == null) {
			flash.error("Invalid Credentials");
			params.flash();
			renderArgs.put("form_error", user);
			index();
		}
		
		session.put("userid", user.id);
		if (user.isAdmin) {
			Admin.resources();
		} else {			
			LibraryUser.library();
		}
		
	}
	public static void changePassword() {
		User user = User.findById(session.get("userid"));

		if (user != null) {
			String oldpassword = params.get("oldpassword");
			String newpassword = params.get("newpassword");
			String confirmpassword = params.get("confirm");

			if (oldpassword == user.password) {
				if (newpassword == confirmpassword) {
					user.password = newpassword;
					renderText("Successful");
				}

				renderTemplate("password mismatch");
			}

			renderText("Wrong old password");
		}

		renderText("Nothing changed");
	}

	public static void changeEmail() {
		User user = User.findById(session.get("userid"));

		if (user != null) {
			String oldemail = params.get("oldemail");
			String newemail = params.get("newemail");
			String confirmemail = params.get("confirm");

			if (oldemail == user.email) {
				if (newemail == confirmemail) {
					user.email = newemail;
					renderText("Successful");
				}

				renderTemplate("email mismatch");
			}

			renderText("Wrong old email");
		}

		renderText("Nothing changed");
	}

	public static void logout() {
		if(session.get("userid")!=null){
			session.clear();
		}
		
		index();
	}

	public static void addResource(
		@Required(message="Title is required") String title,
		@Required(message="Select or Enter new category") String category,
		File attachment) {
		
		if(validation.hasErrors()) {
			validation.keep();
			params.flash();
			Admin.resources();
		}
		
		Book book = new Book(title, null, null).save();
		book.categorize(category);
		
		Admin.resources();
	}

	public static void editResource(
	Long shelveid, 
	Long bookid,
	@Required (message = "Book title is required") String title,
	@Required (message = "You need to select or enter a new category") String category,
	String preview,
	Long newShelveid
	) {
		if (validation.hasErrors()) {
			validation.keep();
			params.flash();
			LibraryUser.editBook(shelveid, bookid);
		}
		
		Book book = Book.findById(bookid);
		book.title = title;
		book.categorize(category);
		
		if (preview != null || preview != "") {
			book.preview = preview;
		}
		
		if (shelveid != newShelveid) {
			Shelve oldShelve = Shelve.findById(shelveid);
			Shelve newShelve = Shelve.findById(newShelveid);
			
			oldShelve.transferBook(book, newShelve);
		}
		flash.success("Book or resource edited successfully");
		LibraryUser.editBook(newShelveid, bookid);
	}
}

