package models;

public class Kontakt {

	public int kontaktID;
	public String nutzer;
	public String email;
	public String nachricht;
	public String art;
	public String status;
	
	public Kontakt() {
	}
	
	public Kontakt(int kontaktID, String nutzer, String email, String nachricht, String art, String status) {
		this.kontaktID = kontaktID;
		this.nutzer = nutzer;
		this.email = email;
		this.nachricht = nachricht;
		this.art = art;
		this.status = status;
	}
}
