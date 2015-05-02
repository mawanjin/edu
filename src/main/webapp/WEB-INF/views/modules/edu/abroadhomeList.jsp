<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海外之家管理</title>
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
		<li class="active"><a href="${ctx}/edu/abroadhome/">海外之家列表</a></li>
		<shiro:hasPermission name="edu:abroadhome:edit"><li><a href="${ctx}/edu/abroadhome/form">海外之家添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="abroadhome" action="${ctx}/edu/abroadhome/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>标题</th><th>简介</th><th>状态</th><th>是否可报名</th><th>备注</th><shiro:hasPermission name="edu:abroadhome:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="abroadhome">
			<tr>
				<td><c:if test="${not empty abroadhome.img}">
					<img src="${ctxImg}/${abroadhome.img}" width="30px" height="30px"/> </c:if></td>

				<td><a href="${ctx}/edu/abroadhome/form?id=${abroadhome.id}">${abroadhome.title}</a></td>
				<td>${abroadhome.summary}</td>
                <td>
                    <c:choose>
                        <c:when test="${abroadhome.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>

				<td>
                    <c:choose>
                        <c:when test="${abroadhome.enrollable==0}">不可报名</c:when>
                        <c:otherwise>
                            可报名
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${abroadhome.remarks}</td>
				<shiro:hasPermission name="edu:abroadhome:edit"><td>
                    <c:choose>
                        <c:when test="${abroadhome.status==0}" >
                            <a href="${ctx}/edu/abroadhome/publish?id=${abroadhome.id}&status=1" onclick="return confirmx('确认要发布该海外之家吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/abroadhome/publish?id=${abroadhome.id}&status=0" onclick="return confirmx('确认要取消发布该海外之家吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/abroadhome/form?id=${abroadhome.id}">修改</a>
					<a href="${ctx}/edu/abroadhome/delete?id=${abroadhome.id}" onclick="return confirmx('确认要删除该海外之家吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
