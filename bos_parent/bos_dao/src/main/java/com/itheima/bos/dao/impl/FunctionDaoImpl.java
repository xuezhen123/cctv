package com.itheima.bos.dao.impl;
import com.itheima.bos.domain.Function;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao{
	//根据用户id查询权限
	public List<Function> findFunctionsByUserId(String userid) {
		String hql = "select distinct f from Function f left outer join f.roles r "
				+ "left outer join r.users u where u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, userid);
	}
	//根据用户id查询菜单
	public List<Function> findMenuByUserId(String userid) {
		String hql = "select distinct f from Function f left outer join f.roles r "
				+ "left outer join r.users u where u.id = ? "
				+ "and f.generatemenu = '1' order by f.zindex asc";
		return (List<Function>) this.getHibernateTemplate().find(hql, userid);
	}
}
