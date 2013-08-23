package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import java.util.*;

import models.*;


@With(Secure.class) 
public class LibraryUser extends Controller {
	@Before
	public static void checkAuthentication() {
	 if (!session.contains("userid")) {
	 	Application.index();
	 }
	}
	
	public static void library(){
		Long userid = Long.parseLong(session.get("userid"));
		User user = User.findById(userid);
		
		if(user!=null){
			Library library = user.library;
			List<Shelve> shelves = library.shelves;
			renderArgs.put("library", library);
			renderArgs.put("shelves", shelves);
			renderArgs.put("user", user);
			render();
		}
		
		Application.index();
	}
	
	public static void addShelve(@Required(message="Shelve name is required") String name){
		if(validation.hasErrors()){
			validation.keep();
			params.flash();
			library();
		}
		
		User user = User.findById(Long.parseLong(session.get("userid")));
		if(user!=null){			
			Library library = user.library;
			if(library!=null){
				//TODO:check for repetition
				Shelve shelve = new Shelve(name, library).save();				
				library.addShelve(shelve);
			}
			library();
		}
	}
	
	public static void removeShelve(Long shelveid) {
		Long userid = Long.parseLong(session.get("userid"));
		User user = User.findById(userid);
		
		Shelve shelve = Shelve.findById(shelveid);
		Library library = user.library;
		
		//System.out.println(shelve.name);
		library.removeShelve(shelve);
		library();
	}
	
	public static void addBooks(){
		Long userid = Long.parseLong(session.get("userid"));
		User user = User.findById(userid);
		List<Book> books = Book.findAll();
		List<Category> categories = Category.findAll();
		renderArgs.put("books",books);
		renderArgs.put("categories",categories);
		renderArgs.put("shelveid",params.get("shelveid"));
		renderArgs.put("user", user);
		render();
	}
	
	public static void addBookToShelve(Long shelveid,ArrayList<String> books){
		Shelve shelve = Shelve.findById(shelveid);
		
		if(shelve!=null){
			for(String s :books){
				//TODO:eliminate duplicates
				Book book = Book.findById(Long.parseLong(s));				
				shelve.addBook(book);
			}
		}
		
		library();
	}
	
	public static void removeBookFromShelve(Long shelveid,Long bookid){
		Shelve shelve = Shelve.findById(shelveid);
		Book book = Book.findById(bookid);
		shelve.removeBook(book);
		library();
	}
	
	public static void reviewBook(Long bookid, Long shelveid) {
		Book book = Book.findById(bookid);
		Long userid = Long.parseLong(session.get("userid"));
		User user = User.findById(userid);
		renderArgs.put("book", book);
		renderArgs.put("user", user);
		renderArgs.put("shelveid", shelveid);
		render();
	}
	
	public static void tansferBookFromShelve(){
		renderText("Transfer book from shelve");
	}
	
	public static void share(){
		renderText("Share book");
	}
	
	public static void downloadBook(){
		renderText("Download book");
	}
	
	public static void rateBook(){
		renderText("Rate book");
	}
	
	public static void commentOnBook(
		@Required Long bookid,
		@Required Long shelveid,
		@Required (message = "Your name is required") String author,
		@Required (message = "You haven't entered anything to comment") String content
		){
		
		if (validation.hasErrors()) {
			validation.keep();
			params.flash();
			reviewBook(bookid, shelveid);
		}
		Book book = Book.findById(bookid);
		book.addComment(content, author);
		flash.success("Comment added successfully, %s", author);
		reviewBook(bookid, shelveid);
	}
	
	public static void editBook(Long shelveid, Long bookid) {
		Long userid = Long.parseLong(session.get("userid"));
		User user = User.findById(userid);
		
		Library library = Library.find("byUser", user).first();
		renderArgs.put("library", library);
		
		Book book = Book.findById(bookid);
		renderArgs.put("book", book);
		
		List <Category> categories = Category.findAll();
		renderArgs.put("categories", categories);
		
		Shelve shelve = Shelve.findById(shelveid);
		renderArgs.put("currentShelve", shelve);
		
		render();
	}
}

