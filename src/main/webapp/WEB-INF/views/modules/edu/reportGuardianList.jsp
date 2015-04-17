<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监护人报告管理</title>
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
		<li><a href="${ctx}/edu/euser">用户列表</a></li>
		<li class="active"><a href="${ctx}/edu/reportGuardian?user.id=${uid}">监护人报告列表</a></li>
		<shiro:hasPermission name="edu:reportGuardian:edit"><li><a href="${ctx}/edu/reportGuardian/form?user.id=${uid}">监护人报告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reportGuardian" action="${ctx}/edu/reportGuardian/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>状态</th><th>内容</th><shiro:hasPermission name="edu:reportGuardian:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportGuardian">
			<tr>
				<td><a href="${ctx}/edu/reportGuardian/form?id=${reportGuardian.id}">${reportGuardian.title}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${reportGuardian.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${fn:substring(reportGuardian.content, 0, 15)}</td>
				<shiro:hasPermission name="edu:reportGuardian:edit"><td>
                    <c:choose>
                        <c:when test="${reportGuardian.status==0}" >
                            <a href="${ctx}/edu/reportGuardian/publish?id=${reportGuardian.id}&status=1" onclick="return confirmx('确认要发布该监护人报告吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/reportGuardian/publish?id=${reportGuardian.id}&status=0" onclick="return confirmx('确认要取消发布该监护人报告吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/reportGuardian/form?id=${reportGuardian.id}">修改</a>
					<a href="${ctx}/edu/reportGuardian/delete?id=${reportGuardian.id}&user.id=${uid}" onclick="return confirmx('确认要删除该监护人报告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
