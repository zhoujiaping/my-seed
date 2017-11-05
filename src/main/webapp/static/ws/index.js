"use strict";
var Chat = {};
Chat.socket = null;
Chat.connect = (function(host) {
	if ('WebSocket' in window) {
		Chat.socket = new WebSocket(host);
	} else if ('MozWebSocket' in window) {
		Chat.socket = new MozWebSocket(host);
	} else {
		Console.log('Error: WebSocket is not supported by this browser.');
		return;
	}
	Chat.socket.onopen = function() {
		Console.log('Info: WebSocket connection opened.');
		document.getElementById('chat').onkeydown = function(event) {
			if (event.keyCode == 13) {
				Chat.sendMessage();
			}
		};
	};
	Chat.socket.onclose = function() {
		document.getElementById('chat').onkeydown = null;
		Console.log('Info: WebSocket closed.');
	};
	Chat.socket.onmessage = function(message) {
		Console.log(message.data);
	};
});
Chat.initialize = function() {
	if (window.location.protocol == 'http:') {
		Chat
				.connect('ws://' + window.location.host
						+ '/seed/ws/test');
	} else {
		Chat.connect('wss://' + window.location.host
				+ '/seed/ws/test');
	}
};
Chat.sendMessage = (function() {
	var message = document.getElementById('chat').value;
	if (message != '') {
		Chat.socket.send(message);
		document.getElementById('chat').value = '';
	}
});
var Console = {};
Console.log = (function(message) {
	var console = document.getElementById('console');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.innerHTML = message;
	console.appendChild(p);
	while (console.childNodes.length > 25) {
		console.removeChild(console.firstChild);
	}
	console.scrollTop = console.scrollHeight;
});
Chat.initialize();