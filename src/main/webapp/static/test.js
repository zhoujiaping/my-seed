$(function(){
	var cxtPath = howso.contextPath();
	$('#submit').on('click',evt=>{
		var method = $('#method').val();
		var origUrl = $('#url').text();
		var url = cxtPath+origUrl;
		var str = $('#params').text()||'{}';
		str = str.split(/\s/igm).join('');
		var data = howso.toAjaxParam(JSON.parse(str));
		http[method](url,data,null,'text').done(res=>{
			$('#res').text(res);
		});
	});
	$('#multi-submit').on('click',evt=>{
		var method = $('#method').val();
		var origUrl = $('#url').text();
		var url = cxtPath+origUrl;
		var str = $('#params').text()||'{}';
		str = str.split(/\s/igm).join('');
		var formData = new FormData();
		var files = $('#file')[0].files;
		for(let i=0;i<files.length;i++){
			formData.append('file', files[i]);
		}
		var data = howso.toAjaxParam(JSON.parse(str));
		for(let k in  data){
			formData.append(k,data[k]);
		}
		$.ajax({
		    url: url,
		    type: method,
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			$('#res').text(res);
		}).fail(function(res) {
			
		});
	});
});