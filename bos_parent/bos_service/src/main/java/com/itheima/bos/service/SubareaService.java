package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface SubareaService {
	public void save(Subarea model);
	public void pageQuery(PageBean pageBean);
	public List<Subarea> findAll();
	public List<Subarea> findSubareaNotAssociation();
	public List<Subarea> findSubareasByDecidedzoneId(String did);
}
