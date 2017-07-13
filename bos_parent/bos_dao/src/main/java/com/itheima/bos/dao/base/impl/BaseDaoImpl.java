package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用实现类
 * @author zhaoqx
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	//代表当前操作的实体的类型
	private Class<T> entityClass;
	
	//使用注解方式将sessionFactory对象注入
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//在构造方法中动态获得实体的类型
	public BaseDaoImpl() {
		//获得父类（BaseDaoImpl<T>）的类型
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得父类上声明的泛型数组对象
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "from " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	//执行任意更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			//为？赋值
			query.setParameter(i++, object);
		}
		query.executeUpdate();
	}

	//通用分页查询实现
	public void pageQuery(PageBean pageBean) {
		int currentpage = pageBean.getCurrentpage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//修改hibernate框架发出的sql形式为：select count(*) from xxx...
		detachedCriteria.setProjection(Projections.rowCount());
		//查询total：总记录数
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = list.get(0);
		pageBean.setTotal(total.intValue());
		//修改hibernate框架发出的sql形式为：select * from xxx...
		detachedCriteria.setProjection(null);
		
		//指定hibernate框架在进行连接查询join时如果封装对象
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (currentpage - 1) * pageSize;
		int maxResults = pageSize;
		//查询rows：当前页需要展示的数据集合
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	//根据任意条件查询
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
