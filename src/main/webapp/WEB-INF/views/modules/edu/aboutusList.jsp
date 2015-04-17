<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关于我们管理</title>
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
		<li class="active"><a href="${ctx}/edu/aboutus/">关于我们列表</a></li>
		<shiro:hasPermission name="edu:aboutus:edit"><li><a href="${ctx}/edu/aboutus/form">关于我们添加</a></li></shiro:hasPermission>
	</ul>

	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>内容</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:aboutus:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="aboutus">
			<tr>
				<td><a href="${ctx}/edu/aboutus/form?id=${aboutus.id}">${fn:substring(aboutus.content, 0, 15)}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${aboutus.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${aboutus.remarks}</td>
				<shiro:hasPermission name="edu:aboutus:edit"><td>
                    <c:choose>
                        <c:when test="${aboutus.status==0}" >
                            <a href="${ctx}/edu/aboutus/publish?id=${aboutus.id}&status=1" onclick="return confirmx('确认要发布该关于我们吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/aboutus/publish?id=${aboutus.id}&status=0" onclick="return confirmx('确认要取消发布该关于我们吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/aboutus/form?id=${aboutus.id}">修改</a>
					<a href="${ctx}/edu/aboutus/delete?id=${aboutus.id}" onclick="return confirmx('确认要删除该关于我们吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
