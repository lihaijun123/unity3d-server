//调用示例 start
/*
$(document).ready(function() {
	var initJson = {};
	//文件上传之后获取文件编号和访问的Url
	//initJson.onComplete = defaultOnComplete;
	//initJson.sizeLimit = 22336;
	initJson.buttonText = buttonTextBrowse;
	veUploadify(initJson, "file_upload1");
	veUploadify(initJson, "file_upload2");
});

//需提供文件html元素id
function getfile_upload1Id(){
	return "fileIds";
}
function getfile_upload2Id(){
	return "fileIds2";
}
//示例-图片压缩所需的参数

function getfile_upload1UrlParam(){
	return {compressCount:1, compressSize:"100*200,170*170,300*300"};
}

/*
function getfile_upload1UrlParam(){
	return {screenshotSize:"500*500", screenshotFromTime:10};
}
*/
/*
function getfile_upload1UrlParam(){
	return {attachType:"_3dmodel_"};
}
*/
//调用示例 end
/**
 *
 * 文件处理时的url参数
 */
function fileParam(){
	//图片压缩的张数,格式：{compressCount:3, compressSize:"100*200,170*170,300*300"}
	this.COMPRESS_COUNT = "compressCount";
	//图片压缩大小,格式：{compressCount:3, compressSize:"100*200,170*170,300*300"}
	this.COMPRESS_SIZE = "compressSize";
	//视频截图大小，格式：{screenshotSize:"500*500", screenshotFromTime:10}
	this.SCREENSHOT_SIZE = "screenshotSize";
	//视频从第几秒开始截图,格式：{screenshotSize:"500*500", screenshotFromTime:10}
	this.SCREENSHOT_FROM_TIME = "screenshotFromTime";
	//模型附件
	this.ATTACH_TYPE_MODEL = "_3dmodel_";
}

//处理选择文件服务器的Action的Url
function getServerActionOperUrl(doWhat){
	return "http://192.168.1.157:8017/nameserver/choose.do?timesstamp=" + new Date().getTime() + "&method=" + doWhat;
}
//文件管理的URL
function getFileManageActionUrl(rootUrl, doWhat){
	return rootUrl + "/dataserver.do?method=" + doWhat;
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
		deleteFileById(oldFileId);
	}
	$("#" + filedId).val(newFileId);
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

var isOccurError = false;
function veUploadify(initJsonValue, uploadFileId){
	var jsonObj = {};
	jsonObj["uploader"] = "/uploadify/uploadify.swf";
	jsonObj["script"] = "";
	jsonObj["cancelImg"] = "/uploadify/cancel.png";
	//选择文件服务器
	jsonObj["onSelectOnce"] = getFileServerPath;
	jsonObj["onSelect"] = validateUpload;
	if(!isEmpty(initJsonValue, "multi")){
		jsonObj["multi"] = initJsonValue.multi;
	}
	if(!isEmpty(initJsonValue, "simUploadLimit")){
		jsonObj["simUploadLimit"] = initJsonValue.simUploadLimit;
	}
	if(!isEmpty(initJsonValue, "queueSizeLimit")){
		jsonObj["queueSizeLimit"] = initJsonValue.queueSizeLimit;
	}
	if(!isEmpty(initJsonValue, "onComplete")){
		jsonObj["onComplete"] = initJsonValue.onComplete;
	}
	else {
		jsonObj["onComplete"] = defaultOnComplete;
	}
	if(!isEmpty(initJsonValue, "buttonText")){
		jsonObj["buttonText"] = initJsonValue.buttonText;
	}
	else if(!isEmpty(initJsonValue, "buttonImg")){
		jsonObj["buttonImg"] = initJsonValue.buttonImg;
	}
	else {
		jsonObj["buttonImg"] = "/uploadify/fileupload-browse.gif";
	}
	if(!isEmpty(initJsonValue, "fileDesc") && !isEmpty(initJsonValue, "fileExt")){
		jsonObj["fileExt"] = initJsonValue.fileExt;
		jsonObj["fileDesc"] = initJsonValue.fileDesc;
	}
	else {
		jsonObj["fileExt"] = "*.jpg;*.album;*.wmv;*.flv;*.mp3;*.wma;*.rar;*.3ds;*.ma;*.mb;*.swf;*.zip;*.unity3d;";
		jsonObj["fileDesc"] = "*.jpg;*.album;*.wmv;*.flv;*.mp3;*.wma;*.rar;*.3ds;*.ma;*.mb;*.swf;*.zip;*.unity3d;";
	}
	if(!isEmpty(initJsonValue, "sizeLimit")){
		jsonObj["sizeLimit"] = initJsonValue.sizeLimit;
	}
	jsonObj["onCancel"] = function(){return true;};
	jsonObj["onError"] = function(event, ID, fileObj, erorObj){
		if(erorObj.type === "File Size"){
			var clientlimitSize =  Math.round(initJsonValue.sizeLimit / 1024);
			if(clientlimitSize >= 1024){
				alert(sizeLimit_error_1 + Math.round(initJsonValue.sizeLimit / 1048576) + "M的" + getFileTypeLimitMsg(fileObj.type) + "，" + sizeLimit_error_2 + (fileObj.size / 1048576).toFixed(2) + "M");
			} else {
				alert(sizeLimit_error_1 + Math.round(initJsonValue.sizeLimit / 1024) + "KB的" + getFileTypeLimitMsg(fileObj.type) + "，" + sizeLimit_error_2 + (fileObj.size / 1024).toFixed(2) + "KB");
			}
			var uploadFileId = event.currentTarget.id;
			$("#" + uploadFileId).uploadifyCancel(ID);
			isOccurError = true;
		}
	};
	try {
		//初始化默认图片
		var filedId = eval("get" + uploadFileId + "Id()");
		if(filedId){
			var filedValue = $("#" + filedId).val();
			if(!filedValue){
				var imagFieldId = filedId + "_img";
				var imgObj = document.getElementsByTagName(imagFieldId);
				if(imgObj){
					$("#" + imagFieldId).attr("src", "/js/uploadFile/no_photo_cn.gif");
				}
			}
		}
	} catch (e) {
		//客户端未实现get方法
	}
	$("#" + uploadFileId).uploadify(jsonObj);
}

function getFileTypeLimitMsg(fileType){
	fileType = fileType.toLowerCase();
	if(fileType === ".jpeg" || fileType == ".jpg" || fileType === "gif"){
		return "图片";
	}
	if(fileType === ".album"){
		return "图册";
	}
	if(fileType === ".wmv" || fileType == ".flv" || fileType == ".swf"){
		return "视频";
	}
	if(fileType === ".rar" || fileType == ".zip"){
		return "压缩包";
	}
	if(fileType === ".mp3" || fileType == ".wma"){
		return "音频";
	}
	return "文件";
}

//为了验证上传的文件是否为空临时使用
var uploadTempID = "";

//验证上传文件的合法性
function validateUpload(event, ID, fileObj){
	uploadTempID = ID;
	validateFileExt(event, ID, fileObj);
}
//选择文件的时候验证文件格式
function validateFileExt(event, ID, fileObj){
	var configFileExtStr = event.data.fileExt;
	var extName = "*" + fileObj.type.toLowerCase();
	//控件id
	var uploadFileId = event.currentTarget.id;
	var validExtAry = null;
	var isFlag = false;
	//先取客户端配置的文件格式
	try {
		if(configFileExtStr){
			validExtAry = configFileExtStr.split(";");
		} else {
			//取用户提供限制文件格式的方法
			validExtAry = eval("get" + event.currentTarget.id + "Ext()");
		}
		for(var i = 0; i < validExtAry.length; i ++){
			var validExtName = validExtAry[i].toLowerCase();
			validExtName = validExtName.split(".")[0] != "*" ? "*" + validExtName : validExtName;
			if(extName === validExtName){
				isFlag = true;
			}
		}
		if(!isFlag){
			alert("文件格式有误，只能上传" + validExtAry + "格式的文件");
			setTimeout("doCancel('" + uploadFileId + "','" + ID + "')", 10);
		}
	} catch (e) {
		//
	}
}
//删除上传文件队列
function doCancel(uploadFileId, ID){
	$("#" + uploadFileId).uploadifyCancel(ID);
}
//获取文件服务器访问路径
function getFileServerPath(event, data){debugger;
	/*
	var fileId;
	var fileCountLimit;
	var selectFileCount = data.fileCount;
	try{
		fileId = eval("get" + event.currentTarget.id + "Id()");
		var fileIds = $("#" + fileId).val();
		fileCountLimit = eval("get" + event.currentTarget.id + "Count()");
		var fileIdCount = fileIds ? fileIds.substring(0, fileIds.length - 1).split(";").length : 0;
		if(parseInt(fileIdCount) + parseInt(selectFileCount) > parseInt(fileCountLimit)){
			vAlertWarning("");
			return;
		}
	}catch(e){
		fileId = "";
	}
	*/
	var uploadFileId = event.currentTarget.id;
	//$("#" + uploadFileId).uploadifyClearQueue();
	var paramJson;
	var prefileSn;
	var fileId;
	try{
		paramJson = eval("get" + event.currentTarget.id + "UrlParam()");
		fileId = eval("get" + event.currentTarget.id + "Id()");
		prefileSn = $("#" + fileId).val();
	}catch(e){
		paramJson = "";
		prefileSn = "";
		fileId = "";
	}
	if(!isOccurError && data.allBytesTotal === 0){
		alert("上传的文件内容为空，请重新上传");
		setTimeout("doCancel('" + uploadFileId + "','" + uploadTempID + "')", 10);
	}
	$.ajax({
		url : getServerActionOperUrl("choose") + "&bytesTotal=" + data.allBytesTotal,
	    type : "GET",
	    dataType : "jsonp",
	    jsonp : "jsoncallback",
	    success : function(jary){debugger;
			//设置文件上传的服务器
			var url = getFileManageActionUrl(jary[0].serverUrl, "uploadToTargetFilePath") + "&orinSn=" + 150614;

	    	if(paramJson){
	    		var jsonStr = "{";
	    		for(var param in paramJson){
	    			jsonStr += "'" + param + "':" + "'" + paramJson[param] + "'" + ",";
	    		}
	    		jsonStr = jsonStr.substring(0, jsonStr.length - 1) + "}";
	    		url += "&urlParam=" + jsonStr;
	    	}
	    	else {
	    		url += "&urlParam=";
	    	}
	    	$("#" + uploadFileId).uploadifySettings('script', url);
			//自动上传
	    	setTimeout("autoUpload('" + uploadFileId + "')", 10);
	    }
	});
}

function autoUpload(uploadFileId){
	$("#" + uploadFileId).uploadifyUpload();
}

//通过文件id删除
function deleteFileById(fileId){
	$.ajax({
		url : getServerActionOperUrl("geUrlInfo") + "&fileId=" + fileId,
	    type : "GET",
	    dataType : "jsonp",
	    jsonp : "jsoncallback",
	    success : function(json){
			$.ajax({
				url : getFileManageActionUrl(json.serverUrl, "delete") + "&subFoldName=" + json.subFoldName + "&fileName=" + json.fileName,
			    type : "GET",
			    dataType : "jsonp",
			    jsonp : "jsoncallback",
			    success : function(json){

			    }
			});
	    }
	});
}

//下载文件
function download(fileId){
	var serverUrl = getServerActionOperUrl("geUrlInfo");
	if(fileId.indexOf("http") != -1){
		serverUrl += "&visitAddr=" + encodeURIComponent(encodeURIComponent(fileId));
	}
	else
	{
		serverUrl += "&fileId=" + fileId;;
	}
	$.ajax({
		url : serverUrl,
	    type : "GET",
	    dataType : "jsonp",
	    jsonp : "jsoncallback",
	    success : function(json){
				var downloadUrl = getFileManageActionUrl(json.serverUrl, "download");
				if(fileId.indexOf("http") != -1){
					downloadUrl += "&visitAddr=" + encodeURIComponent(encodeURIComponent(fileId));
				}
				else
				{
					downloadUrl += "&fileSn=" + fileId;;
				}
	    		window.location = downloadUrl;
	    }
	});
}
function isEmpty(obj, key){
	return null == obj[key] || "" == obj[key];
}
