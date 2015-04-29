<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询回复管理</title>
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
		<li class="active"><a href="${ctx}/edu/question/">咨询回复列表</a></li>
		<shiro:hasPermission name="edu:question:edit"><li><a href="${ctx}/edu/question/form">咨询回复添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="question" action="${ctx}/edu/question/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>留言者</th><th>内容</th><th>回复</th><shiro:hasPermission name="edu:question:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="question">
			<tr>
				<td><a href="${ctx}/edu/question/form?id=${question.id}">${question.title}</a></td>
				<td>${question.euser.name}</td>
                <td>
						${question.msg}
                </td>
				<td>${fn:substring(question.reply, 0, 16) }</td>
				<shiro:hasPermission name="edu:question:edit"><td>
    				<a href="${ctx}/edu/question/form?id=${question.id}">修改</a>
					<a href="${ctx}/edu/question/delete?id=${question.id}" onclick="return confirmx('确认要删除该咨询回复吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
