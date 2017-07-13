package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	// 属性驱动，接收页面用户输入的验证码
	private String checkcode;

	@Autowired
	private UserService service;

	// 登录方法---使用shiro框架提供的方式进行认证操作
	public String login() {
		// 从session中获取生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		// 判断页面输入的验证码是否正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			//使用shiro提供的方式进行认证操作
			Subject subject = SecurityUtils.getSubject();//代表当前用户对象
			//用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), MD5Utils.md5(getModel().getPassword()));
			try{
				subject.login(token);
				User user = (User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("currentUser", user);
				return HOME;
			}catch(Exception e){
				//登录失败
				e.printStackTrace();
				return LOGIN;
			}
		} else {
			// 输入的验证码错误,设置错误信息，跳转到登录页面
			// 登录失败，设置错误信息，跳转到登录页面
			this.addActionError("验证码输入错误！");
			return LOGIN;
		}
	}

	// 登录方法
	public String login_bak() {
		// 从session中获取生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		// 判断页面输入的验证码是否正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			// 输入的验证码正确
			// 调用service完成登录
			User user = service.login(getModel());
			if (user != null) {
				// 登录成功，跳转到主页面
				ServletActionContext.getRequest().getSession().setAttribute("currentUser", user);
				return HOME;
			} else {
				// 登录失败，设置错误信息，跳转到登录页面
				this.addActionError("账号或者密码输入错误！");
				return LOGIN;
			}
		} else {
			// 输入的验证码错误,设置错误信息，跳转到登录页面
			// 登录失败，设置错误信息，跳转到登录页面
			this.addActionError("验证码输入错误！");
			return LOGIN;
		}
	}

	// 用户注销
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}

	// 修改密码
	public String editPassword() throws Exception {
		String flag = "1";
		// 获得页面提交的密码
		String password = getModel().getPassword();
		// 从session中获取当前登录用户对象
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("currentUser");
		try {
			service.editPassword(password, user.getId());
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	//属性驱动，接收多个roleIds参数
	private String[] roleIds;
	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	//添加用户
	public String add(){
		service.save(getModel(),roleIds);
		return LIST;
	}
	
	//分页查询
	public String pageQuery(){
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
}
