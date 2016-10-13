<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>app发布</title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
</head>
<body style="margin-top: 50px;">
	<%@include file="../workspace/navtop.jsp" %>
<form:form modelAttribute="app" action="/fs/appbkmanage/create" method="post">
	<div style="margin-left: 150px;">
	app发布概要


	<table class="table" >
		<tr>
			<td><font color="red">*</font>名称：</td>
			<td ><form:input path="name"/></td>
		</tr>
		<tr>
			<td><font color="red">*</font>图标：</td>
			<td >
				<img id="appIconFileSn_img" width="100xp;" height="100px;"/><br>
				<input id="file_upload1" name="file_upload1" type="file" />
				<input type="hidden" id="appIconFileSn" name="appIconFileSn"/>
				<form:errors path="appIconFileSn" cssClass="errors" />
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>描述：</td>
			<td >
				<form:textarea path="remark" cols="30" rows="5"/>
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>状态：</td>
			<td >
				<input type="radio" value="3" name="status" checked="checked"/>审核通过
				<input type="radio" value="4" name="status" />审核拒绝

			</td>
		</tr>
	</table>
	<!-- 具体app -->
	<%
		int lineNum = 2;
		for(int i =0; i < lineNum; i ++){
			pageContext.setAttribute("index", i);
	%>
	<hr>
	${app.detail[index].title }
	<table width="50%">
		<tr>
			<td><font color="red">*</font>名称：</td>
			<td ><form:input path="detail[${index }].name"/></td>
		</tr>
		<tr>
			<td><font color="red">*</font>版本号：</td>
			<td >
				<form:input path="detail[${index }].versionNum"/>
				<form:hidden path="detail[${index }].systemType"/>
				<form:hidden path="detail[${index }].deviceType"/>
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>包名：</td>
			<td >
				<form:input path="detail[${index }].codeId"/>
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>文件：</td>
			<td >
				<input id="appFileSn_${index }" name="detail[${index }].appFileSn" type="hidden"/>
				<input id="file_upload2_${index }" name="file_upload2_${index }" type="file" /><br>
				<span id="appFileSn_${index }_namedisplay" />
			</td>
		</tr>
		<tr>
			<td>下载通道链接</td>
			<td ><form:textarea path="detail[${index }].thirdDownloadUrl" cols="30" rows="5"/></td>
		</tr>
		<tr>
			<td>下载通道：</td>
			<td >
				<input type="radio" name="detail[${index }].downloadType" value="1" checked="checked"/>本地
				<input type="radio" name="detail[${index }].downloadType" value="2"/>第三方应用市场
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>描述：</td>
			<td >
				<form:textarea path="detail[${index }].remark" cols="30" rows="5"/>
			</td>
		</tr>
		<tr>
			<td><font color="red">*</font>状态：</td>
			<td >
				<input type="radio" name="detail[${index }].status" value="8" checked="checked"/>发布
				<input type="radio" name="detail[${index }].status" value="10"/>下架

			</td>
		</tr>
	</table>
	<% } %>

		<div style="margin-top: 10px;">
			<button type="submit" class="btn btn-primary" >
				提交
			</button>
			<a href="/fs/appbkmanage/list">返回列表</a>
		</div>
	</div>
</form:form>
</body>
<!-- Ext Js Lib Start -->
 <link type="text/css" rel="stylesheet" href="/js/uploadify/uploadify.css" />
	<script type="text/javascript" src="/js/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/js/uploadify/fileUploadInit.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify_zh_CN.js"></script>
<script type="text/javascript" src="/js/app/appinfo.js"></script>
</html>