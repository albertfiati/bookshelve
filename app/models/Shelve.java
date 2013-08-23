package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Shelve extends Model {
	@Required(message="Shelve name is required")
	public String name;
	@ManyToOne
	public Library library;
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Book> books;
	
	public Shelve(String shelvename,Library library){
		this.name = shelvename;
		this.library = library;
		this.books = new ArrayList<>();
	}
	
	public void addBook(Book book){
		this.books.add(book);
		this.save();
	}
	
	public void removeBook(Book book){		
		this.books.remove(books.indexOf(book));
		this.save();
	}
	
	public void transferBook(Book book,Shelve newShelve){
		this.removeBook(book);
		newShelve.addBook(book);
		this.save();
		newShelve.save();
	}
}

