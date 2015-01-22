package models;

public class Nutzer {

	private String nn;
	private String pw;
	private boolean admin;
	
	public String name;
	public String hobby;
	public String bild;
	
	public Nutzer(String nn, String name, String hobby, String bild) {
		this.setNn(nn);
		this.name = name;
		this.hobby = hobby;
		this.bild = bild;
		this.admin = false;
	}
	
	public Nutzer(String nn, String pw, String name, String hobby, String bild) {
		this.setNn(nn);
		this.setPw(pw);
		this.name = name;
		this.hobby = hobby;
		this.bild = bild;
		this.admin = false;
	}
	
	public Nutzer(String nn){
		this.nn = nn;
		this.admin = false;
	}
			
	public Nutzer(String nn, String pw) {
		this.nn = nn;
		this.pw = pw;
		this.admin = false;
	}
	
	public Nutzer(){
		this.nn = "Gast";
		this.pw = "pw";
		this.admin = false;
	}
	public String getNn(){
		return this.nn;
	}
	public void setNn(String nn){
		this.nn = nn;
	}
	public String getPw(){
		return this.pw;
	}
	public void setPw(String pw){
		this.pw = pw;
	}
}
