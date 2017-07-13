package com.itheima.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 自定义拦截器，实现用户未登录自动跳转到登录页面
 * @author zhaoqx
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{
	//方法拦截
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//从session中获取登录用户对象
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("currentUser");
		if(user == null){
			//没有登录，跳转到登录页面
			return "login";
		}else{
			//已经登录，放行
			return invocation.invoke();
		}
	}
}
