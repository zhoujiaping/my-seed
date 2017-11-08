var stompClient = null;
const host = window.location.host;
function connect() {
	var socket = new WebSocket(`ws://${host}/seed/ws;JSESSIONID=80425ac8-3673-4d6a-9100-51ee6fbbbb7c`);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('连接了: ' + frame);
		stompClient.subscribe('/topic/hi', function(greeting) {
			console.info(greeting);
		});
		stompClient.subscribe('/topic/hello', function(greeting) {
			console.info(greeting);
		});
		stompClient.subscribe('/queue/hello', function(greeting) {
			console.info(greeting);
		});
		stompClient.send("/app/hello", {}, JSON.stringify({
			'name' : 'zhou'
		}));
	});
}
function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("已断开连接");
}
connect();
