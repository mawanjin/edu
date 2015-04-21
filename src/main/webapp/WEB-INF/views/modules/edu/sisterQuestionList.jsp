<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>留言咨询管理</title>
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
		<li class="active"><a href="${ctx}/edu/sisterQuestion/">留言咨询列表</a></li>
		<shiro:hasPermission name="edu:sisterQuestion:edit"><li><a href="${ctx}/edu/sisterQuestion/form">留言咨询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sisterQuestion" action="${ctx}/edu/sisterQuestion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>用户</th><th>标题</th><th>状态</th><th>详情</th><shiro:hasPermission name="edu:sisterQuestion:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="sisterQuestion">
			<tr>
				<td>${sisterQuestion.user.name}</td>
				<td><a href="${ctx}/edu/sisterQuestion/form?id=${sisterQuestion.id}">${sisterQuestion.title}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${sisterQuestion.status==1}">已回复</c:when>
                        <c:otherwise>
                            未回复
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${fn:substring(sister.content, 0, 15)}</td>
				<shiro:hasPermission name="edu:sisterQuestion:edit"><td>
                    <c:choose>
                        <c:when test="${sisterQuestion.status==0}" >
                            <a href="${ctx}/edu/sisterQuestion/publish?id=${sisterQuestion.id}&status=1" onclick="return confirmx('确认要发布该留言咨询吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/sisterQuestion/publish?id=${sisterQuestion.id}&status=0" onclick="return confirmx('确认要取消发布该留言咨询吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/sisterQuestion/form?id=${sisterQuestion.id}">修改</a>
					<a href="${ctx}/edu/sisterQuestion/delete?id=${sisterQuestion.id}" onclick="return confirmx('确认要删除该留言咨询吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
