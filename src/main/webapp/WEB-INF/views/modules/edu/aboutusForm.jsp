<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关于我们管理</title>
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
		<li><a href="${ctx}/edu/aboutus/">关于我们列表</a></li>
		<li class="active"><a href="${ctx}/edu/aboutus/form?id=${aboutus.id}">关于我们<shiro:hasPermission name="edu:aboutus:edit">${not empty aboutus.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:aboutus:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="aboutus" action="${ctx}/edu/aboutus/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="content">内容:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/edu/aboutus" />
			</div>
		</div>

        <div class="control-group">
            <label class="control-label" for="status">状态:</label>
            <div class="controls">
                <form:radiobutton path="status" value="0" checked="checked"   />未发布
                <form:radiobutton path="status" value="1" />已发布
            </div>
        </div>

		<div class="control-group">
			<label class="control-label" for="remarks">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:aboutus:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
