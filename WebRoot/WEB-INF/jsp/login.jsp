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

    <style>
		.form-signin{
			max-width:330px;
			padding:15px;
			margin:0 auto;
		}
		input{
			margin-bottom:10px;
		}
		body{background:url(/images/login_bj.jpg) top center no-repeat;}
		.w1{width:100px;}
		.mar_b20{ margin-bottom:20px;}
		.mar_t200{margin-top:220px;}
		@media (max-width: 767px) {
			.mar_t200_ts{margin-top:20px;}
			body{background:url(/images/login_bj_ts.jpg) top center no-repeat;}
		}
	</style>

</head>
<body>
<div class="container mar_t200 mar_t200_ts">

	<form class="form-signin" role="form" action="/fs/login" method="post">
			<h2 class="mar_b20" style="text-align: center;">欢迎登录</h2>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
					<input class="form-control" id="name" name="name" type="text" maxlength=11 placeholder="请输入手机号码"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
					<input class="form-control" id="password" name="password" type="password" maxlength=20 placeholder="请输入密码"/>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-sm-6 ">
						<button type="submit" class="btn btn-primary btn-lg w100">登录</button>
					</div>
				</div>
			</div>
			<font color="red">${message }</font>
	</form>

</div>
</body>