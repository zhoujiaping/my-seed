$(function() {
	let url = howso.addSid('users');
	$.get(url, {}, null, 'json').done(function(res) {
		console.info(res);
	});
});