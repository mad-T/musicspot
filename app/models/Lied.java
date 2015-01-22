package models;

public class Lied {

	public int trackID;
	public String titel;
	public String dauer;
	public String genre;
	public String interpret;
	public String pfad;
	public String video;
	
	public Lied(){
		
	}
			
	public Lied(int trackID, String titel, String dauer, String genre, String interpret, String pfad, String video) {
		this.trackID = trackID;
		this.titel = titel;
		this.dauer = dauer;
		this.genre = genre;
		this.interpret = interpret;
		this.pfad = pfad;
		this.video = video;
	}
	
}
