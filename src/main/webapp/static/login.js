$(()=>{
	const rootpath = '/seed';
	$('#submit').on('click',event=>{
		const name=$('#name').val();
		const password=$('#password').val();
		$.post('/seed/login;JSESSIONID='+howso.getSessionid(location.href),{
			name:name,
			password:password
		},null,'json').done(res=>{
			console.info(res);
		});
	});
	$('#multi-submit').on('click',event=>{
		const name=$('#p1').val();
		const file=$('#file')[0].files[0];
		var fd = new FormData();
	    fd.append("name", name);
	    fd.append("file", file);
        $.ajax({
	        url: '/seed/perm/login/import',
	        type: 'post',
			processData: false,
			contentType: false,
			data: fd,
			success: function(d) {
			    console.log(d);
	        }
	    });
	});
});