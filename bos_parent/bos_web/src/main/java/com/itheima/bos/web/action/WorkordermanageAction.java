package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.WorkordermanageService;
import com.itheima.bos.web.action.base.BaseAction;
/**
 * 工作单管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	@Autowired
	private WorkordermanageService service;
	
	//添加工作单
	public String add() throws Exception{
		String flag = "1";
		try{
			service.save(getModel());
		}catch(Exception e){
			flag= "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
}
