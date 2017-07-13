package com.itheima.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

/**
 * 自定义realm
 * 
 * @author zhaoqx
 *
 */
public class BosRealm extends AuthorizingRealm {
	@Autowired
	private UserDao userDao;
	@Autowired
	private FunctionDao functionDao;
	
	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证方法执行了。。。。");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String username = usernamePasswordToken.getUsername();//页面输入的账号
		//根据页面输入的username查询数据库中的密码
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("username", username));
		List<User> list = userDao.findByCriteria(detachedCriteria);
		if(list != null && list.size() > 0){
			User user = list.get(0);
			AuthenticationInfo info  = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
			return info;
		}else{
			//没有查询到数据
			return null;
		}
	}

	// 授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//创建一个简单授权信息对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权
		// 后期需要修改为查询当前登录用户对应的数据库中实际权限，进行授权
		//Subject subject = SecurityUtils.getSubject();
		//User user1 = (User) subject.getPrincipal();
		//使用shiro框架提供的方式获取当前登录用户
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//当前用户是内置超级管理员账户,查询所有权限，为其授权
			list = functionDao.findAll();
		}else{
			//当前用户是普通账户，查询其对应的权限数据
			list = functionDao.findFunctionsByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}
}
