$(document).ready(function(){
	
	/*------------------------------NEWS-------------------------------*/
	
	$("#buttonInternational").click(function(){
		var news = 		"<h3 id='headline'>BenniB veröffentlicht neues Album! </h3>"
						+	"<img id='newspic' src='/assets/images/bb.jpg' alt='Newsbild' height='75%' width='75%'> "
						+	"<br/>"
						+	"<p>Am 10.10.1010 kommt der neue Sound :D</p>"
						+	"<input type='button' id='next' value='next'>"
		$("#newstext").html($(news).fadeIn("slow"));	
	});
	$("#buttonNational").click(function(){
		var news = 		"<h3 id='headline'>Miwata geht auf Acoustictour! </h3>"
						+	"<img id='newspic' src='/assets/images/Miwata.jpg' alt='Newsbild'height='75%' width='75%'>"
						+	"<p></br>Nur mit der Gitarre im Arm tourt der Musiker durch die Gegend</p>"
						+	"<input type='button' id='next' value='next'>"
		$("#newstext").html($(news).fadeIn("slow"));	
	});

	/*------------------------------Account-------------------------------*/	
	
	
//	$("#ButtonUploads").click(function(){
//		var panelA = 		"<div id='imgprofil'>"
//						+	"<img src='/assets/images/profilpic.jpg' alt='Profilbild'>"
//						+	"</div>"
//						+	"<div id='profiltext'>"
//						+	"<table class='table'>"
//						+	"<tr><td>Nickname:</td><td>nutzer</td></tr>"
//						+	"<tr><td>Hobbies:</td><td><ul><li>HobbyNr1</li><li>HobbyNr2</li><li>HobbyNr3</li><li>HobbyNr4</li></ul></td></tr>"
//						+	"<tr><td>e-mail:</td>"
//						+	"<td>email</td></tr>"
//						+	"</table>"
//						+	"<a class='link' href='/edit'>Profil bearbeiten</a>"
//						+	"</div>"
//						
//		var panelB = 		"<h4>Uploadvorschlag?<br/> Immer her damit unsere Redaktion wird diesen bearbeiten und ggf. auf Musicspot hochladen</h4>"
//						+	"<form role='form'>"
//						+	"<div class='form-group'>"
//						+	"<label for='titel'>Songtitel:</label>"
//						+	"<input class='form-control' type='text' name='titel' placeholder='Hier den Titel des Liedes eingeben'"
//						+	"</div>"
//						+	"<div class='form-group'>"
//						+	"<label for='interpret'>Interpret:</label>"
//						+	"<input class='form-control' type='text' name='interpret' placeholder='Hier den Interpret des Liedes eingeben'"
//						+	"</div>"
//						+	"<div class='form-group'>"
//						+	"<label for='genre'>Genre:</label>"
//						+	"<select class='form-control' name='Genre'><option value='hiphop'>HipHop</option><option value='raggea'>Raggea</option><option value='house'>House</option></select>"
//						+	"</div>"
//						+	"<div class='form-group'>"
//						+	"<label for='link'>Link:</label>"
//						+	"<input class='form-control' type='text' name='link' placeholder='Hier den Link zum Lied eingeben'"
//						+	"</div>"
//						+   "<div class='actions'>"
//						+ 	"<br/><input type='submit' value='Vorschlag abschicken' class='btn btn-inverse fb pull-right'/>"
//						+	"</div>"
//						+	"</form>"
//		$("#accPanelA").html($(panelA).fadeIn("slow"));
//		$("#accPanelB").html($(panelB).fadeIn("slow"));	
//	});
	
	/*------------------------------FAQ-------------------------------*/
	
	$("#buttonFAQ").click(function(){
		$.ajax({
			url:"/assets/javascripts/daten.json",
			dataType:"json",
			success: function(json){
				var htmlcode = "<h3>Häufig gestellte Fragen:</h3></br><ul>";
				$.each(json.alle, function(){
					htmlcode += "<li><h4>" + this['Frage'] + "</h4>" + this['Antwort'] +"</li></br>";
				});
				htmlcode += "</ul>";
				$("#contactPanel").html(htmlcode);
			}
			
		});			
	}
);
	$("#indexButton").click(function(){
		$("#index").fadeOut("slow");
	});
	
	
});

function comment(){
	var comment = document.getElementById("comment").value;
	$("#showcomments").append(comment);
}

function proof(){
	
	var password = document.getElementById("password").value;
	var confirm = document.getElementById("confirm").value;
	
	if(password != confirm){
		alert("Passwort stimmt nicht über ein!");
	}
}

function suche(){
	var eingabe = document.getElementById("suche");
	var url ="/suche/?wert=" + escape(eingabe.value);
	var req;
	if(window.XMLHttpRequest){
		req = new XMLHttpRequest();
	} else if( window.ActiveXObject){
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	req.onreadystatechange = function(){
		if ( req.readyState == 4 ) {
			if ( req.status == 200 ) {
				var erg = req.responseText;
				document.getElementById("out").innerHTML = erg;
			}
		}
	};
	req.open("GET", url, true);
	req.send(null);
}