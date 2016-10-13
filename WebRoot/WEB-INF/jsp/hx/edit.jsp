<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix= "fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<script type="text/javascript" src="/js/hx/hx_upload.js"></script>
	<script type="text/javascript" src="/js/hx/hx.js"></script>

</head>

<body style="margin-top: 50px;">
	<%@include file="../workspace/navtop.jsp" %>
	<input type="hidden" id="tab_index" value="2"/>
	<div style="margin-top: 30px;float: left;width: 100%;">
		xml文件名称：${xmlInfo.name }
		<br></br>
		<table class="table">
			<thead>
				<tr>
					<td>名称</td>
					<td>关联的xml属性</td>
					<td><a class="btn" id="addHxInfBtn"><font color="red">添加户型</font></a></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="hxInfo" items="${hxInfoList }">
					<tr>
						<td>
							<span id="index_${hxInfo.sn }" parentSn="${hxInfo.sn }">
								<c:if test="${fn:length(hxInfo.children) > 0}">
								-
								</c:if>
							</span>
							<a class="btn" id="modifyHxBtn_${hxInfo.sn }" parentSn="${hxInfo.sn }"><h4><font color="green">${hxInfo.name }</font></h4></a>
						</td>
						<td><h4><font color="green">${hxInfo.xmlDetailName }</font></h4></td>
						<td>
							<a class="btn" id="addHxChildBtn_${hxInfo.sn }" parentSn="${hxInfo.sn }"><h4><font color="green">添加房间</font></h4></a>
							<a class="btn" id="modifyHxBtn_2_${hxInfo.sn }" parentSn="${hxInfo.sn }"><h4><font color="green">修改户型</font></h4></a>
							<a class="btn" id="deleteHxBtn_${hxInfo.sn }" hxSn="${hxInfo.sn }"><h4><font color="green">删除户型</font></h4></a>
						</td>
					</tr>
					<c:if test="${fn:length(hxInfo.children) > 0 }">
						<tr id="tr_${hxInfo.sn }">
							<td>
								<table class="table">
									<thead>
										<tr>
											<td>名称</td>
											<td>关联的xml属性</td>
										</tr>
									</thead>
									<c:forEach var="hxChild" items="${hxInfo.children}">
											<tr>
												<td>&nbsp;&nbsp;&nbsp;<a class="btn" id="modifyHxChildBtn_${hxChild.sn }" hxChildSn="${hxChild.sn }">${hxChild.name }</a></td>

												<td>
												${hxChild.xmlDetailName }&nbsp;
												<a class="btn" id="modifyHxChildBtn_2_${hxChild.sn }" hxChildSn="${hxChild.sn }">修改</a>
												<a class="btn" id="deleteHxChildBtn_${hxChild.sn }" hxChildSn="${hxChild.sn }">删除</a>
												</td>
											</tr>
									</c:forEach>
								</table>
							</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</c:if>
				</c:forEach>

			</tbody>
		</table>
		<!-- 根节点 -->
		<form action="/fs/hx/add" method="post">
			<input type="hidden" name="xmlInfoSn" value="${xmlInfoSn }"/>
			<input type="hidden" id="unityFileVersion" name="unityFileVersion"/>
			<input type="hidden" id="unityIosFileVersion" name="unityIosFileVersion"/>
			<input type="hidden" id="sn" name="sn"/>
			<div class="modal fade" id="hxModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			   <div class="modal-dialog">
			      <div class="modal-content" style="width:800px;">
			         <div class="modal-header">
			            <button type="button" class="close"
			               data-dismiss="modal" aria-hidden="true">
			                  &times;
			            </button>
			            <h4 class="modal-title" id="myModalLabel">
			             	添加
			            </h4>
			         </div>
			         <div class="modal-body">
			         	<div class="from-group">
			         		<label for="name">名称：</label>
			           	  	<input type="text" class="form-control" id="name" name="name" maxlength="20" placeholder="请输入名称"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="xmlId">关联xml属性：</label>
				           	<select class="form-control" id="xmlId" name="xmlId">
				           		<c:forEach var="xmlDetail" items="${xmlDetailList}">
				           			<option value="${xmlDetail.sn }">${xmlDetail.name }</option>
				           		</c:forEach>
				           	</select>
			         	</div>
			         	<div class="from-group">
			         		<label for="name">户型编号：</label>
			           	  	<input type="text" class="form-control" id="hxCode" name="hxCode" maxlength="20" placeholder="请输入户型编号"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="name">楼盘名称：</label>
			           	  	<input type="text" class="form-control" id="buildingName" name="buildingName" maxlength="20" placeholder="请输入楼盘名称"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="name">均价：</label>
			           	  	<input type="text" class="form-control" id="hxPrice" name="hxPrice" maxlength="10" placeholder="请输入均价"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="name">面积：</label>
			           	  	<input type="text" class="form-control" id="hxSize" name="hxSize" maxlength="10" placeholder="请输入面积"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="name">地址：</label>
			           	  	<input type="text" class="form-control" id="hxAddress" name="hxAddress" maxlength="150" placeholder="请输入地址"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="imageFileSn">图片：</label>
			         		<input type="hidden" id="picFileSn" name="picFileSn"/>
			         		<img id="picFileSn_img" src="" width="90" height="160" />
							<p class="mar_t20 mar_b20">
							<input id="file_upload1" name="file_upload1" type="file"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="unityFileSn">unity文件（android）：</label>
			         		<input type="hidden" id="unityFileSn" name="unityFileSn"/>
			         		<span id="unityFileSn_namedisplay"></span>
							<p class="mar_t20 mar_b20">
							<input id="file_upload2" name="file_upload2" type="file"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="unityFileSn">unity文件（ios）：</label>
			         		<input type="hidden" id="unityIosFileSn" name="unityIosFileSn"/>
			         		<span id="unityIosFileSn_namedisplay"></span>
							<p class="mar_t20 mar_b20">
							<input id="file_upload3" name="file_upload3" type="file"/>
			         	</div>
			         	<div class="from-group">
							<label for="summary">描述：</label>
							<textarea id="summary" name="summary" class="form-control" row="3"></textarea>
			         	</div>
			         </div>
			         <div class="modal-footer">
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="submit" class="btn btn-primary">
			               		提交更改
			            </button>
			         </div>
			      </div>
				</div>
		   </div>
		</form>
		<!-- 子节点 -->
		<form action="/fs/hx/add" method="post">
			<input type="hidden" name="xmlInfoSn" value="${xmlInfoSn }"/>
			<input type="hidden" id="childSn" name="sn"/>
			<input type="hidden" id="parentSn" name="parentSn"/>
			<div class="modal fade" id="hxChildModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			   <div class="modal-dialog">
			      <div class="modal-content" style="width:800px;">
			         <div class="modal-header">
			            <button type="button" class="close"
			               data-dismiss="modal" aria-hidden="true">
			                  &times;
			            </button>
			            <h4 class="modal-title" id="myModalLabel">
			             	添加
			            </h4>
			         </div>
			         <div class="modal-body">
			         	<div class="from-group">
			         		<label for="name">名称：</label>
			           	  	<input type="text" class="form-control" id="childName" name="name" maxlength="20" placeholder="请输入名称"/>
			         	</div>
			         	<div class="from-group">
			         		<label for="xmlId">关联xml属性：</label>
				           	<select class="form-control" id="childXmlId" name="xmlId">
				           		<c:forEach var="xmlDetail" items="${xmlDetailList}">
				           			<option value="${xmlDetail.sn }">${xmlDetail.name }</option>
				           		</c:forEach>
				           	</select>
			         	</div>
			         	<div class="from-group">
							<label for="summary">描述：</label>
							<textarea id="childSummary" name="summary" class="form-control" row="3"></textarea>
			         	</div>
			         </div>
			         <div class="modal-footer">
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="submit" class="btn btn-primary">
			               		提交更改
			            </button>
			         </div>
			      </div>
				</div>
		   </div>
		</form>

	</div>
</body>

</html>
