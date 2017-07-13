package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService{
	@Autowired
	private DecidedzoneDao dao;
	@Autowired
	private SubareaDao subareaDao;
	
	//添加一个定区，同时需要关联分区
	public void save(Decidedzone model, String[] subareaid) {
		dao.save(model);
		//建立分区和定区的关系
		for (String sid : subareaid) {
			Subarea subarea = subareaDao.findById(sid);//分区对象
			subarea.setDecidedzone(model);//分区关联定区
		}
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
}
