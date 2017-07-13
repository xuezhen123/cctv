package com.itheima.bos.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
	//根据账号和密码查询
	public User findByUsernameAndPassword(String username, String md5) {
		String hql = "from User where username = ? and password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,md5);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
