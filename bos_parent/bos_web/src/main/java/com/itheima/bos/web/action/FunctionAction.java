package com.itheima.bos.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 权限管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private FunctionService service;
	
	//查询所有权限，用于combotree展示
	public String listajax(){
		List<Function> list = service.findTopFunction();
		this.java2Json(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	//添加权限
	public String add(){
		service.save(getModel());
		return LIST;
	}
	
	public String pageQuery(){
		String page = getModel().getPage();
		pageBean.setCurrentpage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","children","roles"});
		return NONE;
	}
	
	//根据当前登录用户查询其对应的菜单数据，返回json
	public String findMenu(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("currentUser");
		List<Function> list = service.findMenuByUser(user);
		this.java2Json(list, new String[]{"parentFunction","children","roles"});
		return NONE;
	}
}
