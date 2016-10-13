<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title></title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>

</head>
<body>
<div class="container text-center" >
	<div class="page-header">
	   <h1>
	      <small>App Download</small>
	   </h1>
	</div>
	<p></p>
	<c:forEach var="app" items="${list }">
		<div style="float: left;width: 48%;">
			<div style="float: left;width: 100%;">
				<a href="${app.qrCodeContent }">
					
			 <img class="img-responsive center-block img-rounded" src="${app.appIconFileUrl }" width="80%;" height="80%;">
				</a>
			</div>
			<div style="float: left;width: 100%;">
				<div style="float: left;width: 100%;">
					<p style="font-size: 20px;">${app.name }</p>
				
				</div>
				<div style="float: left;width: 95%;word-break: break-all;word-wrap: break-word;">
				<p >${app.remark }</p>
				</div>
			
			</div>
		</div>
		<div style="float: left;width: 4%;"></div>
	</c:forEach>
</div>
</body>