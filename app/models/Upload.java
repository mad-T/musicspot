package models;

public class Upload {
	
	public int uploadID;
	public String titel;
	public String interpret;
	public String link;
	public String nutzer;
	public String status;
	
	public Upload() {
	}
	
	public Upload(int uploadID, String titel, String interpret, String link, String nutzer, String status) {
		this.uploadID = uploadID;
		this.titel = titel;
		this.interpret = interpret;
		this.link = link;
		this.nutzer = nutzer;
		this.status = status;
	}
	
	
	
}
