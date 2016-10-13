
$(function(){
	var initJson = {};
	initJson.fileExt = "*.jpg;*.jpeg;*.png;";
	initJson.fileDesc = "*.jpg;*.jpeg;*.png;";
	veUploadify(initJson, "file_upload1");
	//预览图
	veUploadify(initJson, "file_upload3_0");
	veUploadify(initJson, "file_upload3_1");
	veUploadify(initJson, "file_upload3_2");
	veUploadify(initJson, "file_upload3_3");
	veUploadify(initJson, "file_upload3_4");
	veUploadify(initJson, "file_upload3_5");
	//识别图
	veUploadify(initJson, "file_upload3_6");
	veUploadify(initJson, "file_upload3_7");
	veUploadify(initJson, "file_upload3_8");
	veUploadify(initJson, "file_upload3_9");
	veUploadify(initJson, "file_upload3_10");
	veUploadify(initJson, "file_upload3_11");

	var initJson1 = {};
	initJson1.fileExt = "*.ipa;*.apk;";
	initJson1.fileDesc = "*.ipa;*.apk;";
	veUploadify(initJson1, "file_upload2_0");

	var initJson2 = {};
	initJson2.fileExt = "*.apk;*.ipa;";
	initJson2.fileDesc = "*.apk;*.ipa;";
	veUploadify(initJson2, "file_upload2_1");

	var initJson3 = {};
	initJson3.fileExt = "*.ipa;*.apk;";
	initJson3.fileDesc = "*.ipa;*.apk;";
	veUploadify(initJson3, "file_upload2_2");

	var initJson4 = {};
	initJson4.fileExt = "*.apk;*.ipa;";
	initJson4.fileDesc = "*.apk;*.ipa;";
	veUploadify(initJson4, "file_upload2_3");
	//图片删除

	$("a[id^='img_del_']").click(function(){
		if(confirm("是否删除")){
			var asn = $(this).attr("asn");
			if(asn){
				$.ajax({
					url:"/appbkmanage/imgdel/" + asn,
					type:"post",
					cache:false,
					success:function(data){
						if(data == "true"){
							window.location.reload();
						}
					},
					error:function(){

					}
				});
			}
		}
	});


});

//需提供文件html元素id
function getfile_upload1Id(){
	return "appIconFileSn";
}
//预览图
function getfile_upload3_0Id(){
	return "screenshots0picFileSn";
}
function getfile_upload3_1Id(){
	return "screenshots1picFileSn";
}
function getfile_upload3_2Id(){
	return "screenshots2picFileSn";
}
function getfile_upload3_3Id(){
	return "screenshots3picFileSn";
}
function getfile_upload3_4Id(){
	return "screenshots4picFileSn";
}
function getfile_upload3_5Id(){
	return "screenshots5picFileSn";
}
//识别图
function getfile_upload3_6Id(){
	return "screenshots6picFileSn";
}
function getfile_upload3_7Id(){
	return "screenshots7picFileSn";
}
function getfile_upload3_8Id(){
	return "screenshots8picFileSn";
}
function getfile_upload3_9Id(){
	return "screenshots9picFileSn";
}
function getfile_upload3_10Id(){
	return "screenshots10picFileSn";
}
function getfile_upload3_11Id(){
	return "screenshots11picFileSn";
}
//app文件
function getfile_upload2_0Id(){
	return "appFileSn_0";
}
function getfile_upload2_1Id(){
	return "appFileSn_1";
}
function getfile_upload2_2Id(){
	return "appFileSn_2";
}
function getfile_upload2_3Id(){
	return "appFileSn_3";
}