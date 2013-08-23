package models;


import java.util.*;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Book extends Model{
	@Required(message="Book title is required")
	public String title;
	@Required(message="Category id is required")
	@ManyToOne(cascade=CascadeType.PERSIST)
	public Category category;
	@Required(message="Book is required")
	public String book;
	@ManyToMany
	public List<Shelve> shelves;
	@OneToMany(mappedBy="book", cascade=CascadeType.ALL)
	public List<Comment> comments;
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Rating> ratings;
	
	@Lob
	public String preview;
	
	@Required
    public Date dateAdded;
	
	public Book(String bookTitle,String book, String preview){
		this.title = bookTitle;
		this.book = book;
		this.preview = preview;
		this.shelves = new ArrayList<>();
		this.comments = new ArrayList<>();
		this.ratings = new ArrayList<>();
		this.dateAdded = new Date();
	}
	
	public Book categorize(String category){
		this.category = (Category.findOrCreateByName(category));
		this.save();
		return this;
	}
	
	public Book addComment(String content,String author){
		Comment comment = new Comment(this,content,author);
		this.comments.add(comment);
		this.save();
		return this;
	}
	
	public Book rate(String rating){
		this.ratings.add(Rating.findOrCreateByName(rating));
		this.save();
		return this;
	}
}

