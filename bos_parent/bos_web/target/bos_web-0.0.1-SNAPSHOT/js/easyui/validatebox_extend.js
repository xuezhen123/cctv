$(function(){
	//扩展校验规则(验证新密码和确认密码输入一致)
	$.extend($.fn.validatebox.defaults.rules, {
		equals: {
			validator: function(value,param){
				return value == $(param[0]).val(); 
			},
			message: '新密码和确认密码输入不一致!' 
		}
	});
	
	//扩展校验规则(对输入的手机号进行校验)
	var regTel = /^1[3|5|7|8][0-9]{9}$/;
	$.extend($.fn.validatebox.defaults.rules, {
		telephone: {
			validator: function(value){
				return regTel.test(value);
			}, 
			message: '手机号输入不正确!' 
		}
	});
});
