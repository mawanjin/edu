<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生活便利管理</title>
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
		<li class="active"><a href="${ctx}/edu/convenience/">生活便利列表</a></li>
		<shiro:hasPermission name="edu:convenience:edit"><li><a href="${ctx}/edu/convenience/form">生活便利添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="convenience" action="${ctx}/edu/convenience/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>标题</th><th>简介</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:convenience:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="convenience">
			<tr>
				<td><c:if test="${not empty convenience.img}">
					<img src="${ctxImg}/${convenience.img}" width="30px" height="30px"/> </c:if></td>
				<td><a href="${ctx}/edu/convenience/form?id=${convenience.id}">${convenience.title}</a></td>
				<td>${convenience.summary}</td>

				<td>
					<c:choose>
						<c:when test="${convenience.status==1}">已发布</c:when>
						<c:otherwise>
							未发布
						</c:otherwise>
					</c:choose>
				</td>

				<td>${convenience.remarks}</td>
				<shiro:hasPermission name="edu:convenience:edit"><td>
                    <c:choose>
                        <c:when test="${convenience.status==0}" >
                            <a href="${ctx}/edu/convenience/publish?id=${convenience.id}&status=1" onclick="return confirmx('确认要发布该生活便利吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/convenience/publish?id=${convenience.id}&status=0" onclick="return confirmx('确认要取消发布该生活便利吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/convenience/form?id=${convenience.id}">修改</a>
					<a href="${ctx}/edu/convenience/delete?id=${convenience.id}" onclick="return confirmx('确认要删除该生活便利吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
