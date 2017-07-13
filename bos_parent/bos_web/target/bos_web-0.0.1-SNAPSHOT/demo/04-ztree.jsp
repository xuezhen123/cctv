<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ztree效果</title>
<!-- 引入easyUI的资源文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

</head>
<body class="easyui-layout">
	<!-- 最多可以分为5个区域，每个区域使用div表示 -->
	<div style="height: 100px" data-options="region:'north'">北部区域</div>
	<div style="width: 200px" data-options="region:'west'">
		<!-- 制作一个折叠面板 ，使用easyui-accordion fit:true表示自适应适应父容器的大小-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 使用子div表示其中的一个面板 -->
			<div data-options="iconCls:'icon-save'" title="系统菜单">
				<a id="mybutton" class="easyui-linkbutton">添加选项卡</a>
				<script type="text/javascript">
					$(function(){
						$("#mybutton").click(function(){
							//判断某个选项卡是否存在
							var e = $("#tt").tabs("exists","这是标题");
							if(e){
								//已经存在了，调用select方法选中
								$("#tt").tabs("select","这是标题");
							}else{
								//动态添加一个选项卡面板,调用easyUI提供的add方法
								$("#tt").tabs("add",{
									title:'这是标题',
									closable:true,
									iconCls:'icon-edit',
									content:'<iframe frameborder="no" width="100%" height="100%" src="https://www.baidu.com"></iframe>'
								});
							}
						});
					});
				</script>
			</div>
			<div data-options="iconCls:'icon-help'" title="业务菜单">
				<!-- 展示ztree效果：使用标准json数据构造 -->
				<ul id="myztree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，调用ztree提供的api动态创建树
						var setting = {};//通过这个json对象指定ztree的属性,使用默认值
						//构造节点数据
						var zNodes = [
						              {name:'节点一',children:[
						                                    	{name:'节点一_1'},
						                                    	{name:'节点一_2'}
						                                    ]},//每个json表示一个节点数据
						              {name:'节点二'},
						              {name:'节点三'}
						              ];
						//调用ztree的init初始化方法创建ztree到页面中
						$.fn.zTree.init($("#myztree1"), setting, zNodes);
					});
				</script>
			</div>
			<div title="ztree展示">
				<!-- 展示ztree效果：使用简单json数据构造 -->
				<ul id="myztree2" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，调用ztree提供的api动态创建树
						var setting2 = {
								data: {
									simpleData: {
										enable: true//支持简单格式json数据
									}
								}
						};//通过这个json对象指定ztree的属性,使用默认值
						//构造节点数据
						var zNodes2 = [
							              {id:'1',pId:'0',name:'节点一'},//每个json表示一个节点数据
							              {id:'2',pId:'1',name:'节点二'},
							              {id:'3',pId:'2',name:'节点三'}
						              ];
						//调用ztree的init初始化方法创建ztree到页面中
						$.fn.zTree.init($("#myztree2"), setting2, zNodes2);
					});
				</script>
			</div>
			<div title="发送ajax请求">
				<!-- 展示ztree效果：发送ajax请求获取json数据构造ztree -->
				<ul id="myztree3" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//页面加载完成后，调用ztree提供的api动态创建树
						var setting3 = {
								data: {
									simpleData: {
										enable: true//支持简单格式json数据
									}
								},
								callback: {
									//为节点绑定单击事件
									onClick: function(event, treeId, treeNode){
										//点击之后，调用tabs对象的add方法，动态添加选项卡
										if(treeNode.page != undefined){
											//判断当前选项卡是否已经存在
											var e = $("#tt").tabs("exists",treeNode.name);
											if(e){
												//存在了，选中就可以
												$("#tt").tabs("select",treeNode.name);
											}else{
												//点击的是叶子节点，需要添加选项卡
												$("#tt").tabs("add",{
													title:treeNode.name,
													closable:true,
													content:'<iframe frameborder="no" width="100%" height="100%" src="'+treeNode.page+'"></iframe>'
												});
											}
										}
									}
								}
						};
						$.post("${pageContext.request.contextPath }/json/menu.json",function(data){
							//调用ztree的init初始化方法创建ztree到页面中
							$.fn.zTree.init($("#myztree3"), setting3, data);
						});
					});
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 制作一个选项卡面板 -->
		<div id="tt" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示其中的一个面板 -->
			<div data-options="iconCls:'icon-save',closable:true" title="系统菜单">这是内容啊</div>
			<div data-options="iconCls:'icon-help'" title="业务菜单">这是内容啊</div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">东部区域</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>