<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>私人定制留言管理</title>
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
		<li class="active"><a href="${ctx}/edu/customizationMessage/">私人定制留言列表</a></li>
		<shiro:hasPermission name="edu:customizationMessage:edit"><li><a href="${ctx}/edu/customizationMessage/form">私人定制留言添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customizationMessage" action="${ctx}/edu/customizationMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>留言 ：</label><form:input path="msg" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>用户</th><th>留言</th><th>时间</th><shiro:hasPermission name="edu:customizationMessage:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="customizationMessage">
			<tr>
				<td>${customizationMessage.user.name}</td>
				<td>${customizationMessage.msg}</td>
				<td>${customizationMessage.createDate}</td>
				<shiro:hasPermission name="edu:customizationMessage:edit"><td>
                    <c:choose>
                        <c:when test="${customizationMessage.status==0}" >
                            <a href="${ctx}/edu/customizationMessage/publish?id=${customizationMessage.id}&status=1" onclick="return confirmx('确认要发布该私人定制留言吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/customizationMessage/publish?id=${customizationMessage.id}&status=0" onclick="return confirmx('确认要取消发布该私人定制留言吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/customizationMessage/form?id=${customizationMessage.id}">修改</a>
					<a href="${ctx}/edu/customizationMessage/delete?id=${customizationMessage.id}" onclick="return confirmx('确认要删除该私人定制留言吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
