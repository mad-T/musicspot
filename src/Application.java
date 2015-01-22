

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

	private static Connection con = null;
	
	
	
	public static void createConnection() throws ClassNotFoundException, SQLException{
		if ( con == null ){
			
			Class.forName("org.sqlite.JDBC");
			String dbUrl = "jdbc:sqlite:app/db/database";
			con = DriverManager.getConnection(dbUrl);
			ResultSet rs = null;
			try{
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery("Select * from interpret");
				System.out.println("yooooo");
				while (rs.next()) {
					System.out.println("this");
					rs.getString(0);
				}
			}catch(SQLException e){
				System.out.println("Fehler beim Laden der Verbindung");
			}
			       
		}
		
	}
//	public static Result index(){
//		return ok(index.render());
//	}
//
//	public static Result news(){
//		Nutzer nutzer = new Nutzer();
//		String nickname = nutzer.nn;
//		return ok(news.render(nickname));
//	}
//	
//	public static Result music(){
//		
//		return ok(music.render( Model.sharedInstance.getM() ));
//	}
//
//	public static Result account(){
//		return ok(account.render());
//	}
//	
//	public static Result about(){
//		return ok(about.render());
//	}
//	
//	public static Result contact(){
//		return ok(contact.render());
//	}
	
	

	
}
