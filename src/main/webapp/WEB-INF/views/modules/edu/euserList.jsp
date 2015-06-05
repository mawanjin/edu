<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息管理</title>
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
		<li class="active"><a href="${ctx}/edu/euser/">用户信息列表</a></li>
		<shiro:hasPermission name="edu:euser:edit"><li><a href="${ctx}/edu/euser/form">用户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="euser" action="${ctx}/edu/euser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="type" items="${fns:getDictList('edu_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<%--<thead><tr><th>名称</th><th>类型</th><th>昵称</th><th>登录名</th><th>所属学校</th><th>出生年月</th><th>教学时间</th><th>教学领域</th><th>详情</th><shiro:hasPermission name="edu:euser:edit"><th>操作</th></shiro:hasPermission></tr></thead>--%>
		<thead><tr><th>名称</th><th>类型</th><th>昵称</th><th>登录名</th><th>所属学校</th><th>出生年月</th><th>详情</th><shiro:hasPermission name="edu:euser:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="euser">
			<tr>
				<td><a href="${ctx}/edu/euser/form?id=${euser.id}">${euser.name}</a></td>
                <td>
                    <c:choose>
                        <c:when test="${euser.type==0}">学生</c:when>
                        <c:when test="${euser.type==1}">家长</c:when>
                        <c:when test="${euser.type==2}">管理员</c:when>
                        <c:otherwise>
                            监护人
                        </c:otherwise>
                    </c:choose>
                </td>
				<td>${euser.nickName}</td>
				<td>${euser.loginName}</td>
				<td>${euser.school.name}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${euser.birth}" type="both"/></td>
				<%--<td>${euser.teachTime}</td>--%>
				<%--<td>${euser.ability}</td>--%>
				<td>${euser.remarks}</td>
				<shiro:hasPermission name="edu:euser:edit"><td>
					<c:if test="${euser.type==1 or euser.type==3}"><a href="${ctx}/edu/userRelation/?eduUserByParentId.id=${euser.id}">查看学生</a></c:if>
					<c:if test="${euser.type==3}"><a href="${ctx}/edu/reportGuardian/?user.id=${euser.id}">查看监护人报告</a></c:if>
    				<a href="${ctx}/edu/euser/form?id=${euser.id}">修改</a>
					<a href="${ctx}/edu/euser/delete?id=${euser.id}" onclick="return confirmx('确认要删除该用户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
