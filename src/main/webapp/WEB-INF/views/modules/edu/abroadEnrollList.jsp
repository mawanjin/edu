<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海外之家报名管理</title>
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
		<li class="active"><a href="${ctx}/edu/abroadEnroll/">海外之家报名列表</a></li>
		<shiro:hasPermission name="edu:abroadEnroll:edit"><li><a href="${ctx}/edu/abroadEnroll/form">海外之家报名添加</a></li></shiro:hasPermission>
	</ul>
	<%--<form:form id="searchForm" modelAttribute="abroadEnroll" action="${ctx}/edu/abroadEnroll/" method="post" class="breadcrumb form-search">--%>
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
		<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<%--<label>名称 ：</label><form:input path="euser.name" htmlEscape="false" maxlength="50" class="input-small"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	<%--</form:form>--%>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>用户</th><th>标题</th><th>状态</th><th>日期</th><shiro:hasPermission name="edu:abroadEnroll:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="abroadEnroll">
			<tr>
				<td><a href="${ctx}/edu/abroadEnroll/form?id=${abroadEnroll.id}">${abroadEnroll.euser.name}</a></td>
				<td>
						${abroadEnroll.abroadhome.title}
				</td>
                <td>
                    <c:choose>
                        <c:when test="${abroadEnroll.status==1}">已读</c:when>
                        <c:otherwise>
                            未读
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${abroadEnroll.createDate}</td>
				<shiro:hasPermission name="edu:abroadEnroll:edit"><td>
    				<a href="${ctx}/edu/abroadEnroll/form?id=${abroadEnroll.id}">修改</a>
					<a href="${ctx}/edu/abroadEnroll/delete?id=${abroadEnroll.id}" onclick="return confirmx('确认要删除该海外之家报名吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
