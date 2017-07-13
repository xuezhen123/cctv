package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements RegionService{
	@Autowired
	private RegionDao dao;
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			dao.saveOrUpdate(region);
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Region> findAll() {
		return dao.findAll();
	}
	public List<Region> findByQ(String q) {
		return dao.findByQ(q);
	}
}
