package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.NoticebillDao;
import com.itheima.bos.dao.WorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.NoticebillService;
@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
	@Autowired
	private NoticebillDao dao;
	@Autowired
	private DecidedzoneDao decidedzoneDao;
	@Autowired
	private WorkbillDao workbillDao;
	//保存一个业务通知单，尝试自动分单
	public void save(Noticebill model, String decidedzoneId) {
		//获取当前用户
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("currentUser");
		model.setUser(user);
		dao.save(model);
		if(StringUtils.isNotBlank(decidedzoneId)){
			//定区id不为空，可以完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);//分单类型改为：自动分单
			//根据定区id查询定区对象
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			//根据定区查询取派员对象
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员对象
			model.setStaff(staff);
			//为取派员产生工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单产生时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态：未取件
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员对象
			workbill.setType(Workbill.TYPE_1);//工单类型：新单
			//保存工单
			workbillDao.save(workbill);
			//调用短信平台为取派员发送短信
		}else{
			//不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);//分单类型改为：人工分单
		}
	}
}
