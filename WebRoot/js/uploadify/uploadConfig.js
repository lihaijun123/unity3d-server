
//=============================================
//�ļ��ϴ��ؼ�����
//xingshikang
//=============================================
//��ʼ��
$(document).ready(function() {
	$('#fileToUpload').uploadify({
	'uploader': '/script/mice/uploadFile/uploadify.swf',
	'folder':'E:/',
	'script': '/fileuploading/upload',
	'fileDataName':'fileToUpload',
	'simUploadLimit':10,
	'fileDesc':'ֻ֧��gif jpg pdf doc docx�ļ�',
	'fileExt':'*.gif;*.jpg;*.pdf;*.doc;*.docx',
	'queueSizeLimit':10,
	'buttonText':'ѡ���ļ�',
	'cancelImg': '/script/mice/uploadFile/cancel.png',
	'wmode': 'transparent',
	'multi':true,
	'onSelectOnce':uploadSelectOnce,
	'onQueueFull':uploadQueueFull,
	'onComplete':uploadComplete,
	'onAllComplete':uploadAllComplete
	});
});
//�ϴ��ɹ���
function uploadComplete(event,queueID,fileObj,response){
	if(response.substr(0,1)=="/"){
		response=response.substr(1);
	}
	$("input[name=uploadedFileName]").get(0).value+=response+"*";
//	$('#fileToUpload').uploadifySettings(
//			'scriptData',
//			{'uploadedFileName':$('input[name=uploadedFileName]').val()}
//	);
}
//ѡ�����ļ���
function uploadSelectOnce(event,data){
	$("#uploadControl").show();
}
//��������޶�����ʱ
function uploadQueueFull(event,queueSizeLimit){
	alert("�����˵����ļ�����ϴ�������");
	return false;
}
//�����ļ��ϴ��ɹ���
function uploadAllComplete(event,data){
	$("#uploadControl").hide();
	//alert("�����ļ��ϴ��ɹ�");
}