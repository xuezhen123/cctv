<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>combobox效果</title>
<!-- 引入easyUI的资源文件 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
	<!-- 将页面静态HTML渲染为combobox样式-->
	<select class="easyui-combobox">
		<option value="1">小明</option>
		<option>小红</option>
		<option>小李</option>
	</select>
	 
	 
	 <!-- 发送ajax请求获取动态数据创建combobox -->
	 <input id="cc" class="easyui-combobox" name="dept" 
	 	data-options="valueField:'id',textField:'name',url:'combobox.json'" />
</body>
</html>