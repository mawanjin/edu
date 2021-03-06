<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人问答回复管理</title>
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
		<li><a href="${ctx}/edu/personalQuestion/">个人问答</a></li>
		<li><a href="${ctx}/edu/personalQuestionReply?personalQuestion.id=${personalQuestionReply.personalQuestion.id}">个人问答回复列表</a></li>
		<li class="active"><a href="${ctx}/edu/personalQuestionReply/form?personalQuestion.id=${personalQuestionReply.personalQuestion.id}">个人问答回复<shiro:hasPermission name="edu:personalQuestionReply:edit">${not empty personalQuestionReply.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:personalQuestionReply:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="personalQuestionReply" action="${ctx}/edu/personalQuestionReply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="personalQuestion.id" value="${personalQuestionReply.personalQuestion.id}"/>

		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="euser.id">回复人:</label>
			<div class="controls">
				<select name="euser.id">
				<c:forEach items="${users}" var="user">
					<option value="${user.id}">${user.name}</option>
				</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="content">内容:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:personalQuestionReply:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
