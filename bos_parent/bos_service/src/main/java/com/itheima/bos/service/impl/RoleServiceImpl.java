package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao dao;

	//添加一个角色，同时需要关联权限
	public void save(Role role, String functionIds) {//1,2,3,4
		dao.save(role);
		if(StringUtils.isNotBlank(functionIds)){
			String[] ids = functionIds.split(",");
			for (String functionId : ids) {
				Function function = new Function(functionId);//托管对象
				role.getFunctions().add(function);//角色关联权限
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return dao.findAll();
	}
}
