<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>国家管理</title>
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
		<li class="active"><a href="${ctx}/edu/country/">国家列表</a></li>
		<shiro:hasPermission name="edu:country:edit"><li><a href="${ctx}/edu/country/form">国家添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="country" action="${ctx}/edu/country/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>名称</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:country:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="country">
			<tr>
				<td><img src="${ctxImg}/${country.img}" width="30px" height="30px"/> </td>
				<td><a href="${ctx}/edu/country/form?id=${country.id}">${country.name}</a></td>
				<td>
					<c:choose>
						<c:when test="${country.hot eq '0'}">热门</c:when>
						<c:when test="${country.hot eq '1'}">其他</c:when>
					</c:choose>
				</td>
				<td>${country.remarks}</td>
				<shiro:hasPermission name="edu:country:edit"><td>
    				<a href="${ctx}/edu/country/form?id=${country.id}">修改</a>
					<a href="${ctx}/edu/country/delete?id=${country.id}" onclick="return confirmx('确认要删除该国家吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
