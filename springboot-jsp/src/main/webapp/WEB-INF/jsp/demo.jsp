<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 导入标签库 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    request.setAttribute("path", path);
    request.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>SpringBoot JSP</title>
</head>
<link type="text/css" rel="stylesheet" href="${path}/resources/css/demo.css">
<body>

Hello World!

<br>请求上下文路径：${path}
<c:if test="${empty path}">
    <label style="color: #929292">空</label>
</c:if>
<br>请求基本路径：${basePath}

<div id="demo">0</div>

</body>
<script type="text/javascript" src="${path}/resources/lib/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${path}/resources/js/demo.js"></script>
</html>
