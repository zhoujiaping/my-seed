$(function(){
	const cxtpath = howso.contextPath();
	$('#commit').on('click',(event)=>{
		//ajax表单方式上传
		/*var formData = new FormData();
		formData.append('file', $('#file1')[0].files[0]);
		$.ajax({
		    url: `${cxtpath}/files/upload-form`,
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			
		}).fail(function(res) {
			
		});*/
		//ajax流方式上传
		var file = $('#file2')[0].files[0];
		var reader = new FileReader();  
        reader.onload = function(event) {  
        	$.ajax({
    			url:`${cxtpath}/files/upload-stream?filename=fuck`,
    			type:'PUT',
    			cache:false,
    			data:event.target.result,
    			processData:false,
    			contentType:false
    		}).done(res=>{
    			
    		}).fail(res=>{
    			
    		});
        }  
        reader.readAsText(file);
		
	});
});