<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>gps管理</title>
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
		<li class="active"><a href="${ctx}/edu/gps/">gps列表</a></li>
		<shiro:hasPermission name="edu:gps:edit"><li><a href="${ctx}/edu/gps/form">gps添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gps" action="${ctx}/edu/gps/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>位置 ：</label><form:input path="location" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>位置</th><th>时间</th><shiro:hasPermission name="edu:gps:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="gps">
			<tr>
				<td><a href="${ctx}/edu/gps/form?id=${gps.id}">${gps.user.name}</a></td>
				<td>${gps.location}</td>
				<td>${gps.createDate}</td>
				<shiro:hasPermission name="edu:gps:edit"><td>
    				<a href="${ctx}/edu/gps/form?id=${gps.id}">修改</a>
					<a href="${ctx}/edu/gps/delete?id=${gps.id}" onclick="return confirmx('确认要删除该gps吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
