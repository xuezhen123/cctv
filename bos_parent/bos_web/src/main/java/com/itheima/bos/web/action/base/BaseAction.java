package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 表现层通用实现类
 * 
 * @author zhaoqx
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;

	public void setPage(int page) {
		pageBean.setCurrentpage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	// 将指定的对象转为json数据，并通过输出流写回客户端
	public void java2Json(Object object, String[] excludes) {
		// 使用json-lib将PageBean对象转为json数据
		JsonConfig jsonConfig = new JsonConfig();
		// 设置哪些属性不需要转为json
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(object, jsonConfig).toString();
		// 通过输出流将json数据写回客户端浏览器
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 将指定的对象转为json数据，并通过输出流写回客户端
	public void java2Json(List list, String[] excludes) {
		// 使用json-lib将PageBean对象转为json数据
		JsonConfig jsonConfig = new JsonConfig();
		// 设置哪些属性不需要转为json
		jsonConfig.setExcludes(excludes);
		String json = JSONArray.fromObject(list, jsonConfig).toString();
		// 通过输出流将json数据写回客户端浏览器
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String HOME = "home";
	public static final String LIST = "list";
	// 模型对象
	private T model;

	public T getModel() {
		return model;
	}

	// 在构造方法中动态获得实体类型，并通过反射创建model对象
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		// 动态获得实体类型
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
