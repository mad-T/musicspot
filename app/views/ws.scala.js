var s;
	$(function(){
		var WS = window['MozWebSocket'] ? window['MozWebSocket'] : WebSocket;
		s = new WS('@routes.Application.kommentar().webSocketURL(request)');
		
		s.onmessage = writeMessages;
		
	})
		
	function writeMessages(event){
		$('#socket-messages').prepend('<p>'+event.data+'</p>');
	};
	
	function sendMessage(){
		var kommi = $('#socket-input').val();
			s.send(kommi);
	};