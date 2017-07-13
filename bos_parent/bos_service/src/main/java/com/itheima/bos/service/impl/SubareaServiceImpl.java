package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService{
	@Autowired
	private SubareaDao dao;
	public void save(Subarea model) {
		dao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Subarea> findAll() {
		return dao.findAll();
	}
	//查询所有未分配到定区的分区
	public List<Subarea> findSubareaNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加一个过滤条件,就是分区类中decidedzone属性为null的就是未分配的
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return dao.findByCriteria(detachedCriteria);
	}
	
	//根据定区id查询分区
	public List<Subarea> findSubareasByDecidedzoneId(String did) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", did));
		return dao.findByCriteria(detachedCriteria);
	}
}
