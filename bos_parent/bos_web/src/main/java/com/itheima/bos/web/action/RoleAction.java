package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 角色管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//属性驱动，接收页面隐藏域拼接的字符串
	private String functionIds;
	
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	@Autowired
	private RoleService service;
	
	//添加角色
	public String add(){
		service.save(getModel(),functionIds);
		return LIST;
	}
	
	//分页查询
	public String pageQuery(){
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	
	//查询所有角色数据，返回json
	public String listajax(){
		List<Role> list = service.findAll();
		this.java2Json(list, new String[]{"functions","users"});
		return NONE;
	}
}
