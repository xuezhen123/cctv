package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.client.Customer;
/**
 * 定区管理
 * @author zhaoqx
 *
 */
import com.itheima.crm.client.CustomerService;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//属性驱动，接收多个分区id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	@Autowired
	private DecidedzoneService service;
	
	//添加定区
	public String add(){
		service.save(getModel(),subareaid);
		return LIST;
	}

	public String pageQuery(){
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentpage","pageSize","detachedCriteria","subareas","decidedzones"});
		return NONE;
	}
	
	//注入crm客户端代理对象
	@Autowired
	private CustomerService crmClient;
	
	//查询未关联到定区的客户
	public String findCustomerNotAssociation(){
		List<Customer> list = crmClient.findCustomerNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	//远程调用crm服务，根据定区id查询客户
	public String findCustomerByDecidedzoneId(){
		List<Customer> list = crmClient.findCustomerByDecidedzoneId(getModel().getId());
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	//属性驱动，接收多个客户id
	private List<Integer> customerIds;
	
	//远程调用crm服务，实现定区关联客户
	public String assignCustomersToDecidedzone(){
		crmClient.assignCustomersToDecidedzone(getCustomerIds(), getModel().getId());
		return LIST;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
}
