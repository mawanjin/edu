<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
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
		<li><a href="${ctx}/edu/suggestion/">意见反馈列表</a></li>
		<li class="active"><a href="${ctx}/edu/suggestion/form?id=${suggestion.id}">意见反馈<shiro:hasPermission name="edu:suggestion:edit">${not empty suggestion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:suggestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="suggestion" action="${ctx}/edu/suggestion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="msg">内容:</label>
			<div class="controls">
				<form:input path="msg" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:suggestion:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
