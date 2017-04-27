/*中缀表达式转前缀表达式，适用于布尔表达式，可带园括号。稍微修改可以支持四则运算表达式*/
Array.prototype.peek = function(){
	if(this.length==0)
		return null;
	return this[this.length-1];
};
//运算符及其优先级
const opers = {
	'(':{
		pri:0
	},
	')':{
		pri:900
	},
	'&&':{
		eleNum:2,//双目运算符
		pri:200
	},
	'||':{
		eleNum:2,
		pri:100
	},
	'!':{
		eleNum:1,
		pri:300
	}
};
//是否为操作符
function isOp(token){
	return opers[token]!=null;
}
function parse(tokens){
	//操作符栈
	const opStk = [];
	//前缀表达式栈
	const prefix = [];
	//从右到左扫描中缀表达式
	for(let i=tokens.length-1;i>=0;i--){
		if(!isOp(tokens[i])){
			prefix.push(tokens[i]);
		}else{
			if(tokens[i]==')'){
				opStk.push(tokens[i]);
			}else if(tokens[i]=='('){
				while(opStk.length>0){
					if(opStk.peek() == ')'){
						opStk.pop();
						break;
					}else{
						prefix.push(opStk.pop());
					}
				}
			}else{
				while(opStk.length>0 && opStk.peek()!=')' && opers[opStk.peek()].pri>opers[tokens[i]].pri){
					prefix.push(opStk.pop());
				}
				opStk.push(tokens[i]);
			}
		}
	}
	while(opStk.length>0){
		prefix.push(opStk.pop());
	}
	return prefix.reverse();
}
(()=>{
	const exp = 'a&&b||c&&(d&&!e||!f)';
	var tokens = ['A','&&','(','!','B','&&','C',')'];
	tokens = 'A,&&,B,||,C,&&,(,D,&&,!,E,||,!,F,)'.split(',');
	console.info(tokens);
	const preExp = parse(tokens);
	console.info(preExp);//["||", "&&", "A", "B", "&&", "C", "||", "&&", "D", "!", "E", "!", "F"]
	const res = toTree(preExp);
	console.info(res);
})();
/**将前缀表达式转成树*/
function toTree(preExp){
	const node = {
			value:preExp[0],
			children:null
	};
	if(!isOp(preExp[0])){//如果为操作数，直接将节点返回
		return node;
	}
	node.children = [];
	let eleNum = opers[preExp[0]].eleNum;//几目运算符
	let j = 1;
	let end = 0;
	let start = 0;
	while(j<=eleNum){
		start = end+1;
		end = findExpEndIndex(preExp,start);
		node.children.push(toTree(preExp.slice(start,end+1)));
		j++;
	}
	return node;
}
function findExpEndIndex(exp,start){
	let index = start-1;
	let num = 0;
	do{
		index++;
		if(isOp(exp[index])){
			num = num - (opers[exp[index]].eleNum - 1);
		}else{
			num +=1;
		}
	}while(num<1);
	return index;
}

