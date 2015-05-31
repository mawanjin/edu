<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>名师管理</title>
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
		<li class="active"><a href="${ctx}/edu/teacher/">名师列表</a></li>
		<shiro:hasPermission name="edu:teacher:edit"><li><a href="${ctx}/edu/teacher/form">名师添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teacher" action="${ctx}/edu/teacher/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>名称</th><th>性别</th><th>专业</th><th>状态</th><shiro:hasPermission name="edu:teacher:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="teacher">
			<tr>
				<td>
					<c:if test="${not empty teacher.img}">
						<img src="${ctxImg}/${teacher.img}" width="30px" height="30px"/>
					</c:if>
				</td>
				<td><a href="${ctx}/edu/teacher/form?id=${teacher.id}">${teacher.name}</a></td>

				<td>
					<c:choose>
						<c:when test="${teacher.gendar==0}">男</c:when>
						<c:otherwise>
							女
						</c:otherwise>
					</c:choose>
				</td>
				<td>${teacher.major.name}</td>

                <td>
                    <c:choose>
                        <c:when test="${teacher.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>

				<shiro:hasPermission name="edu:teacher:edit"><td>
                    <c:choose>
                        <c:when test="${teacher.status==0}" >
                            <a href="${ctx}/edu/teacher/publish?id=${teacher.id}&status=1" onclick="return confirmx('确认要发布该名师吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/teacher/publish?id=${teacher.id}&status=0" onclick="return confirmx('确认要取消发布该名师吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/teacher/form?id=${teacher.id}">修改</a>
					<a href="${ctx}/edu/teacher/delete?id=${teacher.id}" onclick="return confirmx('确认要删除该名师吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
