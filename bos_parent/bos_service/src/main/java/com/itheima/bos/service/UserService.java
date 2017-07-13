package com.itheima.bos.service;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;

public interface UserService {
	public User login(User model);

	public void editPassword(String password, String id);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);
}
