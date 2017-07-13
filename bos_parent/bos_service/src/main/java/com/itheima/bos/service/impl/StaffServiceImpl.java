package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	//注入dao
	@Autowired
	private StaffDao dao;
	public void save(Staff model) {
		dao.save(model);
	}
	//分页查询
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	//取派员批量删除，属于逻辑删除
	public void deleteBatch(String ids) {//1,2,3,4
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String staffId : staffIds) {
				dao.executeUpdate("staff.delete", staffId);
			}
		}
	}
	public Staff findById(String id) {
		return dao.findById(id);
	}
	public void update(Staff staff) {
		dao.update(staff);
	}
	//查询所有未删除的取派员
	public List<Staff> findStaffNotDelete() {
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		//添加一个查询条件，deltag为0
		dc.add(Restrictions.eq("deltag", "0"));
		return dao.findByCriteria(dc);
	}
}
