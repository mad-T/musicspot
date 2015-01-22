package testDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

	private static Connection con = null;
	
	public static Connection createConnection() throws SQLException {
		if(con == null){
			Connection tmp = null;
			try {
				Class.forName( "org.sqlite.JDBC" );
				tmp = DriverManager.getConnection("jdbc:sqlite:app/db/database");
			} catch (ClassNotFoundException e) {
				System.out.println("Fehler bei DB-Verbindung");
				e.printStackTrace();
			}
			con = tmp;
			
		}
		System.out.println("DB-Verbindung hergestellt: " + con.toString() );
		return con;
	}
	
	public static void showInterpreten() throws SQLException{
		Statement s = con.createStatement();
		
		ResultSet rs = s.executeQuery( "SELECT * FROM interpret;" );
	    String name = rs.getString("kname");
		System.out.println("Der name: " + name);
		
	}
	
	public static void showInterpret(String name) throws SQLException{
		String ss = "select * from interpret where kname = ? ;";
		PreparedStatement ps = con.prepareStatement(ss);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
	    {
	    	  System.out.println("Gesuchter Interpret: " + rs.getString(1) + " " + rs.getString(2) );
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
