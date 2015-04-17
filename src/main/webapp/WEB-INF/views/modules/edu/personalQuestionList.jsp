<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人问答管理</title>
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
	<li class="active"><a href="${ctx}/edu/personalQuestion/">个人问答列表</a></li>
	<shiro:hasPermission name="edu:personalQuestion:edit"><li><a href="${ctx}/edu/personalQuestion/form">个人问答添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="personalQuestion" action="${ctx}/edu/personalQuestion/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>
	&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th>缩略图</th><th>标题</th><th>类别</th><th>简介</th><th>访问次数</th><th>状态</th><th>备注</th><shiro:hasPermission name="edu:personalQuestion:edit"><th>操作</th></shiro:hasPermission></tr></thead>
	<tbody>
	<c:forEach items="${page.list}" var="personalQuestion">
		<tr>
			<td><img src="${ctxImg}/${personalQuestion.img}" width="30px" height="30px"/> </td>
			<td><a href="${ctx}/edu/personalQuestion/form?id=${personalQuestion.id}">${personalQuestion.title}</a></td>
			<td>${personalQuestion.questionCategory.name}</td>
			<td>${personalQuestion.summary}</td>
			<td>${personalQuestion.viewCount}</td>
			<td>
				<c:choose>
					<c:when test="${personalQuestion.status==1}">已发布</c:when>
					<c:otherwise>
						未发布
					</c:otherwise>
				</c:choose>
			</td>
			<td>${personalQuestion.remarks}</td>
			<shiro:hasPermission name="edu:personalQuestion:edit"><td>
				<c:choose>
					<c:when test="${personalQuestion.status==0}">
						<a href="${ctx}/edu/personalQuestion/publish?id=${personalQuestion.id}&status=1" onclick="return confirmx('确认要发布该个人问答吗？', this.href)">发布</a>
					</c:when>
					<c:otherwise>
						<a href="${ctx}/edu/personalQuestion/publish?id=${personalQuestion.id}&status=0" onclick="return confirmx('确认要取消发布该个人问答吗？', this.href)">取消发布</a>
					</c:otherwise>
				</c:choose>

				<a href="${ctx}/edu/personalQuestion/form?id=${personalQuestion.id}">修改</a>
				<a href="${ctx}/edu/personalQuestion/delete?id=${personalQuestion.id}" onclick="return confirmx('确认要删除该个人问答吗？', this.href)">删除</a>
				<a href="${ctx}/edu/personalQuestionReply?personalQuestion.id=${personalQuestion.id}">查看回复</a>

			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
