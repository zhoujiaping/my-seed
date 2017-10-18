$.ajaxSetup({
	/** 解决未登录情况下ajax无法重定向到登录页面的问题 */
	complete : function(XMLHttpRequest, textStatus) {
		let redirectUrl = XMLHttpRequest.getResponseHeader("X-Redirect-Url");
		if (redirectUrl) {
			window.top.location.href = '/seed'+redirectUrl;
		}
	}
});
const http = {
		'get':function(){
			return $.get.apply($,arguments);
		},
		'post':function(){
			return $.post.apply($,arguments);
		},
		'patch':function(){
			arguments[1]._method='patch';
			return $.post.apply($,arguments);
		},
		'put':function(){
			arguments[1]._method='put';
			return $.post.apply($,arguments);
		},
		'delete':function(){
			arguments[1]._method='delete';
			return $.post.apply($,arguments);
		}
};
if(typeof howso === "undefined"){
	var howso = {};
}
howso.sessionToken = 'JSESSIONID';
howso.hasContext = true;
/** 从url中提取sessionid */
howso.getSessionid = function(url){
	// "/fuck;JSESSIONID=12345;SESSIONID=6789?v=1"
			let index = url.indexOf('?');
			if(index>=0){
				url = url.substring(0,index);// /fuck;JSESSIONID=12345;SESSIONID=6789
			}
			index = url.indexOf(';');
			if(index<0){
				return null;
			}
			url = url.substring(index+1);// JSESSIONID=12345;SESSIONID=6789
			let token = howso.sessionToken+'=';
			index = url.lastIndexOf(token);
			if(index<0){
				console.info('can not find sessionid from '+url);
				return null;
			}
			url = url.substring(index+token.length);// 12345;SESSIONID=6789
			index = url.indexOf(';');
			if(index>=0){
				url = url.substring(0,index);// 12345
			}
			return url;
}
/** 添加/覆盖sessionid到url */
howso.addSessionid = function(url,sessionid){
	// sessionid:abcd
	// "/fuck;JSESSIONID=12345;SESSIONID=6789?v=1"
	let index = url.indexOf('?');
	let params = '';
	if(index>=0){
		params = url.substring(index);
		url = url.substring(0,index);// /fuck;JSESSIONID=12345;SESSIONID=6789
	}
	let token = howso.sessionToken+'=';
	index = url.lastIndexOf(token);
	if(index<0){
		return url+';'+token+sessionid+params;//
	}
	let before = url.substring(0,index);// /fuck;
	url = url.substring(index+token.length);// 12345;SESSIONID=6789
	index = url.indexOf(';');
	let after = url.substring(index);// ;SESSIONID=6789
	return before+';'+token+sessionid+after+params;
}
/** 从top.location.href中获取sessionid，设置到url */
howso.addSid = function(url){
	let sid = howso.getSessionid(top.location.href);
	return howso.addSessionid(url,sid);
}
/**
 * 将js对象的key=value方式转为http请求参数的key=value方式。 js中的value支持对象，数组，基本类型（数值、字符串，布尔），
 * 但是http请求参数支持数值，字符串，布尔。 在和后端springmvc参数注入配合时，会出现问题。
 * 所以需要一个转换的方法。避免后端手动调用json库进行解析。 放在前端进行更方便，因为后端需要手动指定类型，前端是动态类型，封装一个方法就够了。
 */
howso.toAjaxParam = (function(){
	function serialize(key, value, param) {
		if(Array.isArray(value)) {
			for(let i = 0; i < value.length; i++) {
				serialize(key + '[' + i + ']', value[i], param)
			}
		} else if(value != undefined && value.constructor === Object) {
			for(let k in value) {
				serialize(key + '.' + k, value[k], param);
			}
		} else {
			param[key] = value;
		}
	}
	function serializeObj(obj) {
		const param = {};
		for(let k in obj) {
			serialize(k, obj[k], param);
		}
		return param;
	}
	return serializeObj;
})();
/** 日期格式化为字符串 */
howso.formatDate = function(date,fmt){
	var o = {
		"M+" : date.getMonth() + 1, // 月份
		"d+" : date.getDate(), // 日
		"H+" : date.getHours(), // 小时
		"m+" : date.getMinutes(), // 分
		"s+" : date.getSeconds(), // 秒
		"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
		"S+" : date.getMilliseconds()
	// 毫秒
	};
	var rst = /(y+)/.exec(fmt);
	rst
			&& (fmt = fmt.replace(rst[1], (date.getFullYear() + '')
					.substr(4 - rst[1].length)));
	for ( var k in o) {
		rst = new RegExp("(" + k + ")").exec(fmt);
		rst
				&& (fmt = fmt.replace(rst[1], rst[1].length == 1 ? o[k]
						: ('000'.substr(0, rst[1].length
								- ('' + o[k]).length) + o[k])));
	}
	return fmt;
};
/**
 * 要求：如果指定毫秒ms，则必须指定全部字段； 如果指定second字段，则必须指定从year到second的所有字段；
 * 以此类推。即，如果指定细粒度的时间，则必须指定所有比它更粗粒度的时间。 例如：不能只指定时分秒而不指定日月年。
 * 否则会出现非预期结果。这是为了程序的一致性故意设计的，使结果与当前日期没有任何关系。
 */
howso.parseDate = function(str,fmt){
	var reg = {
			year : /(y+)/,
			month:/(M+)/,
			day:/(d+)/,
			hour:/(H+)/,
			minute:/(m+)/,
			second:/(s+)/, 
			ms:/(S+)/ 
		};
		var date = new Date();

		var rst = reg.year.exec(fmt);
		var year = rst?+str.substr(rst.index,rst[1].length):null;
		date.setFullYear(year);
		
		rst = reg.month.exec(fmt);
		var month = rst?+str.substr(rst.index,rst[1].length)-1:0;	
		rst = reg.day.exec(fmt);
		var day = rst?+str.substr(rst.index,rst[1].length):1;
		date.setMonth(month,day);
		
		rst = reg.hour.exec(fmt);
		var hour = rst?+str.substr(rst.index,rst[1].length):0;
		date.setHours(hour);

		rst = reg.minute.exec(fmt);
		var minute = rst?+str.substr(rst.index,rst[1].length):0;
		date.setMinutes(minute);

		rst = reg.second.exec(fmt);
		var second = rst?+str.substr(rst.index,rst[1].length):0;
		date.setSeconds(second);

		rst = reg.ms.exec(fmt);
		var ms = rst?+str.substr(rst.index,rst[1].length):0;
		date.setMilliseconds(ms);
		
		return date;
};
/** 日期加上天数，返回新的日期 */
howso.addDate = function(date,daynum){
	var ms = date.getTime() + daynum * 24 * 60 * 60 * 1000;
	var newdate = new Date();
	newdate.setTime(ms);
	return newdate;
};

howso.addMinute = function(date,minutenum){
	var ms = date.getTime() + minutenum * 60 * 1000;
	var newdate = new Date();
	newdate.setTime(ms);
	return newdate;
};
/**格式化字符串*/
howso.formatString = function(str,params){
	var reg = null;
	$.each(params,function(index,ele){
		reg = new RegExp("\\{\\{" + index + "\\}\\}", "igm");
		str = str.replace(reg,ele);
	});
	return str;
};
/**返回项目上下文路径，比如‘/seed’，最后不带‘/’*/
howso.contextPath = function() {
	if(howso.hasContext){
		var pathname = window.document.location.pathname;
		return pathname.substr(0,pathname.substr(1).indexOf('/') + 1);
	}else{
		return '';
	}
};
