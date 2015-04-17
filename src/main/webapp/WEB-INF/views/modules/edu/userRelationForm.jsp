<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户关系管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/userRelation/?eduUserByParentId.id=${user.id}">用户关系列表</a></li>
		<li class="active"><a href="${ctx}/edu/userRelation/form?id=${userRelation.id}">用户关系<shiro:hasPermission name="edu:userRelation:edit">${not empty userRelation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:userRelation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>

	<form:form id="inputForm" modelAttribute="userRelation" action="${ctx}/edu/userRelation/save" method="post" class="form-horizontal">

		<input type="hidden" name="pid" value="${user.id}" />
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" >家长:</label>
			<div class="controls">
				<input type="text" value="${user.name}" disabled  />
			</div>
		</div>
	</form:form>
	选择学生:
	<form:form id="searchForm" modelAttribute="euser"  action="${ctx}/edu/userRelation/form/filter" method="post" class="breadcrumb form-search">

		<input name="eduUserByParentId.id" type="hidden" value="${user.id}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>类型</th><th>昵称</th><th>登录名</th><th>备注</th><shiro:hasPermission name="edu:euser:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="euser">
			<tr>
				<td>${euser.name}</td>
				<td>
					<c:choose>
						<c:when test="${euser.type==0}">学生</c:when>
						<c:when test="${euser.type==1}">家长</c:when>
						<c:otherwise>
							管理员
						</c:otherwise>
					</c:choose>
				</td>
				<td>${euser.nickName}</td>
				<td>${euser.loginName}</td>
				<td>${euser.remarks}</td>
				<td><a href="${ctx}/edu/userRelation/save?eduUserByParentId.id=${user.id}&eduUserById.id=${euser.id}">关联关系</a> </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
