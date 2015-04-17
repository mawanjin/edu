<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海外之前图片管理</title>
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
		<li class="active"><a href="${ctx}/edu/abroadhomeTop/">海外之前图片列表</a></li>
		<shiro:hasPermission name="edu:abroadhomeTop:edit"><li><a href="${ctx}/edu/abroadhomeTop/form">海外之前图片添加</a></li></shiro:hasPermission>
	</ul>

	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缩略图</th><th>顺序</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:abroadhomeTop:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="abroadhomeTop">
			<tr>
				<td><img src="${ctxImg}/${abroadhomeTop.img}" width="30px" height="30px"/> </td>
				<td>${abroadhomeTop.porder}</td>
                <td>
                    <c:choose>
                        <c:when test="${abroadhomeTop.status==1}">已发布</c:when>
                        <c:otherwise>
                            未发布
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${abroadhomeTop.remarks}</td>
				<shiro:hasPermission name="edu:abroadhomeTop:edit"><td>
                    <c:choose>
                        <c:when test="${abroadhomeTop.status==0}" >
                            <a href="${ctx}/edu/abroadhomeTop/publish?id=${abroadhomeTop.id}&status=1" onclick="return confirmx('确认要发布该海外之前图片吗？', this.href)">发布</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/edu/abroadhomeTop/publish?id=${abroadhomeTop.id}&status=0" onclick="return confirmx('确认要取消发布该海外之前图片吗？', this.href)">取消发布</a>
                        </c:otherwise>
                    </c:choose>
    				<a href="${ctx}/edu/abroadhomeTop/form?id=${abroadhomeTop.id}">修改</a>
					<a href="${ctx}/edu/abroadhomeTop/delete?id=${abroadhomeTop.id}" onclick="return confirmx('确认要删除该海外之前图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
