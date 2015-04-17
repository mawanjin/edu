<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户关系管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li s><a href="${ctx}/edu/euser/">用户信息表</a></li>
		<li class="active"><a href="${ctx}/edu/userRelation/?eduUserByParentId.id=${user.id}">用户关系列表</a></li>
		<shiro:hasPermission name="edu:userRelation:edit"><li><a href="${ctx}/edu/userRelation/form?eduUserByParentId.id=${uid}">用户关系添加</a></li></shiro:hasPermission>
	</ul>
	${user.name}
	<form:form id="searchForm" modelAttribute="userRelation" action="${ctx}/edu/userRelation/?eduUserByParentId.id=${user.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>姓名 ：</label><form:input path="eduUserById.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><shiro:hasPermission name="edu:userRelation:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="userRelation">
			<tr>
				<td>${userRelation.eduUserById.name}</td>

				<shiro:hasPermission name="edu:userRelation:edit"><td>
					<a href="${ctx}/edu/userRelation/delete?id=${userRelation.id}&eduUserByParentId.id=${user.id}" onclick="return confirmx('确认要删除该用户关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
