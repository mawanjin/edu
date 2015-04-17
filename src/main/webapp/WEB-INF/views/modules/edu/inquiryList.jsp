<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>留言咨询管理</title>
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
		<li class="active"><a href="${ctx}/edu/inquiry/">留言咨询列表</a></li>
		<shiro:hasPermission name="edu:inquiry:edit"><li><a href="${ctx}/edu/inquiry/form?type=${type}">留言咨询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="inquiry" action="${ctx}/edu/inquiry/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>留言者</th><th>详情</th><th>时间</th><th>查看次数</th><shiro:hasPermission name="edu:inquiry:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="inquiry">
			<tr>
				<td><a href="${ctx}/edu/inquiry/form?id=${inquiry.id}">${inquiry.title}</a></td>
				<td>${inquiry.euser.name}</td>
				<td>${inquiry.content}</td>
                <td>${inquiry.createDate}</td>
				<td>${inquiry.viewCount}</td>
				<shiro:hasPermission name="edu:inquiry:edit"><td>

    				<a href="${ctx}/edu/inquiry/form?id=${inquiry.id}">修改</a>
					<a href="${ctx}/edu/inquiry/delete?id=${inquiry.id}" onclick="return confirmx('确认要删除该留言咨询吗？', this.href)">删除</a>
					<a href="${ctx}/edu/inquiryReply?inquiry.id=${inquiry.id}&type=${type}">查看回复</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
