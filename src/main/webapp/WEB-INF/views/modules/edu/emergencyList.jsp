<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>重要提示管理</title>
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
		<li class="active"><a href="${ctx}/edu/emergency/">重要提示列表</a></li>
		<shiro:hasPermission name="edu:emergency:edit"><li><a href="${ctx}/edu/emergency/form">重要提示添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="emergency" action="${ctx}/edu/emergency/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<%--<tr><th>缩略图</th>--%>
			<th>名称</th><th>类型</th><th>置顶</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:emergency:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="emergency">
			<tr>
				<%--<td><img src="${ctxImg}/${emergency.img}" width="30px" height="30px"/> </td>--%>
				<td><a href="${ctx}/edu/emergency/form?id=${emergency.id}">${emergency.title}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${emergency.type==0}">重要</c:when>
                        <c:otherwise>
                            系统
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>
                    <c:choose>
                        <c:when test="${emergency.top==1}">已置顶</c:when>
                        <c:otherwise>
                            普通
                        </c:otherwise>
                    </c:choose>
                </td>

				<td>
                    <c:choose>
                        <c:when test="${emergency.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${emergency.remarks}</td>
				<shiro:hasPermission name="edu:emergency:edit"><td>
                    <c:choose>
                        <c:when test="${emergency.status==0}" >
                            <a href="${ctx}/edu/emergency/publish?id=${emergency.id}&status=1" onclick="return confirmx('确认要发布该重要提示吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/emergency/publish?id=${emergency.id}&status=0" onclick="return confirmx('确认要取消发布该重要提示吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/emergency/form?id=${emergency.id}">修改</a>
					<a href="${ctx}/edu/emergency/delete?id=${emergency.id}" onclick="return confirmx('确认要删除该重要提示吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
