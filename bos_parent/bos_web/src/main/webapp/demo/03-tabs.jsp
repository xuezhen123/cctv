<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs效果</title>
<!-- 引入easyUI的资源文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
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
			<div data-options="iconCls:'icon-help'" title="业务菜单">这是内容啊</div>
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