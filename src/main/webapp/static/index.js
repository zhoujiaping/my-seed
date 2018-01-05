$(function() {
	const cxt = howso.contextPath();
	let url = howso.addSid(cxt+'/users');
	var defer = $.get(url, {_permSpaceId:-1}, null, 'json').done(function(res) {
		//console.info(res);
	});
});