<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
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
		<li class="active"><a href="${ctx}/edu/suggestion/">意见反馈列表</a></li>
		<shiro:hasPermission name="edu:suggestion:edit"><li><a href="${ctx}/edu/suggestion/form">意见反馈添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="suggestion" action="${ctx}/edu/suggestion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>内容</th><th>时间</th><shiro:hasPermission name="edu:suggestion:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="suggestion">
			<tr>
				<td><a href="${ctx}/edu/suggestion/form?id=${suggestion.id}">${suggestion.title}</a></td>
				<td>${suggestion.msg}</td>
				<td>${suggestion.createDate}</td>
				<shiro:hasPermission name="edu:suggestion:edit"><td>
    				<a href="${ctx}/edu/suggestion/form?id=${suggestion.id}">修改</a>
					<a href="${ctx}/edu/suggestion/delete?id=${suggestion.id}" onclick="return confirmx('确认要删除该意见反馈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
