package models;

import javax.persistence.Entity;

import org.hamcrest.core.IsNull;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Category extends Model implements Comparable<Category>{
	@Required(message="Category Name Required")
	public String category;
	
	public Category(String category){
		this.category = category;
	}
	
	public static Category findOrCreateByName(String categoryName){
		Category category = Category.find("byCategory",categoryName).first();
		if(category==null){
			category = new Category(categoryName).save();
		}
		return category;
	}

	@Override
	public int compareTo(Category category) {
		return this.compareTo(category);
	}
	
	public String toString(){
		return this.category;
	}
}
