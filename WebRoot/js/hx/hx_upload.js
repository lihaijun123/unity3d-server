jQuery(function($){
	//自定义
	var initJson1 = {};
	initJson1.defaultImage = "/js/uploadify/j_004.png";
	initJson1.fileExt = "*.jpg;*.png;*.gif;*.bmp";
	initJson1.fileDesc = "*.jpg;*.png;*.gif;*.bmp";
	//initJson1.buttonImg = "/js/uploadify/btn03.png";
	initJson1.onComplete = hxUploadOnComplete;

	var initJson2 = {};
	initJson2.defaultImage = "/js/uploadify/j_004.png";
	initJson2.fileExt = "*.unity3d";
	initJson2.fileDesc = "*.unity3d";
	initJson2.onComplete = hxUploadOnComplete;

	veUploadify(initJson1, "file_upload1");
	veUploadify(initJson2, "file_upload2");
	veUploadify(initJson2, "file_upload3");

});

function getfile_upload1Id(){
	return "picFileSn";
}
function getfile_upload1UrlParam(){
	return {type:"_pic_"};
}
function getfile_upload2Id(){
	return "unityFileSn";
}
function getfile_upload3Id(){
	return "unityIosFileSn";
}


function hxUploadOnComplete(event, ID, fileObj, response, data){
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
}
