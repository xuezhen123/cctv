package com.itheima.bos.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonLibTest {
	//测试json-lib将Java对象转为json数据
	@Test
	public void test1(){
		//JSONObject----可以将单个对象或者Map集合转为json数据
		User user = new User();
		user.setId("001");
		user.setUsername("test");
		String json = JSONObject.fromObject(user).toString();
		System.out.println(json);
		
		//JSONArray-----可以将List集合或者数组对象转为json数据
		List<User> list = new ArrayList<>();
		list.add(user);
		json = JSONArray.fromObject(list).toString();
		System.out.println(json);
	}
	
	//json-lib提供解决死循环文件方案
	@Test
	public void test2(){
		Staff staff = new Staff();
		staff.setId("001");
		staff.setName("abc");
		
		Decidedzone decidedzone = new Decidedzone();
		decidedzone.setId("dq001");
		decidedzone.setName("朝阳定区");
		
		decidedzone.setStaff(staff);//定区关联取派员
		staff.getDecidedzones().add(decidedzone);//取派员关联定区
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		String json = JSONObject.fromObject(staff,jsonConfig).toString();
		System.out.println(json);
	
	}
}
