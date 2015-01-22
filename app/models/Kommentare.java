package models;

import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import java.util.*;

public class Kommentare {

	//alle websockets
	private static List<WebSocket.Out<String>> connections = new ArrayList<WebSocket.Out<String>>();
		
	public static void start(WebSocket.In<String> in, WebSocket.Out<String> out){
			
		connections.add(out);
		
		//nachricht erhalten, in connection liste speichern 
		in.onMessage(new Callback<String>(){
			public void invoke(String event){
				Kommentare.notifyAll(event);
				Model.getInstance().addKommentar(event, Model.getInstance().getNutzername(), Model.getInstance().getAktuellerSong());
			}			
		});
		
		in.onClose(new Callback0(){
		
			public void invoke(){
				Kommentare.notifyAll("Verbindung getrennt");
			}
			
		});
	}
	// liste auslesen und nachrichten schreiben
	public static void notifyAll(String message){
	
		for (WebSocket.Out<String> out : connections) {
			out.write(message);	
		}		
	}
}

