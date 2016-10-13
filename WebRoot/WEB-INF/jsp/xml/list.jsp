<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
   <title></title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
</head>
<body style="margin-top: 50px;">
	<%@include file="../workspace/navtop.jsp" %>
	<input type="hidden" id="tab_index" value="1"/>
	<div style="margin-top: 30px;float: left;width: 100%;">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>添加时间</th>
					<th>
					<c:if test="${fn:length(xmlInfoList) <= 0}">
						<a href="/fs/xml/add">添加</a>
					</c:if>
					&nbsp;
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="xmlInfo" items="${xmlInfoList }" varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${xmlInfo.name}</td>
						<td>${xmlInfo.addTime}</td>
						<td>
							<a href="/fs/xml/edit/${xmlInfo.encryptSn }">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>

</html>
