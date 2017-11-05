var websocket = window.WebSocket || window.MozWebSocket;
var isConnected = false;

function doOpen() {
	isConnected = true;
	console.info('doOpen');
}

function doClose() {
	console.info('doClose');
	isConnected = false;
}

function doError() {
	console.info('doError');
	isConnected = false;

}

function doMessage(message) {
	var event = $.parseJSON(message.data);
	console.info(event);
}

function doSend(message) {
	if (websocket != null) {
		websocket.send(JSON.stringify(message));
	} else {
		console.info('doSend');
	}
}

// 初始话 WebSocket
function initWebSocket(wcUrl) {
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI(wcUrl));
		websocket.onopen = doOpen;
		websocket.onerror = doError;
		websocket.onclose = doClose;
		websocket.onmessage = doMessage;
	} else {
		console.info('no websocket');

	}
};

initWebSocket('ws://localhost:8080/seed/ws/hello');