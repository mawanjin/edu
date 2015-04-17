<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>重要提示管理</title>
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
		<li><a href="${ctx}/edu/emergency/">重要提示列表</a></li>
		<li class="active"><a href="${ctx}/edu/emergency/form?id=${emergency.id}">重要提示<shiro:hasPermission name="edu:emergency:edit">${not empty emergency.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:emergency:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="emergency" action="${ctx}/edu/emergency/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title">名称:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label" for="status">类型:</label>
            <div class="controls">
                <form:radiobutton path="type" value="0" checked="checked"   />重要
                <form:radiobutton path="type" value="1" />系统
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

		<div class="control-group">
			<label class="control-label" for="content">详情:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/edu/emergency" />
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="edu:emergency:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
