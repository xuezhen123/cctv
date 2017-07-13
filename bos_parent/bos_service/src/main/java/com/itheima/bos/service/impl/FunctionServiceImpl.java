package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService{
	@Autowired
	private FunctionDao dao;
	public List<Function> findAll() {
		return dao.findAll();
	}
	//查询所有顶级权限
	public List<Function> findTopFunction() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.isNull("parentFunction"));
		detachedCriteria.addOrder(Order.desc("zindex"));
		return dao.findByCriteria(detachedCriteria);
	}
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		dao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	
	//根据用户查询对应的菜单数据
	public List<Function> findMenuByUser(User user) {
		if(user.getUsername().equals("admin")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			detachedCriteria.add(Restrictions.eq("generatemenu", "1"));
			detachedCriteria.addOrder(Order.asc("zindex"));
			//超级管理员,查询所有菜单
			return dao.findByCriteria(detachedCriteria);
		}else{
			return dao.findMenuByUserId(user.getId());
		}
	}
}
