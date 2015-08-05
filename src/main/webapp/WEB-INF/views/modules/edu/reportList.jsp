<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日常报告管理</title>
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
		<li class="active"><a href="${ctx}/edu/report/">日常报告列表</a></li>
		<shiro:hasPermission name="edu:report:edit"><li><a href="${ctx}/edu/report/form">日常报告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="report" action="${ctx}/edu/report/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<label>类型：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="type" items="${fns:getDictList('edu_report_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />


	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>类型</th><th>接受方</th><th>状态</th><th>内容</th><shiro:hasPermission name="edu:report:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="report">
			<tr>
				<td><a href="${ctx}/edu/report/form?id=${report.id}">${report.title}</a></td>
				<td>
					<c:choose>
						<c:when test="${report.type eq 0}">周报告</c:when>
						<c:when test="${report.type eq 1}">月报告</c:when>
						<c:when test="${report.type eq 2}">季报告</c:when>
						<c:when test="${report.type eq 3}">年报告</c:when>
					</c:choose>
				</td>
                <td>${report.euser.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${report.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${fn:substring(report.content, 0, 15)}</td>
				<shiro:hasPermission name="edu:report:edit"><td>
                    <c:choose>
                        <c:when test="${report.status==0}" >
                            <a href="${ctx}/edu/report/publish?id=${report.id}&status=1" onclick="return confirmx('确认要发布该日常报告吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/report/publish?id=${report.id}&status=0" onclick="return confirmx('确认要取消发布该日常报告吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/report/form?id=${report.id}">修改</a>
					<a href="${ctx}/edu/report/delete?id=${report.id}" onclick="return confirmx('确认要删除该日常报告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
