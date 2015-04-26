<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校新闻管理</title>
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
		<li class="active"><a href="${ctx}/edu/schoolNews/">学校新闻列表</a></li>
		<shiro:hasPermission name="edu:schoolNews:edit"><li><a href="${ctx}/edu/schoolNews/form">学校新闻添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolNews" action="${ctx}/edu/schoolNews/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>标题</th><th>学校</th><th>简介</th><th>置顶</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:schoolNews:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolNews">
			<tr>
				<td><img src="${ctxImg}/${schoolNews.img}" width="30px" height="30px"/> </td>
				<td><a href="${ctx}/edu/schoolNews/form?id=${schoolNews.id}">${schoolNews.title}</a></td>
				<td>${schoolNews.school.name}</td>
				<td>${schoolNews.summary}</td>
				<td>
					<c:choose >
						<c:when test="${schoolNews.top eq 0}">未置顶</c:when>
						<c:when test="${schoolNews.top eq 1}">置顶</c:when>
					</c:choose>
				</td>
                <td>
                    <c:choose>
                        <c:when test="${schoolNews.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${schoolNews.remarks}</td>
				<shiro:hasPermission name="edu:schoolNews:edit"><td>
                    <c:choose>
                        <c:when test="${schoolNews.status==0}" >
                            <a href="${ctx}/edu/schoolNews/publish?id=${schoolNews.id}&status=1" onclick="return confirmx('确认要发布该学校新闻吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/schoolNews/publish?id=${schoolNews.id}&status=0" onclick="return confirmx('确认要取消发布该学校新闻吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/schoolNews/form?id=${schoolNews.id}">修改</a>
					<a href="${ctx}/edu/schoolNews/delete?id=${schoolNews.id}" onclick="return confirmx('确认要删除该学校新闻吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
