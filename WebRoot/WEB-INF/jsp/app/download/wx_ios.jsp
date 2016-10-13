<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>app下载</title>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <script type="text/javascript" src="/js/bootstrap.minn-3.3.5.js"></script>
</head>
<body >
<style type="text/css">
#popweixin {
    width:100%;
    height:100%;
    overflow:hidden;
    position:fixed;
    z-index:1000;
    background:rgba(0,0,0,.5);
    top:0;
    left:0;
}
#popweixin .tip {
    width:100%;
    background:#fff;
    z-index:1001;
}
#popweixin img {
    width:100%;
}
.top2bottom {
    -webkit-animation:top2bottom 1.2s ease;
    -moz-animation:top2bottom 1.2s ease;
    -o-animation:top2bottom 1.2s ease;
    animation:top2bottom 1.2s ease;
    -webkit-animation-fill-mode:backwards;
    -moz-animation-fill-mode:backwards;
    -o-animation-fill-mode:backwards;
    animation-fill-mode:backwards;
}
.animate-delay-1 {
    -webkit-animation-delay:1s;
    -moz-animation-delay:1s;
    -o-animation-delay:1s;
    animation-delay:1s
}
@-webkit-keyframes top2bottom {
    0% {
    -webkit-transform:translateY(-300px);
    opacity:.6
}
100% {
    -webkit-transform:translateY(0px);
    opacity:1
}
}@keyframes top2bottom {
    0% {
    transform:translateY(-300px);
    opacity:.6
}
100% {
    transform:translateY(0px);
    opacity:1
}

</style>

<div id="popweixin">
    <div class="tip top2bottom animate-delay-1">
        <img src="/images/wx_d_ios.jpg">
    </div>
</div>
</body>
</html>