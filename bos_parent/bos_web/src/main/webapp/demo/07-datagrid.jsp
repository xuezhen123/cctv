<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid效果</title>
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
</head>
<body>
	<!-- 数据表格使用方式一：将页面中静态HTML代码渲染为datagrid效果 -->
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>小明</td>
				<td>88</td>
			</tr>
			<tr>
				<td>002</td>
				<td>老李</td>
				<td>3</td>
			</tr>
		</tbody>
	</table>
	
	<hr>
	
	<!-- 数据表格使用方式二：发送ajax请求获取动态json数据动态创建datagrid -->
	<table class="easyui-datagrid" data-options="url:'datagrid.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
	</table>
	
	<hr>
	
	<!-- 数据表格使用方式三：调用easyUI提供的API动态创建datagrid -->
	<table id="mygrid"></table>
	<script type="text/javascript">
		$(function(){
			//页面加载完成后，调用easyUI提供的API在页面中动态创建一个datagrid
			$("#mygrid").datagrid({
				//定义标题行
				columns:[[
				          {field:'id',title:'编号',checkbox:true},//每个json对象表示一列
				          {field:'name',title:'姓名'},
				          {field:'age',title:'年龄'},
				          {field:'address',title:'地址'}
				          ]],
				//指定ajax请求的地址
				url:'datagrid.json',
				//显示行号
				rownumbers:true,
				//是否可以单选
				singleSelect:true,
				//定义数据表格的工具栏
				toolbar:[
				         {text:'添加',iconCls:'icon-add',
				        	 //为当前按钮绑定单击事件
				        	 handler:function(){
				        	 	alert("你点击了添加按钮");
				         		}},//每个json对象表示 一个按钮
				         {text:'删除',iconCls:'icon-remove'},
				         {text:'修改',iconCls:'icon-edit'}
				         ],
				//显示分页条
				pagination:true,
				pageList:[10,30,50,100]
			});
		});
	</script>
</body>
</html>