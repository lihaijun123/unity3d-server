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
<body >
	<%@include file="../workspace/navtop.jsp" %>
	<form:form modelAttribute="app" action="/fs/appbkmanage/edit" method="post">
	<div style="float: left;margin-left: 250px;margin-bottom:30px;width: 100%">
		<a href="/fs/appbkmanage/new">新建</a>|
		<a href="/fs/appbkmanage/list">返回列表</a>
	</div>
	<div style="margin-left: 250px;">



	<table class="table">
		<form:hidden path="sn"/>
		<tr>
			<td><font color="red">*</font>名称：</td>
			<td ><form:input path="name"/></td>
		</tr>
		<tr>
			<td><font color="red">*</font>图标：</td>
			<td >
				<img id="appIconFileSn_img" src="${app.appIconFileUrl }" width="100xp;" height="100px;"/><br>
				<input id="file_upload1" name="file_upload1" type="file" />
				<input type="hidden" id="appIconFileSn" name="appIconFileSn" value="${app.appIconFileSn }"/>
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
				<c:if test="${app.status eq 3}">
					<input type="radio" value="3" name="status" checked="checked"/>审核通过
					<input type="radio" value="4" name="status" />审核拒绝
				</c:if>
				<c:if test="${app.status eq 4}">
					<input type="radio" value="3" name="status" />审核通过
					<input type="radio" value="4" name="status" checked="checked"/>审核拒绝
				</c:if>

			</td>
		</tr>

	</table>
	<table>
		<tr>
			<td>下载地址</td>
			<td>${qrContent }</td>
		</tr>
		<tr>
			<td>下载二维码</td>
			<td><img src="/fs/appbkmanage/qrcode/${app.sn }" height="150px;" width="150px"/></td>
		</tr>
	</table>
	<!-- 具体app -->
	<%
		int lineNum = 2;
		for(int i =0; i < lineNum; i ++){
			request.setAttribute("index", i);
	%>
	<hr>
	${app.detail[index].title }

	<input type="hidden" value="${app.detail[index].plistVisitUrl }"/>
	<form:hidden path="detail[${index }].sn"/>
	<form:hidden path="detail[${index }].downloadNum"/>
	<table width="50%">
	    <c:if test="${app.detail[index].qrFileSn ne null}">
	    <tr>
	    <td>二维码：</td>
	    <td>
	    	<f:img width="100" height="100" src="${app.detail[index].qrFileSn }"/>
	    	<f:fileDownload value="${app.detail[index].appFileSn }"/>
	    </td>
	    </tr>
	    </c:if>
		<tr>
			<td>编号：</td>
			<td >
			<form:hidden path="detail[${index }].appId"/>
			${app.detail[index].appId }
			</td>
		</tr>
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
				<input id="appFileSn_${index }" name="detail[${index }].appFileSn" type="hidden" value="${app.detail[index].appFileSn }"/>
				<input id="file_upload2_${index }" name="file_upload2_${index }" type="file" /><br>
				<span id="appFileSn_${index }_namedisplay">${app.detail[index].fileName }</span>
			</td>
		</tr>
		<tr>
			<td>下载通道链接：</td>
			<td ><form:textarea path="detail[${index }].thirdDownloadUrl" cols="30" rows="5"/></td>
		</tr>
		<tr>
			<td>下载通道：</td>
			<td >
				<c:if test="${app.detail[index].downloadType eq 1}">
					<input type="radio" name="detail[${index }].downloadType" value="1" checked="checked"/>本地
					<input type="radio" name="detail[${index }].downloadType" value="2"/>第三方应用市场
				</c:if>
				<c:if test="${app.detail[index].downloadType eq 2}">
					<input type="radio" name="detail[${index }].downloadType" value="1" />本地
					<input type="radio" name="detail[${index }].downloadType" value="2" checked="checked"/>第三方应用市场
				</c:if>
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
			<c:if test="${app.detail[index].status eq 8}">
				<input type="radio" name="detail[${index }].status" value="8" checked="checked"/>发布
				<input type="radio" name="detail[${index }].status" value="10"/>下架
			</c:if>
			<c:if test="${app.detail[index].status eq 10}">
				<input type="radio" name="detail[${index }].status" value="8"/>发布
				<input type="radio" name="detail[${index }].status" value="10" checked="checked"/>下架
			</c:if>
			<c:if test="${app.detail[index].status ne 8 && app.detail[index].status ne 10}">
				<input type="radio" name="detail[${index }].status" value="8"/>发布
				<input type="radio" name="detail[${index }].status" value="10" checked="checked"/>下架
			</c:if>
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
 <link type="text/css" rel="stylesheet" href="/js/uploadify/uploadify.css" />
	<script type="text/javascript" src="/js/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/js/uploadify/fileUploadInit.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify_zh_CN.js"></script>
<script type="text/javascript" src="/js/app/appinfo.js"></script>
</html>