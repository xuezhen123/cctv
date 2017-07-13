package com.itheima.bos.dao;

import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.domain.User;

public interface UserDao extends BaseDao<User>{
	//根据账号和密码查询
	public User findByUsernameAndPassword(String username, String md5);
}
