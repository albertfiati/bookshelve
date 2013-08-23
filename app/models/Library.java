package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.mapping.Array;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Library extends Model{
	@Required(message="Library name required")
	public String name;
	@OneToOne
	public User user;
	@OneToMany(mappedBy="library", cascade=CascadeType.ALL)
	public List<Shelve> shelves;
	
	public Library(String libraryName,User user){
		this.name = libraryName;
		this.user = user;
		shelves = new ArrayList<>();
	}
	
	public void addShelve(Shelve shelve){
		this.shelves.add(shelve);
		this.save();
	}
	
	public void removeShelve(Shelve shelve){
		this.shelves.remove(this.shelves.indexOf(shelve));
		this.save();
		System.out.println(this.shelves.size());
		//System.out.println(this.shelves.count());
	}
	
	public void renameShelve(Shelve shelve){
		
	}
}
