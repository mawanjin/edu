<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>gps管理</title>
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
		<li><a href="${ctx}/edu/gps/">gps列表</a></li>
		<li class="active"><a href="${ctx}/edu/gps/form?id=${gps.id}">gps<shiro:hasPermission name="edu:gps:edit">${not empty gps.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:gps:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="gps" action="${ctx}/edu/gps/save" method="post" class="form-horizontal">
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
			<label class="control-label" for="remarks">位置:</label>
			<div class="controls">
				<form:textarea path="location" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:gps:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
