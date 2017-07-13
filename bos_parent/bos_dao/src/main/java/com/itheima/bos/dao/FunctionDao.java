package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.domain.Function;

public interface FunctionDao extends BaseDao<Function> {

	public List<Function> findFunctionsByUserId(String id);

	public List<Function> findMenuByUserId(String id);

}
