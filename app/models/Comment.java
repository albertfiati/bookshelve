package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Comment extends Model {
	@ManyToOne
	public Book book;
	public String author;
	@Lob
	public String content;
	
	@Required
    public Date postedAt;
	
	public Comment(Book book,String content,String author){
		this.book = book;
		this.content = content;
		this.author = author;	
		this.postedAt = new Date();			
	}
}
