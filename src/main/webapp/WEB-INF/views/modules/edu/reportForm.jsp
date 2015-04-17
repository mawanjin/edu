<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日常报告管理</title>
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
		<li><a href="${ctx}/edu/report/">日常报告列表</a></li>
		<li class="active"><a href="${ctx}/edu/report/form?id=${report.id}">日常报告<shiro:hasPermission name="edu:report:edit">${not empty report.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:report:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="report" action="${ctx}/edu/report/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="title">类型:</label>
			<div class="controls">
				<select name="type">
					<option value="0">周报告</option>
					<option value="1">月报告</option>
					<option value="2">季报告</option>
					<option value="3">年报告</option>
				</select>
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
			<label class="control-label" for="content">内容:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/edu/report" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:report:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
