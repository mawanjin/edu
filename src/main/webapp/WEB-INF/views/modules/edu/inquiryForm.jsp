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
		<li><a href="${ctx}/edu/inquiry/">留言咨询列表</a></li>
		<li class="active"><a href="${ctx}/edu/inquiry/form?id=${inquiry.id}">留言咨询<shiro:hasPermission name="edu:inquiry:edit">${not empty inquiry.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:inquiry:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="inquiry" action="${ctx}/edu/inquiry/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="type">类型:</label>
			<div class="controls">
				<select name="type">



					<option value="0" <c:if test="${inquiry.type eq '0'}">selected</c:if> >生活便利</option>
					<option value="1" <c:if test="${inquiry.type eq '1'}">selected</c:if>>主题活动</option>
					<option value="2" <c:if test="${inquiry.type eq '2'}">selected</c:if>>海外之家</option>
					<option value="3" <c:if test="${inquiry.type eq '3'}">selected</c:if>>GPS导航</option>
				</select>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label" for="euser.id">留言者:</label>
			<div class="controls">
				<select name="euser.id">
				<c:forEach items="${users}" var="user">
					<option value="${user.id}">${user.name}</option>
				</c:forEach>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="content">详情:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="viewCount">查看次数:</label>
			<div class="controls">
				<form:input path="viewCount" htmlEscape="false" maxlength="200" class="required number" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:inquiry:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
