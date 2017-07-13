package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;

public interface FunctionService {
	public List<Function> findAll();
	public List<Function> findTopFunction();
	public void save(Function model);
	public void pageQuery(PageBean pageBean);
	public List<Function> findMenuByUser(User user);
}
