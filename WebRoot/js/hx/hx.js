		$(function(){
			//叶子节点弹出框打开
			$("#hxChildModel").on("show.bs.modal", function(){
				var hxChildSn = $("#childSn").val();
				if(hxChildSn != ""){
					$.ajax({
						url:"/fs/hx/edit/" + hxChildSn,
						type:"get",
						cache:false,
						dataType:"json",
						success:function(data){
							$("#childSn").val(data.sn);
							$("#parentSn").val(data.parentSn);
							$("#childName").val(data.name);
							$("#childSummary").val(data.summary);
							var xmlId = data.xmlId;
							if(xmlId){
								$("#childXmlId").find("option[value=" + xmlId + "]").attr("selected", true);
							}
						},
						error:function(){

						}
					});

				}
			});
			//根节点弹出框打开
			$("#hxModel").on("show.bs.modal", function(){
				var sn = $("#sn").val();
				if(sn != ""){
					$.ajax({
						url:"/fs/hx/edit/" + sn,
						type:"get",
						cache:false,
						dataType:"json",
						success:function(data){
							$("#Sn").val(data.sn);
							$("#name").val(data.name);
							$("#summary").val(data.summary);
							var picFileUrl = "";
							if(!data.picFileUrl){
								picFileUrl = "/js/uploadify/j_004.png";
							} else {
								picFileUrl = data.picFileUrl;
							}
							$("#picFileSn_img").attr("src", picFileUrl);
							$("#picFileSn").val(data.picFileSn);
							$("#unityFileSn_namedisplay").text(data.unityFileName);
							$("#unityFileSn").val(data.unityFileSn);
							var xmlId = data.xmlId;
							if(xmlId){
								$("#xmlId").find("option[value=" + xmlId + "]").attr("selected", true);
							}
							$("#hxCode").val(data.hxCode);
							$("#buildingName").val(data.buildingName);
							$("#hxPrice").val(data.hxPrice);
							$("#hxSize").val(data.hxSize);
							$("#hxAddress").val(data.hxAddress);
							$("#unityIosFileSn_namedisplay").text(data.unityIosFileName);
							$("#unityIosFileSn").val(data.unityIosFileSn);
							$("#unityFileVersion").val(data.unityFileVersion);
							$("#unityIosFileVersion").val(data.unityIosFileVersion);
						},
						error:function(){

						}
					});

				}
			});
			//添加根节点
			$("#addHxInfBtn").click(function(){
				$("#hxModel").modal("show");
			});
			//根节点弹出框关闭
			$("#hxModel").on("hide.bs.modal", function(){
				$("#sn").val("");
				$("#parentSn").val("");
				$("#name").val("");
				$("#summary").val("");
				$("#picFileSn_img").attr("src", "/js/uploadify/j_004.png");
				$("#picFileSn").val("");
				$("#unityFileSn_namedisplay").text("");
				$("#unityFileSn").val("");

				$("#hxCode").val("");
				$("#buildingName").val("");
				$("#hxPrice").val("");
				$("#hxSize").val("");
				$("#hxAddress").val("");
				$("#unityIosFileSn_namedisplay").text("");
				$("#unityIosFileSn").val("");
				$("#unityFileVersion").val("");
				$("#unityIosFileVersion").val("");
			});
			//修改根节点
			$("a[id^='modifyHxBtn_']").click(function(){
				var parentSn = $(this).attr("parentSn");
				$("#sn").val(parentSn);
				$("#hxModel").modal("show");
			});
			//删除根节点
			$("a[id^='deleteHxBtn_']").click(function(){
				if(window.confirm("是否删除")){
					var hxSn = $(this).attr("hxSn");
					if(hxSn){
						$.ajax({
							url:"/fs/hx/delete/" + hxSn,
							type:"get",
							cache:false,
							dataType:"json",
							success:function(data){
								var status = data.status;
								if(status === 0){
									window.location.reload();
								}
							},
							error:function(){

							}
						});
					}
				}
			});

			//叶子节点弹出框关闭
			$("#hxChildModel").on("hide.bs.modal", function(){
				$("#childSn").val("");
				$("#parentSn").val("");
				$("#childName").val("");
				$("#childSummary").val("");
			});
			//添加叶子节点
			$("a[id^='addHxChildBtn_']").click(function(){
				var parentSn = $(this).attr("parentSn");
				$("#parentSn").val(parentSn);
				$("#hxChildModel").modal("show");
			});
			//节点展开关闭显示
			$("span[id^='index_']").click(function(){
				var parentSn = $(this).attr("parentSn");
				var trObj = $("#tr_" + parentSn);
				var textVl = $(this).text();
				if(textVl.indexOf("-") != -1){
					$(this).text("+");
					trObj.hide();
				} else {
					$(this).text("-");
					trObj.show();
				}
			});
			//修改叶子节点
			$("a[id^='modifyHxChildBtn_']").click(function(){
				var hxChildSn = $(this).attr("hxChildSn");
				$("#childSn").val(hxChildSn);
				$("#hxChildModel").modal("show");
			});
			//删除叶子节点
			$("a[id^='deleteHxChildBtn_']").click(function(){
				if(window.confirm("是否删除")){
					var hxChildSn = $(this).attr("hxChildSn");
					if(hxChildSn){
						$.ajax({
							url:"/fs/hx/delete/" + hxChildSn,
							type:"get",
							cache:false,
							dataType:"json",
							success:function(data){
								var status = data.status;
								if(status === 0){
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