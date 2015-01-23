package models;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import play.*;
import play.db.*;

public class Model {

	private static Model instance = null;
	private Nutzer u = new Nutzer();
	private int aktuellerSong;
	
	private Connection con = null;
	
	public void addKontakt(String nutzer, String email, String nachricht, String art, String status){
		PreparedStatement ps = null;
		System.out.println("nutzer: " + nutzer + " email: " + email + " nachricht: " + nachricht );
		if(nutzer != null && nutzer != "Gast" && nachricht != null){	
			try{
				con = DB.getConnection();
				String s = "insert into kontakt(kontaktID, nutzer, email, nachricht, art, status) values ((select max(kontaktID)+1 from kontakt), ?, ?, ?, ?, ?);";
				ps = con.prepareStatement(s);
				ps.setString(1, nutzer);
				ps.setString(2, email);
				ps.setString(3, nachricht);
				ps.setString(4, art);
				ps.setString(5, status);
				ps.executeUpdate();
				System.out.println("Model Kontakt: "+nachricht );
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim Einfügen des Kontaktformular von "+nutzer+" ");
			}finally{
				try{
					if(ps != null){
						ps.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void addUpload(String titel, String interpret, String link, String nutzer, String status){
		PreparedStatement ps = null;
		System.out.println("nutzer: " + nutzer + " titel: " + titel + " link: " + link );
		if(nutzer != null && nutzer != "Gast" && link != null){	
			try{
				con = DB.getConnection();
				String s = "insert into upload(uploadID, titel, interpret, link, nutzer, status) values ((select max(uploadID)+1 from upload), ?, ?, ?, ?, ?);";
				ps = con.prepareStatement(s);
				ps.setString(1, titel);
				ps.setString(2, interpret);
				ps.setString(3, link);
				ps.setString(4, nutzer);
				ps.setString(5, status);
				ps.executeUpdate();
				System.out.println("Model Upload: "+link );
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim Einfügen des Uploadvorschlags von "+nutzer+" ");
			}finally{
				try{
					if(ps != null){
						ps.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void addKommentar(String text, String verfasser, int lied){
		PreparedStatement ps = null;
		System.out.println("Kommi: " + text + " Verfasser: " + verfasser + " Lied: " + lied );
		if(text != null && verfasser != null && verfasser != "Gast" && lied != 0){	
			try{
				con = DB.getConnection();
				String s = "insert into kommentar(kommID, text, verfasser, lied ) values ( (select max(kommID)+1 from kommentar), ?, ?, ?);";
				ps = con.prepareStatement(s);
				ps.setString(1, text);
				ps.setString(2, verfasser);
				ps.setInt(3, lied);
				ps.executeUpdate();
				System.out.println("Model Kommentar: "+text );
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim Einfügen des Kommentars von "+verfasser+" ");
			}finally{
				try{
					if(ps != null){
						ps.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
				
			}
		}
	}
	
	public void deleteKontakt(int kid){
		PreparedStatement stmt = null;
		System.out.println("KONTAKTNUMMER model : " +kid);
			try{
					con = DB.getConnection();
					String ss = "delete from kontakt where kontaktID = ?;" ;
					stmt = con.prepareStatement(ss);
					stmt.setInt(1, kid);
					stmt.executeUpdate();
					
					System.out.println("gelöscht KontaktNr: " + kid + " ");
				
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim löschen von " + kid);
			}finally{
				try{
					if(stmt != null){
						stmt.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
	}
	public void deleteUpload(int uid){
		PreparedStatement stmt = null;
		System.out.println("UPLOADNUMMER model : " +uid);
			try{
					con = DB.getConnection();
					String ss = "delete from upload where uploadID = ?;" ;
					stmt = con.prepareStatement(ss);
					stmt.setInt(1, uid);
					stmt.executeUpdate();
					
					System.out.println("gelöscht UploadNr: " + uid);
				
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim löschen von " + uid);
			}finally{
				try{
					if(stmt != null){
						stmt.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
	}
	
	public void editNutzer(Nutzer nu){
		PreparedStatement stmt = null;
		if(nu.getNn() != "Gast"){
			try{
					con = DB.getConnection();
					String ss = "update nutzer set pw = ?, name = ?, hobby = ?, bild = ? where nn = ?;" ;
					stmt = con.prepareStatement(ss);
					stmt.setString(1, securePw(nu.getPw()));
					stmt.setString(2, nu.name);
					stmt.setString(3, nu.hobby);
					stmt.setString(4, nu.bild);
					stmt.setString(5, nu.getNn());
					stmt.executeUpdate();
					
					System.out.println("Nutzerdaten von " + nu.getNn() + " geändert: " +stmt.toString());
				
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim Ändern der Nutzerdaten von " + nu.getNn());
			}finally{
				try{
					if(stmt != null){
						stmt.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	public String sucheErgänzen(String gesucht){
		System.out.println("Model " + gesucht);
		ArrayList<String> namen = new ArrayList<String>();
		String erg = "";
		for(int i = 0; i < this.getLieder().size(); i++){
			namen.add(i, this.getLieder().get(i).titel);
		}
		for(String iter: namen){
			String tmp = iter;
			if( tmp.toUpperCase().startsWith(gesucht.toUpperCase()) ){
				erg = tmp;
			}
		}
		System.out.println("Model Ergebnis= " +erg);
		return erg;
	}
	
	public String getNutzername(){
		return this.u.getNn();
	}
	public Nutzer getNutzer(){
		return this.u;
	}
	public void setNutzer(Nutzer nu){
		this.u = nu;
	}
	
	public void setAktuellerSong(int tid){
		this.aktuellerSong = tid;
	}
	public int getAktuellerSong(){
		return this.aktuellerSong;
	}
	
	public String checkLogin(Nutzer nu){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println("Model Login: " +nu.getNn() + " | ");
		try{
			con = DB.getConnection();
			String ss = "select * from nutzer where nn = ? and pw = ?;" ;
			stmt = con.prepareStatement(ss);
			stmt.setString(1, nu.getNn());
			stmt.setString(2, securePw(nu.getPw()));
			rs = stmt.executeQuery();
				if(rs.next())
			    {
			    	Nutzer tmp = new Nutzer(rs.getString("nn"));
			    		this.setNutzer(tmp);
			    		System.out.println("Login erfolgreich");
			    } else{
			    	System.out.println("Login fehlgeschlagen");
			    	this.setNutzer(new Nutzer());
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Login");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		System.out.println("Model Login von: " + this.getNutzername() + " |");
		return this.getNutzername();
	}
	
	public void doLogout(){
		this.setNutzer(new Nutzer());
		if(this.getNutzername() == "Gast"){
			System.out.println("Logout erfolgreich");
		}
	}

	public String doSignIn(Nutzer nu){
		PreparedStatement ps = null;
		if(nu.getNn() != null && nu.getNn() != "Gast"){	
			try{
				con = DB.getConnection();
				
				String s = "insert into nutzer(nn, pw, name, hobby, bild) values (?, ?, ?, ?, ?);";
				
				ps = con.prepareStatement(s);
				ps.setString(1, nu.getNn());
				ps.setString(2, securePw(nu.getPw()));
				ps.setString(3, nu.name);
				ps.setString(4, nu.hobby);
				ps.setString(5, nu.bild);
				
				System.out.println("insert into nutzer(nn, pw) values ('" + nu.getNn() + "', '" + securePw(nu.getPw()) + "');");
				ps.executeUpdate();
				this.setNutzer(nu);
				System.out.println("Model signIn: " + this.getNutzername() );
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Fehler beim Erstellen des Nutzers: " + nu.getNn() + this.getNutzername());
			}finally{
				try{
					if(ps != null){
						ps.close();
					}
					if(con != null){
						con.close();
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}else{
			this.setNutzer(new Nutzer());
		}
		return this.getNutzername();
	}
	
	public static String securePw(String p){
		String genPw = "";
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-1");
	        md.update(p.getBytes());
	        byte[] bytes = md.digest(p.getBytes());
	        String cpw = "";
	        for(int i=0; i< bytes.length ;i++){
	            cpw += (bytes[i]);
	        }
	        genPw = cpw;
	    }
	    catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	    }
	    return genPw;
	}
	
	public Interpret showInterpretName(String name) {
		Interpret i = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = DB.getConnection();
			String ss = "select * from interpret where kname = ? ;";
			ps = con.prepareStatement(ss);
			ps.setString(1, name);
			rs = ps.executeQuery();
				while (rs.next())
			    {
			    	 i = new Interpret(rs.getString(1), rs.getString(2), rs.getString(3)); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Interpret nach Name Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return i;
	}
	
	public ArrayList<Upload> getUploads(){
		Statement s = null;
		ResultSet rs = null;
		ArrayList<Upload> uploads = new ArrayList<Upload>();
		try{
			con = DB.getConnection();
			s = con.createStatement();
			rs = s.executeQuery("select * from upload;");
				while (rs.next())
			    {
					uploads.add( new Upload( rs.getInt("uploadID"), rs.getString("titel"), rs.getString("interpret"), rs.getString("link"), rs.getString("nutzer"), rs.getString("status") ) ); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Upload Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(s != null){
					s.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		for(Upload iter : uploads){
			System.out.println("Upload von: " + iter.nutzer);
			System.out.println("Link: " + iter.link);
			System.out.println("Status: " + iter.status);
		}
		return uploads;
	}
	public ArrayList<Kontakt> getKontakte(){
		Statement s = null;
		ResultSet rs = null;
		ArrayList<Kontakt> kontakte = new ArrayList<Kontakt>();
		try{
			con = DB.getConnection();
			s = con.createStatement();
			rs = s.executeQuery("select * from Kontakt;");
				while (rs.next())
			    {
					kontakte.add( new Kontakt( rs.getInt("kontaktID"), rs.getString("nutzer"), rs.getString("email"), rs.getString("nachricht"), rs.getString("art"), rs.getString("status") ) ); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Kontakte Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(s != null){
					s.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		for(Kontakt iter : kontakte){
			System.out.println("Name: " + iter.nutzer);
			System.out.println("Nachricht: " + iter.nachricht);
			System.out.println("Status: " + iter.status);
		}
		return kontakte;
	}
	
	public ArrayList<Interpret> getInterpreten(){
		Statement s = null;
		ResultSet rs = null;
		ArrayList<Interpret> interpreten = new ArrayList<Interpret>();
		try{
			con = DB.getConnection();
			s = con.createStatement();
			rs = s.executeQuery("select * from interpret;");
				while (rs.next())
			    {
			    	interpreten.add( new Interpret(rs.getString("kname"), rs.getString("beschreibung"), rs.getString("bild")) ); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Interpret Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(s != null){
					s.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		for(Interpret iter : interpreten){
			System.out.println("Name: " + iter.kName);
			System.out.println("Info: " + iter.beschreibung);
			System.out.println("Bild: " + iter.bild);
		}
		return interpreten;
	}
	public ArrayList<Lied> getLieder(){
		Statement s = null;
		ResultSet rs = null;
		ArrayList<Lied> lieder = new ArrayList<Lied>();
		try{
			con = DB.getConnection();
			s = con.createStatement();
			rs = s.executeQuery("select * from lied order by titel asc;");
				while (rs.next())
			    {
			    	lieder.add( new Lied(rs.getInt("trackID"), rs.getString("titel"), rs.getString("dauer"), rs.getString("genre"), rs.getString("interpret"), rs.getString("pfad"), rs.getString("video")) ); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Lied Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(s != null){
					s.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}	
		for(Lied iter : lieder){
			System.out.println("TrackID: " + iter.trackID);
			System.out.println("Titel: " + iter.titel);
			System.out.println("Dauer: " + iter.dauer);
			System.out.println("Genre: " + iter.genre);
			System.out.println("Interpret: " + iter.interpret);
			System.out.println("Pfad: " + iter.pfad);
			System.out.println("Video: " + iter.video);
		}
		return lieder;
	}
	public ArrayList<Kommentar> getKommentare(int lid){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Kommentar> kommentare = new ArrayList<Kommentar>();
		try{
			con = DB.getConnection();
			String s = "select * from kommentar where lied = ?;";
			ps = con.prepareStatement(s);
			ps.setInt(1, lid);
			rs = ps.executeQuery();
				while (rs.next())
			    {
			    	kommentare.add( new Kommentar(rs.getInt("kommID"), rs.getString("text"), rs.getString("verfasser"), rs.getInt("lied")) ); 
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Kommentar Select");
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		for(Kommentar iter : kommentare){
			System.out.println("Kommentare der TrackID: " + iter.kommID);
			System.out.println("Kommentar: " + iter.text);
			System.out.println("Verfasser: " + iter.verfasser);
			System.out.println("Lied: " + iter.lied);
		}
		return kommentare;
	}
	
	public Nutzer getNutzer(String nns){
		Nutzer nutzer = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = DB.getConnection();
			String s = "select * from nutzer where nn = ?;";
			ps = con.prepareStatement(s);
			ps.setString(1, nns);
			rs = ps.executeQuery();
				while (rs.next())
			    {
			    	nutzer = new Nutzer( rs.getString("nn"), rs.getString("name"), rs.getString("hobby"), rs.getString("bild") );
			    	System.out.println("Nutzer erstellt :" + nutzer.getNn() + "  " + nutzer.name);
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Select von Nutzer " + nns);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return nutzer;
	}
	
	public Lied getLied(String titel){
		Lied lied = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = DB.getConnection();
			String s = "select * from lied where titel = ?;";
			ps = con.prepareStatement(s);
			ps.setString(1, titel);
			rs = ps.executeQuery();
				while (rs.next())
			    {
			    	lied = new Lied(rs.getInt("trackID"), rs.getString("titel"), rs.getString("dauer"), rs.getString("genre"), rs.getString("interpret"), rs.getString("pfad"), rs.getString("video")); 
			    	this.setAktuellerSong(lied.trackID);
			    }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Select von Lied " + titel);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return lied;
	}	
	
	public void deleteTable(String tname) {
		Statement s = null;
		try{
			con = DB.getConnection();
			s = con.createStatement();
			String deleteString = "drop table "+tname+";"; 
			s.executeUpdate(deleteString);
			System.out.println("Tabelle "+tname+" gelöscht");
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim Löschen der "+tname+"-Tabelle");
		}finally{
			try{
				if(s != null){
					s.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static Model getInstance(){
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	private Model(){
//		deleteDB();
		initDB();
	}
	
	public void deleteDB(){
		deleteTable("kommentar");
		deleteTable("lied");
		deleteTable("interpret");
		deleteTable("nutzer");
		deleteTable("kontakt");
		deleteTable("upload");
	}
	
	public void initDB() {
		Statement s = null;
		ResultSet rs = null;
		try{
			con = DB.getConnection();
			
			DatabaseMetaData meta = con.getMetaData();
			
			s = con.createStatement();
			
			rs = meta.getTables(null, null, "interpret", null);
			if(!rs.next()){
				String createString = "create table interpret" +
		                "(kname string primary key," +
		                " beschreibung string, " + 
		                " bild string " +
		                ");" + 
		                "insert into interpret (kname, beschreibung, bild) values ('Miwata', 'Best German Reggae', '/assets/images/miwata2.jpg');"+
						"insert into interpret (kname, beschreibung, bild) values ('BenniB', 'Feiner Hip-Hop', '/assets/images/benniB.jpg');"+
						"insert into interpret (kname, beschreibung, bild) values ('ThomasJack', 'edles Deephouse', '/assets/images/tjack.jpg');"+
						"insert into interpret (kname, beschreibung, bild) values ('Nhyx', 'abgehender Deep und Tech', '/assets/images/nhyx.jpg');";
				s.executeUpdate(createString);
				System.out.println("Interpretdaten erstellt");
			}
			rs.close();
			
			rs = meta.getTables(null, null, "lied", null);
			if(!rs.next()){
				String createString = "create table lied" +
		                "(trackID int primary key," +
		                " titel string," + 
		                " dauer string," +
		                " genre string," +
		                " interpret string," +
		                " pfad string," +
		                " video string," +
		                " foreign key(interpret) references interpret(kname)" +
		                ");" + 
		                "insert into lied(trackID, titel, dauer, genre, interpret, pfad, video) values (1,'Auf dem Weg Richtung Sonne', '02:35', 'Reggae', 'Miwata', '/assets/images/Miwata.mp3', '//www.youtube.com/embed/dClOxsBQaB8');" +
						"insert into lied(trackID, titel, dauer, genre, interpret, pfad, video) values (2,'I Wont Crash', '03:56', 'Hip-Hop',  'BenniB', '/assets/images/BenniB.mp3', '//www.youtube.com/embed/164o74cYlB4');" + 
						"insert into lied(trackID, titel, dauer, genre, interpret, pfad, video) values (3,'Gold', '03:56', 'DeepHouse',  'ThomasJack', '/assets/images/T_Jack.mp3', '//www.youtube.com/embed/uuL3Yg_P0Pc');" +
						"insert into lied(trackID, titel, dauer, genre, interpret, pfad, video) values (4,'Khronos', '04:43', 'DeepHouse',  'Nhyx', '/assets/images/nhyx.mp3', '//www.youtube.com/embed/lT_Bo9NkHLo');"; 
				s.executeUpdate(createString);
				System.out.println("Lieddaten erstellt");
			}
			rs.close();	
			
			rs = meta.getTables(null, null, "nutzer", null);
			if(!rs.next()){
				String createString = "create table nutzer" +
		                "(nn string primary key," +
		                " pw string, " +
		                " name string, " +
		                " hobby string, " +
		                " bild string " +
		                ");" + 
		                "insert into nutzer(nn, pw, name, hobby, bild) values ('Admin', '" + securePw("MusicSpotHtwg15") + "', 'Admin', 'Admin', 'Admin' );" +
		                "insert into nutzer(nn, pw, name, hobby, bild) values ('DerGenosse', '" + securePw("123") + "', 'Alexander', 'Pumpen', '/assets/images/genosse.jpg' );" +
						"insert into nutzer(nn, pw, name, hobby, bild) values ('Linserich', '" + securePw("234") + "', 'Matthias', 'Gammeln', '/assets/images/nuesse.jpg' );"; 
				s.executeUpdate(createString);
				System.out.println("Nutzerdaten erstellt");
			}
			rs.close();
			
			rs = meta.getTables(null, null, "kommentar", null);
			if(!rs.next()){
				String createString = "create table kommentar" +
		                "(kommID int primary key," +
		                " text string," + 
		                " verfasser string," +
		                " lied int," +
		                " foreign key (verfasser) references nutzer(nn)," +
		                " foreign key (lied) references Lied(trackID)" +
		                ");" + 
		                "insert into kommentar(kommID, text, verfasser, lied) values (1, 'Super Song =)', 'DerGenosse', 1);" +
						"insert into kommentar(kommID, text, verfasser, lied) values (2, 'Big Up !', 'Linserich', 2);"; 
				s.executeUpdate(createString);
				System.out.println("Kommentardaten erstellt");
			}
			rs.close();
			
			rs = meta.getTables(null, null, "kontakt", null);
			if(!rs.next()){
				String createString = "create table kontakt" +
		                "(kontaktID int primary key," +
		                " nutzer string," + 
		                " email string," +
		                " nachricht int," +
		                " art string," +
		                " status string" +
		                ");" +
		                "insert into kontakt(kontaktID, nutzer, email, nachricht, art, status) values (1, 'default', 'default', 'default', 'default', 'default');";
		                
				s.executeUpdate(createString);
				System.out.println("Kontakttabelle erstellt");
			}
			rs.close();
			
			rs = meta.getTables(null, null, "upload", null);
			if(!rs.next()){
				String createString = "create table upload" +
		                "(uploadID int primary key," +
		                " titel string," + 
		                " interpret string," +
		                " link int," +
		                " nutzer string," +
		                " status string" +
		                ");" +
		                "insert into upload(uploadID, titel, interpret, link, nutzer, status) values (1, 'default', 'default', 'default', 'default', 'default');";	
				s.executeUpdate(createString);
				System.out.println("Uploadtabelle erstellt");
			}
			rs.close();
			
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Fehler beim erstellen der DB + Dummy-Daten");
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
//	SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;
}
