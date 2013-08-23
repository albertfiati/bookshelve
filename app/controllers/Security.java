package controllers;

import java.lang.reflect.InvocationTargetException;
import play.Play;
import play.mvc.*;
import play.data.validation.*;
import models.*;


public class Security extends Controller{
	public static void index() {
	render();
	}
	
	//@Before
	//static boolean checkAuthentication(String email, String password){
		//return User.verify(email, password) != null;
	//}
	
	//static void onDisconnected() {
      //  Application.index();
    //}
	
	//@After
	static void log(){
		
	}
	
	//@Finally
	static void logAll(){
		
	}
}
