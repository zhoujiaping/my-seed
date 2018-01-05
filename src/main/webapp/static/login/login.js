var cxt = howso.contextPath();
var app = new Vue({
	el:'#app',
	data:{
		styles:['my-style','default-style','none'],
		selectedStyle:'my-style',
		name:'',
		password:''
	},
	created:function(){
		/*setInterval(function(){
			app.bgIndex = (app.bgIndex+1)%5;
		},2000);*/
	},
	/*computed: {
	    style: function () {
	      return './'+this.selectedStyle+'/login.css';
	    }
	},*/
	methods:{
		login:function(){
			$.post(cxt+'/login',{
				name:this.name,
				password:this.password
			},null,'json').done(res=>{
				$.ajaxSetup({
					  beforeSend:(XHR,options)=>{
						  options.url = howso.addSessionId(options.url,res.sid);                    			
					  }
				  });
			  	$.ajaxSetup({
					error:(XHR)=>{
						if(XHR.responseJSON.code === '401.1'){//未登录
							location.href = cxt+'/static/login/login.html';
						}else if(XHR.responseJSON.code === '401.2'){//未授权
							console.info(XHR.responseJSON)
						}else{
							console.info(XHR.responseJSON)
						}
					}
				});
			  	location.href = howso.addSessionid(cxt + '/static/index.html',res.sid);
			});
		}
	}
});