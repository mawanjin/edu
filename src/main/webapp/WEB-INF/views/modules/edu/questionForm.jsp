<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询回复管理</title>
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
		<li><a href="${ctx}/edu/question/">咨询回复列表</a></li>
		<li class="active"><a href="${ctx}/edu/question/form?id=${question.id}">咨询回复<shiro:hasPermission name="edu:question:edit">${not empty question.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:question:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="question" action="${ctx}/edu/question/save" method="post" class="form-horizontal">
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

		<div class="control-group">
			<label class="control-label" for="euser.id">留言者:</label>
			<div class="controls">
				<select name="euser.id">
					<c:forEach items="${users}" var="user">
						<option value="${user.id}" <c:if test="${user.id eq question.euser.id}" >selected="selected" </c:if>>${user.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="content">回复:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="reply" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="reply" uploadPath="/edu/question" />
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="edu:question:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
