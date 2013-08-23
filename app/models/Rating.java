package models;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Rating extends Model implements Comparable<Rating>{
	public String rating;
	
	public Rating(String rating){
		this.rating = rating;
	}
	
	public static Rating findOrCreateByName(String ratings){
		Rating rating = Rating.find("byRating",ratings).first();
		if(rating==null){
			rating = new Rating(ratings).save();
		}
		return rating;
	}

	@Override
	public int compareTo(Rating rating) {
		return this.compareTo(rating);
	}
	
	public String toString(){
		return this.rating;
	}
	
}
