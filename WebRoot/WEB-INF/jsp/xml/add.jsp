<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <title></title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.min-3.3.5.js"></script>

    <link type="text/css" rel="stylesheet" href="/js/uploadify/uploadify.css" />
	<script type="text/javascript" src="/js/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/js/uploadify/fileUploadInit.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify_zh_CN.js"></script>
	<script type="text/javascript" src="/js/xml/xml_upload.js"></script>
</head>

<body style="margin-top: 50px;">
		<%@include file="../workspace/navtop.jsp" %>
		<div style="margin-top: 30px;float: left;width: 100%;">
			<form action="/fs/xml/add" method="post">
				<table class="table">
					<tr>
						<td>名称：</td>
						<td><input type="text" class="form-control" id="childName" name="name" maxlength="15" placeholder="请输入名称"/></td>
					</tr>
					<tr>
						<td width="20%">上传xml文件：</td>
						<td>
							<div style="width: 99%;float: left;">
								<input type="hidden" id="xmlFileSn" name="xmlFileSn"/>
								<input id="file_upload1" name="file_upload1" type="file" /><br>
								<span id="xmlFileSn_namedisplay"></span>
					   		</div>
						</td>
					</tr>


					<tr id="datTrId" style="display: none;">
						<td width="20%">上传dat文件：</td>
						<td>
							<div style="width: 99%;float: left;">
								<input type="hidden" id="datFileSn" name="datFileSn"/>
								<input id="file_upload2" name="file_upload2" type="file" /><br>
								<span id="datFileSn_namedisplay"></span>
						   	</div>
						</td>
					</tr>

					<tr>
						<td colspan="2" align="center">
							<button type="submit" class="btn btn-primary">
			               		提交
			            	</button>
						</td>
					</tr>

				</table>
			</form>
		</div>
</body>

</html>
