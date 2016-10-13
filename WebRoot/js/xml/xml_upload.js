$(function(){
		var initJson_101 = {};
		initJson_101.fileExt = "*.xml";
		initJson_101.fileDesc = "*.xml";
		initJson_101.onComplete = defaultOnComplete;
		veUploadify(initJson_101, "file_upload1");

		var initJson_102 = {};
		initJson_102.fileExt = "*.dat";
		initJson_102.fileDesc = "*.dat";
		initJson_102.onComplete = defaultOnComplete;
		veUploadify(initJson_102, "file_upload2");
	});

	function getfile_upload1Id(){
		return "xmlFileSn";
	}

	function getfile_upload2Id(){
		return "datFileSn";
	}

	function getfile_upload1UrlParam(){
		return {fileName:"home"};
	}
	function getfile_upload2UrlParam(){
		return {fileName:"home"};
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
		var newFileUrl = rv.fileUrl;
		//原先Id
		var oldFileId = $("#" + filedId).val();
		//修改文件后删除服务器上面的旧文件
		if(oldFileId){
			//deleteFileById(oldFileId);
		}
		$("#" + filedId).val(newFileId);
		//var fileUrlValue = $("#fileUrls").val();
		//$("#fileUrls").val(rv.fileUrl);
		//如果有图片
		var imagFieldId = filedId + "_img";
		var imgObj = document.getElementById(imagFieldId);
		if(imgObj){
			$("#" + imagFieldId).attr("src", newFileUrl);
		}
		//如果有文件，则显示文件名称
		var fileNameDisplayId = filedId + "_namedisplay";
		var fileNameDisplayObj = document.getElementById(fileNameDisplayId);
		if(fileNameDisplayObj){
			$("#" + fileNameDisplayId).text("上传成功 " + fileObj.name);
		}
		$("#datTrId").show();
	}
