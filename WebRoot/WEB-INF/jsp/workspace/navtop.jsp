<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function(){
		var tab_index = $("#tab_index").val();
		if(!isNaN(tab_index)){
			$("#index_" + tab_index).addClass("active");
		}
	});
</script>


<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
   <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse"
         data-target="#example-navbar-collapse">
         <span class="sr-only">切换导航</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/fs/xml/list">后台系统</a>
   </div>
   <div class="collapse navbar-collapse" id="example-navbar-collapse">
      <ul class="nav navbar-nav">
		<li id="index_1"><a href="/fs/xml/list">文件管理</a></li>
		<li id="index_2"><a href="/fs/user/list">注册用户</a></li>
		<li id="index_3"><a href="/fs/appbkmanage/list">App发布</a></li>
		<li id="index_4" class="dropdown">
         	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
         		统计<b class="caret"></b>
         	</a>
         	<ul class="dropdown-menu">
               <li><a href="/fs/chart/scanimg">识别图</a></li>
               <li class="divider"></li>
               <li><a href="/fs/chart/usetime">使用时长</a></li>
               <li class="divider"></li>
               <li><a href="/fs/chart/appdownload">App下载</a></li>
            </ul>
         </li>
      </ul>
      <p class="navbar-text navbar-right">&nbsp;</p>
      <p class="navbar-text navbar-right">&nbsp;</p>
      <p class="navbar-text navbar-right"><a href="/fs/login/logout">退出</a></p>
   </div>
</nav>
