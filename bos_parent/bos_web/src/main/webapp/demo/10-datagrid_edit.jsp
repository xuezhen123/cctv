<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid编辑效果</title>
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
	<!-- 数据表格使用方式三：调用easyUI提供的API动态创建datagrid -->
	<table id="mygrid"></table>
	<script type="text/javascript">
		$(function(){
			var myIndex = -1;//全局变量，值为当前正处于编辑状态行的索引
			
			//页面加载完成后，调用easyUI提供的API在页面中动态创建一个datagrid
			$("#mygrid").datagrid({
				//定义标题行
				columns:[[
				          {field:'id',title:'编号',checkbox:true},//每个json对象表示一列
				          {width:100,field:'name',title:'姓名',
				        	  								//指定当前列具有编辑功能
				        	  								editor:{
				        	  									type:'validatebox',
				        	  									options:{
				        	  										required:true
				        	  									}
				         									}
				           },
				          {width:100,field:'age',title:'年龄',//指定当前列具有编辑功能
 								editor:{
  									type:'numberbox',
  									options:{}
									}},
				          {width:200,field:'address',title:'地址',//指定当前列具有编辑功能
    	  								editor:{
    	  									type:'datebox',
    	  									options:{}
     									}}
				          ]],
				//指定ajax请求的地址
				url:'datagrid.json',
				//显示行号
				rownumbers:true,
				//是否可以单选
				singleSelect:true,
				
				//结束编辑状态时触发的事件
				onAfterEdit:function(a,rowData,c){
					//将结束编辑时行数据提交到服务器
					console.info(rowData);
					//发送ajax请求，将编辑后的数据提交到服务器
					$.post("xxx",rowData,function(data){
						
					});
				},
				
				//定义数据表格的工具栏
				toolbar:[
				         {text:'添加',iconCls:'icon-add',
				        	 //为当前按钮绑定单击事件
				        	 handler:function(){
				        		 $("#mygrid").datagrid("insertRow",{
				        			 index:0,//在第一行插入数据
				        			 row:{}//空行
				        		 });
				        		 myIndex = 0;
				        		 $("#mygrid").datagrid("beginEdit",myIndex);
				         		}},//每个json对象表示 一个按钮
				         {text:'删除',iconCls:'icon-remove',handler:function(){
				        	 //选中哪一行，删除哪一行
				        	 var rows = $("#mygrid").datagrid("getSelections");
				        	 if(rows.length == 1){
				        		 var row = rows[0];
				        		 var index = $("#mygrid").datagrid("getRowIndex",row);
					        	 $("#mygrid").datagrid("deleteRow",index);
				        	 }
				         }},
				         {text:'修改',iconCls:'icon-edit',handler:function(){
				        	 var rows = $("#mygrid").datagrid("getSelections");
				        	 if(rows.length == 1){
				        		 var row = rows[0];
				        		 myIndex = $("#mygrid").datagrid("getRowIndex",row);
					        	 //调用数据表格的beginEdit方法，开启数据表格编辑状态
					        	 $("#mygrid").datagrid("beginEdit",myIndex);
				        	 }
				        	 
				         }},
				         {text:'保存',iconCls:'icon-save',handler:function(){
				        	 //调用数据表格的endEdit方法，结束数据表格编辑状态
				        	 $("#mygrid").datagrid("endEdit",myIndex);
				         }}
				         ],
				//显示分页条
				pagination:true,
				pageList:[10,30,50,100]
			});
		});
	</script>
</body>
</html>