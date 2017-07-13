package com.itheima.bos.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONObject;

/**
 * 用户实体
 */

public class User implements java.io.Serializable {

	// Fields

	private String id;
	private String username;
	private String password;
	private Double salary;
	private Date birthday;
	private String gender;
	private String station;
	private String telephone;
	private String remark;
	private Set noticebills = new HashSet(0);
	private Set<Role> roles = new HashSet(0);
	
	public String getRoleNames(){
		String names = "";
		if(roles != null && roles.size() > 0){
			for(Role role : roles){
				String name = role.getName();
				names += name + " ";
			}
		}
		return names;
	}
	
	public String getBirthdayString(){
		if(birthday != null){
			return new SimpleDateFormat("yyyy-MM-dd").format(birthday);
		}
		return "暂无数据";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set getNoticebills() {
		return noticebills;
	}
	public void setNoticebills(Set noticebills) {
		this.noticebills = noticebills;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
}