<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知心姐姐管理</title>
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
		<li class="active"><a href="${ctx}/edu/sister/">知心姐姐列表</a></li>
		<shiro:hasPermission name="edu:sister:edit"><li><a href="${ctx}/edu/sister/form">知心姐姐添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sister" action="${ctx}/edu/sister/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>名称</th><th>性别</th><th>简介</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:sister:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="sister">
			<tr>
				<td>
					<c:if test="${not empty sister.img}">
						<img src="${ctxImg}/${sister.img}" width="30px" height="30px"/>
					</c:if>
				</td>
				<td><a href="${ctx}/edu/sister/form?id=${sister.id}">${sister.name}</a></td>
				<td>
					<c:choose>
						<c:when test="${sister.gendar==1}">女</c:when>
						<c:otherwise>
							男
						</c:otherwise>
					</c:choose>
				</td>
				<td>${sister.summary}</td>
                <td>
                    <c:choose>
                        <c:when test="${sister.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${sister.remarks}</td>
				<shiro:hasPermission name="edu:sister:edit"><td>
					<a href="${ctx}//edu/sisterQuestion?sister.id=${sister.id}">查看留言</a>
                    <c:choose>
                        <c:when test="${sister.status==0}" >
                            <a href="${ctx}/edu/sister/publish?id=${sister.id}&status=1" onclick="return confirmx('确认要发布该知心姐姐吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/sister/publish?id=${sister.id}&status=0" onclick="return confirmx('确认要取消发布该知心姐姐吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/sister/form?id=${sister.id}">修改</a>
					<a href="${ctx}/edu/sister/delete?id=${sister.id}" onclick="return confirmx('确认要删除该知心姐姐吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
