<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海外之家报名管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/abroadEnroll/">海外之家报名列表</a></li>
		<li class="active"><a href="${ctx}/edu/abroadEnroll/form?id=${abroadEnroll.id}">海外之家报名<shiro:hasPermission name="edu:abroadEnroll:edit">${not empty abroadEnroll.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:abroadEnroll:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="abroadEnroll" action="${ctx}/edu/abroadEnroll/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="euser.id">用户:</label>
			<div class="controls">
				<select name="euser.id">
					<c:forEach items="${users}" var="user">
						<option value="${user.id}">${user.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="abroadhome.id">海外之家条目:</label>
			<div class="controls">
				<select name="abroadhome.id">
					<c:forEach items="${abroads}" var="abroad">
						<option value="${abroad.id}">${abroad.title}</option>
					</c:forEach>
				</select>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label" for="status">状态:</label>
            <div class="controls">
                <form:radiobutton path="status" value="0" checked="checked"   />未读
                <form:radiobutton path="status" value="1" />已读
            </div>
        </div>

		<div class="form-actions">
			<shiro:hasPermission name="edu:abroadEnroll:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
