<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校管理</title>
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
		<li class="active"><a href="${ctx}/edu/school/">学校列表</a></li>
		<shiro:hasPermission name="edu:school:edit"><li><a href="${ctx}/edu/school/form">学校添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="school" action="${ctx}/edu/school/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>校徽</th><th>名称</th><th>简介</th><th>类型</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:school:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="school">
			<tr>
				<td>
					<c:if test="${not empty school.img}">
						<img src="${ctxImg}/${school.img}" width="30px" height="30px"/>
					</c:if>
				</td>

				<td>
					<c:if test="${not empty school.badge}">
						<img src="${ctxImg}/${school.badge}" width="30px" height="30px"/>
					</c:if>
				</td>
				<td><a href="${ctx}/edu/school/form?id=${school.id}">${school.name}</a></td>
				<td>${school.summary}</td>
				<td>
					<c:choose>
						<c:when test="${school.grade eq '0'}">中小学</c:when>
						<c:when test="${school.grade eq '1'}">大学</c:when>
					</c:choose>
				</td>
                <td>
                    <c:choose>
                        <c:when test="${school.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${school.remarks}</td>
				<shiro:hasPermission name="edu:school:edit"><td>
                    <c:choose>
                        <c:when test="${school.status==0}" >
                            <a href="${ctx}/edu/school/publish?id=${school.id}&status=1" onclick="return confirmx('确认要发布该学校吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/school/publish?id=${school.id}&status=0" onclick="return confirmx('确认要取消发布该学校吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/school/form?id=${school.id}">修改</a>
					<a href="${ctx}/edu/school/delete?id=${school.id}" onclick="return confirmx('确认要删除该学校吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
