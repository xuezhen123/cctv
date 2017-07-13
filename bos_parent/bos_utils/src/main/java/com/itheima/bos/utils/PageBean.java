package com.itheima.bos.utils;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
/**
 * 封装分页相关的属性
 * @author zhaoqx
 *
 */
public class PageBean {
	private int currentpage;//当前页码
	private int pageSize;//每页显示的记录数
	private DetachedCriteria detachedCriteria;//封装查询条件
	private int total;//总记录数(查询数据库获得)
	private List rows;//当前页需要展示的数据集合(查询数据库获得)
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}