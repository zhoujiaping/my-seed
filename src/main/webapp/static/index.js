$(function() {
	let url = howso.addSid('users');
	var defer = $.get(url, {_permSpaceId:-1}, null, 'json').done(function(res) {
		//console.info(res);
	});
});