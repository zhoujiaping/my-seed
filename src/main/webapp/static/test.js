$(function(){
	var cxtPath = howso.contextPath();
	$('#submit').on('click',evt=>{
		
		var method = $('#method').val();
		var origUrl = $('#url').text();
		var url = howso.addSessionid(cxtPath+origUrl,$('#sid').text());
		var str = $('#params').text()||'{}';
		//str = str.split(/\r\n/igm).join('');
		var data = howso.toAjaxParam(JSON.parse(str));
		http[method](url,data,null,'text').done(res=>{
			$('#res').text(res);
		});
	});
});