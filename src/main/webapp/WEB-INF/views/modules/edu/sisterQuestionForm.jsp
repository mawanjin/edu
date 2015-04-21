<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>留言咨询管理</title>
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
		<li><a href="${ctx}/edu/sisterQuestion/">留言咨询列表</a></li>
		<li class="active"><a href="${ctx}/edu/sisterQuestion/form?id=${sisterQuestion.id}">留言咨询<shiro:hasPermission name="edu:sisterQuestion:edit">${not empty sisterQuestion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:sisterQuestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="sisterQuestion" action="${ctx}/edu/sisterQuestion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>


		<div class="control-group">
			<label class="control-label" for="user.id">用户:</label>
			<div class="controls">
				<select name="user.id">
					<c:forEach items="${users}" var="user">
						<option value="${user.id}">${user.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="sister.id">用户:</label>
			<div class="controls">
				<select name="sister.id">
					<c:forEach items="${sisters}" var="sister">
						<option value="${sister.id}">${sister.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="title">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="content">内容:</label>
			<div class="controls">
				<form:input path="content" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="reply">回复内容:</label>
			<div class="controls">
				<form:input path="reply" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>



        <div class="control-group">
            <label class="control-label" for="status">状态:</label>
            <div class="controls">
                <form:radiobutton path="status" value="0" checked="checked"   />已回复
                <form:radiobutton path="status" value="1" />未回复
            </div>
        </div>

		<div class="control-group">
			<label class="control-label" for="remarks">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:sisterQuestion:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
