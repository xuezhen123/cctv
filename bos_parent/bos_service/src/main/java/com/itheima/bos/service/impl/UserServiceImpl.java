package com.itheima.bos.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements UserService{
	//注入dao
	@Autowired
	private UserDao dao;
	public User login(User user) {
		String md5 = MD5Utils.md5(user.getPassword());
		return dao.findByUsernameAndPassword(user.getUsername(),md5);
	}
	//根据id修改用户的密码
	public void editPassword(String password, String id) {
		password = MD5Utils.md5(password);
		dao.executeUpdate("user.editPassword",password,id);
	}
	
	//添加一个用户，同时关联角色
	public void save(User user, String[] roleIds) {
		//将密码使用md5进行加密
		String md5 = MD5Utils.md5(user.getPassword());
		user.setPassword(md5);
		dao.save(user);
		if(roleIds != null  && roleIds.length > 0){
			for(String roleId : roleIds){
				Role role = new Role(roleId);//托管对象
				user.getRoles().add(role);//用户关联角色
			}
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
}
