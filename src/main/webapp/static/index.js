$.ajaxSetup({
	/** 解决未登录情况下ajax无法重定向到登录页面的问题 */
	complete : function(XMLHttpRequest, textStatus) {
		let redirectUrl = XMLHttpRequest.getResponseHeader("X-Redirect-Url");
		if (redirectUrl) {
			window.top.location.href = '/seed'+redirectUrl;
		}
	}
});
$(function() {
	$.get('users;JSESSIONID='+howso.getSessionid(), {}, null, 'json').done(function(res) {
		console.info(res);
	});
});