package controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Date;
import play.Play;
import play.mvc.*;
import play.data.validation.*;
import play.libs.*;
import play.utils.*;
import models.*;

public class Secure extends Controller{
	static void checkAuthentication() {
		 
	}
	
	@After
	static void log(){
		
	}
	
//	@Catch(IllegalArguement.class)
//	static void logIllegalArguement(){
//		
//	}
	
	@Finally
	static void logAll(){
		
	}
}
