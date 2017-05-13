<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<link rel="stylesheet" type="text/css" href="/js/daterangepicker/daterangepicker-bs3.css"/>
	<link rel="stylesheet" type="text/css" href="/js/fancyBox/source/jquery.fancybox.css"/>
	<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
</head>

<body>
	<input type="hidden" id="tab_index" value="4"/>
	<%@include file="../workspace/navtop.jsp" %>

 	<div class="panel panel-info">
      <div class="panel-heading">
         <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion"
               href="#collapseThree">
               		意见反馈
            </a>
         </h4>
      </div>
      <div id="collapseThree" class="panel-collapse collapse">
         <div id="container"  class="panel-body">
         	<div class="table-responsive">
   <table class="table">
      <caption>意见反馈</caption>
      <thead>
         <tr>
            <th>序号</th>
            <th>反馈内容</th>
            <th>时间</th>
         </tr>
      </thead>
      <tbody>
      	<c:forEach var="itm" items="${list }" varStatus="status">
         <tr>
            <td>${status.index + 1 }</td>
            <td>${itm.content }</td>
            <td>${itm.addTime }</td>
         </tr>
      	</c:forEach>
      </tbody>
   </table>
</div>
         </div>
      </div>
   </div>


<script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(function () { $('#collapseThree').collapse('toggle')});
	});
</script>

</body>

</html>