<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>app下载</title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
</head>
<body style="background-color:#efefef;">
<input id="appUrl" type="hidden" value="${appUrl}"/>

<script>
	$(function(){
		$("img[id^='download_']").click(function(){
			window.location.href = document.getElementById("appUrl").value;
		});
	});
</script>
<div class="well well-lg">
Congratulation, you have successfully joined ravengo activity ! Click on the link to download.After downloading successfully, activated your AR tool in accordance with the following steps, let's go!
</div>
<div class="text-center">
	<img id="download_1" src="/images/download.png"/>
</div>
<h3 class="text-center">activated your AR tool</h3>
<img src="/images/prt/00.png" class="img-responsive " />
<div class="text-center" style="font-size:20px;">step1</div>
<img src="/images/prt/01.png" class="img-responsive " />
<div class="text-center" style="font-size:20px;">step2</div>
<img src="/images/prt/02.png" class="img-responsive" />
<div class="text-center" style="font-size:20px;">step3</div>
<img src="/images/prt/03.png" class="img-responsive" />
<div class="text-center" style="font-size:20px;">step4</div>
<img src="/images/prt/04.png" class="img-responsive" />
<div class="text-center" style="font-size:20px;">step5</div>
<img src="/images/prt/05.png" class="img-responsive" />
<div class="text-center" style="font-size:20px;">step6</div>
<div class="text-center">
	<img id="download_2" src="/images/download.png"/>
</div>
</body>
</html>