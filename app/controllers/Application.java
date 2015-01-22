package controllers;

import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import play.db.*;
import java.sql.*;
import java.util.ArrayList;

import javax.management.modelmbean.ModelMBeanAttributeInfo;

public class Application extends Controller {
	
	final static Form<Nutzer> loginForm = Form.form(Nutzer.class);
	final static Form<Nutzer> signInForm = Form.form(Nutzer.class);
	final static Form<Nutzer> editForm = Form.form(Nutzer.class);

	public static WebSocket<String> kommentar(){
		return new WebSocket<String>(){
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out){
				Kommentare.start(in, out);
			}	
		};
	}
	
	public static Result wsJs() {
		return ok(views.js.ws.render());
	}
	
	public static Result lied(String l){
		String user = session("name");
		if(user != null){
			Lied ll = Model.getInstance().getLied(l);
			ArrayList<Kommentar> alk = Model.getInstance().getKommentare(ll.trackID);
			System.out.println("Akt. Song: " + Model.getInstance().getAktuellerSong());
			return ok( lied.render( user, ll, alk ) );
		}else{
			user = "Gast";
			Lied ll = Model.getInstance().getLied(l);
			ArrayList<Kommentar> alk = Model.getInstance().getKommentare(ll.trackID);
			System.out.println("Akt. Song: " + Model.getInstance().getAktuellerSong());
			return ok( lied.render( user, ll, alk ) );
		}
	}
	
	public static Result suche(String wert){
		System.out.println("Application suche: " +wert);
		return ok (Model.getInstance().sucheErgänzen(wert) );
	}
	
	
	
	public static Result interpret(){ 
		String user = session("name");
		if(user != null){
			return ok( interpret.render( user, Model.getInstance().getInterpreten() ));
		}else{
			user = "Gast";
			return ok( interpret.render( user, Model.getInstance().getInterpreten() ));
		}
		
	}
	
	public static Result logout(){
		Model.getInstance().doLogout();
		session().clear();
		return redirect( "/news");
	}
	
	public static Result edit(){
		String user = session("name");
		if(user != null){
			return ok( edit.render( user, editForm) );
		}else{
			return ok( "/news" );
		}
	}
	
	public static Result editSubmit(){
		Form<Nutzer> editFormVoll = editForm.bindFromRequest();
		Model.getInstance().editNutzer(editFormVoll.get());
		String user = session("name");
		if(user != null){
			return ok( account.render( user, editFormVoll.get() ) );
		}else{
	    	return redirect("/edit");
	    }
	}

	public static Result loginSubmit(){
	    Form<Nutzer> loginFormVoll = loginForm.bindFromRequest();
	    String user;
	    
	    if(Model.getInstance().checkLogin(loginFormVoll.get()) != "Gast"){
	    	session("name", Model.getInstance().getNutzername());
		    user = session("name");
		    System.out.println("Login Session: " + user);
	    	return ok( account.render( user, Model.getInstance().getNutzer(user) ));
	    }else{
	    	return redirect("/login");
	    }	    
	}
	
	public static Result login(){ 
		String user = session("name");
		if(user != null){
			return ok( account.render( user, Model.getInstance().getNutzer(user) ) );
		}else{
			user = "Gast";
			return ok( login.render( user, loginForm ));
		}
	}
	
	public static Result signIn(){
		String user = session("name");
		if(user != null){
			return ok( account.render( user, Model.getInstance().getNutzer(user) ) );	
		}else{
			user = "Gast";
			return ok( signin.render( user, signInForm ) );
		}
	}
	
	public static Result signInSubmit(){
		Form<Nutzer> signInFormVoll = signInForm.bindFromRequest();
		
		String neuerNutzer = Model.getInstance().doSignIn(signInFormVoll.get());
		
		if( neuerNutzer != "Gast" ){
			System.out.println("Nutzer " + neuerNutzer  + " wurde Erfolgreich registriert :-) ");
			return ok( login.render( "Gast" , loginForm ) );
		}else{
			return redirect("/signIn");
		}
	}
	
	public static Result index(){
		session().clear();
		String user = session("name");
		if(user != null){
			user = "Gast";
			return ok( index.render( user ));
		}else{
			user = "Gast";
			return ok( index.render( user) );
		}
	}

	public static Result news(){
		String user = session("name");
		if(user != null){
			return ok( news.render( user ) );
		}else{
			user = "Gast";
			return ok( news.render( user ) );
		}
	}

	public static Result music(){
		String user = session("name");
		if(user != null){
			return ok( music.render(user, Model.getInstance().getInterpreten(), Model.getInstance().getLieder(), "" ) );	
		}else{
			user = "Gast";
			return ok( music.render(user, Model.getInstance().getInterpreten(), Model.getInstance().getLieder(), "" ) );
		}
	}
	public static Result titel(){
		String user = session("name");
		if(user != null){
			return ok( titel.render(user, Model.getInstance().getLieder(), "" ) );	
		}else{
			user = "Gast";
			return ok( titel.render(user, Model.getInstance().getLieder(), "" ) );
		}
	}

	public static Result account(){
		String user = session("name");
		if(user != null){
			return ok( account.render( user, Model.getInstance().getNutzer(user) ) );
		}else{
			return redirect( "/login" );
		}
	}
	
	public static Result about(){
		String user = session("name");
		if(user != null){
			return ok( about.render( user ) );
		}else{
			user = "Gast";
			return ok( about.render( user ) );
		}
	}
	
	public static Result contact(){
		String user = session("name");
		if(user != null){
			return ok( contact.render( user ) );
		}else{
			user = "Gast";
			return ok( contact.render( user ) );
		}
	}	
}