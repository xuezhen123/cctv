package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限实体
 */

public class Function implements java.io.Serializable {

	// Fields

	private String id;
	private Function parentFunction;//当前权限的上级权限
	private String name;
	private String code;
	private String description;
	private String page;
	private String generatemenu;//是否生成菜单 1：生成 0：不生成
	private Integer zindex;
	private Set children = new HashSet(0);//当前权限的下级权限
	private Set roles = new HashSet(0);//当前权限对应的角色
	
	public String getText(){
		return name;
	}
	
	public String getpId(){
		if(parentFunction != null){
			return parentFunction.getId();
		}
		return "0";
	}
	
	public Function() {}
	
	public Function(String functionId) {
		this.id = functionId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Function getParentFunction() {
		return parentFunction;
	}
	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getGeneratemenu() {
		return generatemenu;
	}
	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}
	public Integer getZindex() {
		return zindex;
	}
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	public Set getChildren() {
		return children;
	}
	public void setChildren(Set children) {
		this.children = children;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
}