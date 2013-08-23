import org.junit.*;

import com.mysql.jdbc.Blob;

import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
//	@Before
//	public void resetDatabase(){
//		Fixtures.deleteDatabase();
//	}

    @Test
    public void userTest(){
    	User user = new User("albertfiati","Albert","Fiati-Kumasenu","awkfiati@gmail.com","albert",true).save();
    	User user1 = new User("albertfiati","Albert","Fiati-Kumasenu","awkfiati@gmail.com","albert",true).save();
    	assertEquals("active",user.status);
    	
    	user.blockUser();
    	
    	assertEquals("blocked",user.status);
    	assertNotNull(user);
    	assertNull(user.library);
    	
    	user.addLibrary();
    	
    	assertEquals(2, User.findAll().size());
    	assertEquals(2,User.count());
    	assertNotNull(user.library);
    }
    
    @Test
    public void libraryTest(){
    	User user = new User("albertfiati","Albert","Fiati-Kumasenu","awkfiati@gmail.com","albert",true).save();
    	Library library = new Library("Studying Hard", user).save();
    	assertEquals(0, library.shelves.size());
    	
    	Shelve shelve = new Shelve("Technology",library);
    	Shelve shelve2 = new Shelve("Business",library);
    	library.addShelve(shelve);
    	library.addShelve(shelve2);
    	
    	assertEquals(2,library.shelves.size());
    	library.removeShelve(shelve2);
    	
    	assertEquals(1, library.shelves.size());
    	assertEquals("Studying Hard", library.name);
    	assertEquals(user,library.user);
    	assertEquals(1, Library.count());
    	assertNotNull(library);
    	
    }
    
    @Test
    public void shelveTest(){
    	  
    }
    
    @Test
    public void categoryTest(){
    	Category category = new Category("Technology").save();
    	Category category1 = Category.findOrCreateByName("Business Finance");
    	
    	assertNotNull(category);  
    	assertNotNull(category1);
    }
    
    @Test
    public void bookTest(){
    	    	  	
    }
    
    @Test
    public void commentTest(){
    	
    }
    
    @Test
    public void ratingTest(){
    	Rating rating = Rating.findOrCreateByName("1 Star");
    	
    	assertNotNull(rating);
    }

}
