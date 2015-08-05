<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();

			$("#showModifyPassword").on("click",function(){
				$("#passField").show();
				$("#passConfirmField").show();
				$("#showModifyPassword").hide();
				$("#cancelModifyPassword").show();
			});

			$("#cancelModifyPassword").on("click",function(){
				$("#passField").hide();
				$("#cancelModifyPassword").hide();
				$("#passConfirmField").hide();
				$("#showModifyPassword").show();
			});


		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/euser/">用户信息列表</a></li>
		<li class="active"><a href="${ctx}/edu/euser/form?id=${euser.id}">用户信息<shiro:hasPermission name="edu:euser:edit">${not empty euser.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:euser:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="euser" action="${ctx}/edu/euser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="name">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="loginName">登录名:</label>
			<div class="controls">
				<form:input path="loginName" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="loginName">昵称:</label>
			<div class="controls">
				<form:input path="nickName" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="loginName"></label>
			<div class="controls">
				<c:if test="${not empty euser.name}">
					<a id="showModifyPassword" href="#">修改密码?</a>
					<a id="cancelModifyPassword" href="#" style="display: none;">取消修改密码?</a>
				</c:if>
			</div>
		</div>


		<div id="passField" class="control-group" <c:if test="${not empty euser.password}">style="display: none"</c:if> >
			<label class="control-label" for="password">密码:</label>
			<div class="controls">
				<form:password path="password" class="required" minlength="5"  />
			</div>
		</div>

		<div id="passConfirmField" class="control-group" <c:if test="${not empty euser.password}">style="display: none"</c:if>>
			<label class="control-label" for="confirmPass">再次输入密码:</label>
			<div class="controls">
				<input id="confirmPass" type="password" equalTo="#password" />
			</div>
		</div>


        <div class="control-group">
            <label class="control-label" for="type">类型:</label>
            <div class="controls">
				<form:radiobuttons  path="type" items="${fns:getDictList('edu_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
            </div>
        </div>


		<div class="control-group">
			<label class="control-label" for="loginName">出生年月:</label>
			<div class="controls">
				<input id="beginDate" name="birth" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${euser.birth}" type="both"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>

		<div class="control-group">
            <label class="control-label" for="type">所属学校:</label>
            <div class="controls">
				<select name="school.id">
					<option value="">-</option>
					<c:forEach items="${schools}" var="school">
						<option value="${school.id}">${school.name}</option>
					</c:forEach>
				</select>
            </div>
        </div>

		<%--<div class="control-group">--%>
			<%--<label class="control-label" for="loginName">教学时间:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="teachTime" htmlEscape="false" maxlength="200" />--%>
			<%--</div>--%>
		<%--</div>--%>

		<%--<div class="control-group">--%>
			<%--<label class="control-label" for="loginName">教学领域:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="ability" htmlEscape="false" maxlength="200" />--%>
			<%--</div>--%>
		<%--</div>--%>


		<div class="control-group">
			<label class="control-label" for="remarks">详情:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="edu:euser:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
