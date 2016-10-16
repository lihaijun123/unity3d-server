<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>App发布</title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
</head>
<body style="margin-top: 50px;">
	<%@include file="../workspace/navtop.jsp" %>
	<input type="hidden" id="tab_index" value="3"/>
	<div class="table-responsive">
		<table class="table table-hover" >
		 	<caption>App发布</caption>
			<thead>
				<tr>
					<th ><h4>序号</h4></th>
					<th ><h4>名称</h4></th>
					<th ><h4>状态</h4></th>
					<th ><h4>创建时间</h4></th>
					<th ><a href="/fs/appbkmanage/new" ><span class="glyphicon glyphicon-plus"></span></a></th>
				</tr>
			</thead>
			<c:forEach var="app" items="${appList }" varStatus="status">
			<tbody>
				<tr>
					<td >${status.index + 1}</td>
					<td >${app.name }</td>
					<td >${app.status eq 3 ? "审核通过" : app.status eq 33 ? "内审通过" : "审核拒绝" }</td>
					<td >${app.addTime }</td>
					<td ><a href="/fs/appbkmanage/edit/${app.sn }" ><span class="glyphicon glyphicon-pencil"></span></a></td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
	</div>
</body>
</html>