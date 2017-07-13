package com.itheima.bos.web.action;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 取派员信息管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private StaffService service;
	//添加取派员
	public String add(){
		service.save(getModel());
		return LIST;
	}

	//分页查询方法----每页过滤条件的分页查询
	public String pageQuery() throws Exception{
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentpage","pageSize","detachedCriteria","decidedzones"});
		return NONE;
	}
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	//批量删除
	@RequiresPermissions("deleteStaff")//调用这个方法需要用户具有deleteStaff这个权限
	public String deleteBatch(){
		service.deleteBatch(ids);
		return LIST;
	}

	//根据id修改取派员信息
	//@RequiresPermissions("editStaff")
	public String edit(){
		//调用shiro框架提供的API完成权限校验
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("editStaff");//检查当前用户是否具有editStaff这个权限
		
		//1、查询数据库中原始数据
		Staff staff = service.findById(getModel().getId());
		
		//2、使用页面提交的数据进行覆盖
		staff.setName(getModel().getName());
		staff.setTelephone(getModel().getTelephone());
		staff.setStandard(getModel().getStandard());
		staff.setStation(getModel().getStation());
		staff.setHaspda(getModel().getHaspda());
		
		service.update(staff);
		return LIST;
	}
	
	//查询所有未删除的取派员，返回json
	public String listajax(){
		List<Staff> list = service.findStaffNotDelete();
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
	}
}
