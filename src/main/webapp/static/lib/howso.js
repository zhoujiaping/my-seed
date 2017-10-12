const howso = {
		getSessionid:function(url){
			let index = url.indexOf('?');
			if(index>0){
				url = url.substring(0,index);
			}
			let key = 'JSESSIONID';
			let str = ';'+key+'=';
			let tokenIndex = url.indexOf(str);
			if(tokenIndex<0){
				console.info('can not find sessionid from '+url);
				return null;
			}
			let sessionid = url.substring(tokenIndex+str.length);
			return sessionid;
		}
};