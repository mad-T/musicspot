package models;

public class Kommentar {

	public int kommID;
	public String text;
	public String verfasser;
	public int lied;
	
	public Kommentar(int kommID, String text, String verfasser, int lied){
		this.kommID = kommID;
		this.text = text;
		this.verfasser = verfasser;
		this.lied = lied;
	}
}
