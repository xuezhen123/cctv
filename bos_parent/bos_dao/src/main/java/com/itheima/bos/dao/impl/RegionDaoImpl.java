package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {
	// 根据q查询区域数据
	public List<Region> findByQ(String q) {
		String hql = "from Region where province like ? or city like ? "
				+ "or district like ? or citycode like ? or shortcode like ?";
		return (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%");
	}
}
