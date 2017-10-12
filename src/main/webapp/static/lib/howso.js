const howso = {
		sessionToken : 'JSESSIONID'
};
/**从url中提取sessionid*/
howso.getSessionid = function(url){
	//"/fuck;JSESSIONID=12345;SESSIONID=6789?v=1"
			let index = url.indexOf('?');
			if(index>=0){
				url = url.substring(0,index);///fuck;JSESSIONID=12345;SESSIONID=6789
			}
			index = url.indexOf(';');
			if(index<0){
				return null;
			}
			url = url.substring(index+1);//JSESSIONID=12345;SESSIONID=6789
			let token = howso.sessionToken+'=';
			index = url.lastIndexOf(token);
			if(index<0){
				console.info('can not find sessionid from '+url);
				return null;
			}
			url = url.substring(index+token.length);//12345;SESSIONID=6789
			index = url.indexOf(';');
			if(index>=0){
				url = url.substring(0,index);//12345
			}
			return url;
}
/**添加/覆盖sessionid到url*/
howso.addSessionid = function(url,sessionid){
	//sessionid:abcd
	//"/fuck;JSESSIONID=12345;SESSIONID=6789?v=1"
	let index = url.indexOf('?');
	let params = '';
	if(index>=0){
		params = url.substring(index);
		url = url.substring(0,index);///fuck;JSESSIONID=12345;SESSIONID=6789
	}
	let token = howso.sessionToken+'=';
	index = url.lastIndexOf(token);
	if(index<0){
		return url+';'+token+sessionid+params;//
	}
	let before = url.substring(0,index);///fuck;
	url = url.substring(index+token.length);//12345;SESSIONID=6789
	index = url.indexOf(';');
	let after = url.substring(index);//;SESSIONID=6789
	return before+';'+token+sessionid+after+params;
}
/**从top.location.href中获取sessionid，设置到url*/
howso.addSid = function(url){
	let sid = howso.getSessionid(top.location.href);
	return howso.addSessionid(url,sid);
}