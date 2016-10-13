<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/css/oss.css" />

    <link rel="stylesheet" type="text/css" href="/js/ext/css/ext-all.css" />
 	<script type="text/javascript" src="/js/ext/js/ext-base.js"></script>
    <script type="text/javascript" src="/js/ext/js/ext-all.js"></script>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>

	<link href="/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="/js/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="/js/uploadify/fileUploadInit_rootserver.js"></script>
	<script type="text/javascript" src="/js/uploadify/uploadify_zh_CN.js"></script>
	<script type="text/javascript">
	$(function(){
		var initJson_101 = {};
		initJson_101.onComplete = defaultOnComplete;
		veUploadify(initJson_101, "file_upload1");
	});

	function getfile_upload1Id(){
		return "fileIds";
	}

	//文件上传成功后默认的回调方法
	function defaultOnComplete(event, ID, fileObj, response, data){
		//文件上传空间对象的id
		var uploadFileId = event.currentTarget.id;

		//文件文本域id一般对应model中的一个属性
		var filedId = eval("get" + uploadFileId + "Id()");
		//返回文件id,url
		var returnJsonAry = eval("(" + response + ")");

		var rv = returnJsonAry[0];
		//新保存的id
		var newFileId = rv.fileId;
		//原先Id
		var oldFileId = $("#" + filedId).val();
		//修改文件后删除服务器上面的旧文件
		if(oldFileId){
			//deleteFileById(oldFileId);
		}
		var rStr = "{\n";
		for(var j in rv){
			rStr += (j + ":" + rv[j] + ", \n");
		}
		rStr += "}";
		$("#" + filedId).val(rStr);
		//var fileUrlValue = $("#fileUrls").val();
		//$("#fileUrls").val(rv.fileUrl);
		//如果有图片
		var imagFieldId = filedId + "_img";
		var imgObj = document.getElementById(imagFieldId);
		if(imgObj){
			$("#" + imagFieldId).attr("src", rv.fileUrl);
		}
		//如果有文件，则显示文件名称
		var fileNameDisplayId = filedId + "_namedisplay";
		var fileNameDisplayObj = document.getElementById(fileNameDisplayId);
		if(fileNameDisplayObj){
			$("#" + fileNameDisplayId).text("上传成功 " + fileObj.name);
		}
	}

	</script>
</head>

<body style="margin-top: 50px;">
		<form action="/fs/hx/xmladd" method="post">
			<table>
				<tr>
					<td width="20%">上传xml文件：</td>
					<td>
						<div style="width: 99%;float: left;">
							<input id="file_upload1" name="file_upload1" type="file" /><br>
							<span id="fileIds_namedisplay"></span>
				   		</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="提交"/>
					</td>
				</tr>

			</table>
		</form>
</body>

</html>
