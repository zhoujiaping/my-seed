$(function() {
	let url = howso.addSid('users');
	$.get(url, {_permSpaceId:1}, null, 'json').done(function(res) {
		console.info(res);
	});
});