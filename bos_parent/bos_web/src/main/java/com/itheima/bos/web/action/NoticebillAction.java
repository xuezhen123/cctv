package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.client.Customer;
import com.itheima.crm.client.CustomerService;

/**
 * 业务通知单管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//注入crm代理对象
	@Autowired
	private CustomerService customerService;
	@Autowired
	private NoticebillService service;
	
	//远程调用crm服务，根据手机号查询客户信息
	public String findCustomerByTelephone(){
		Customer customer = customerService.findCustomerByTelephone(getModel().getTelephone());
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	//属性驱动，接收定区id
	private String decidedzoneId;
	
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	//添加一个业务通知单，并且尝试自动分单
	public String add(){
		service.save(getModel(),decidedzoneId);
		return "noticebill_add";
	}
}
