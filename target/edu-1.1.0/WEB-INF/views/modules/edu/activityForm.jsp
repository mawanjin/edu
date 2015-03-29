<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>主题活动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();
			$("#uploadBtn").on("click",function(){
				$("#oUploadBtn").trigger("click");
				$("#uploadTable").show();
			});
		});
	</script>

	<link href="${ctxStatic}/fileupload/bootstrap-image-gallery.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/fileupload/jquery.fileupload-ui.css" type="text/css" rel="stylesheet" />
</head>
<body>

	<ul class="nav nav-tabs">
		<li><a href="${ctx}/edu/activity/">主题活动列表</a></li>
		<li class="active"><a href="${ctx}/edu/activity/form?id=${activity.id}">主题活动<shiro:hasPermission name="edu:activity:edit">${not empty activity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="edu:activity:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>

	<div class="container">
		<!-- The file upload form used as target for the file upload widget -->
		<form id="fileupload" action="${ctx}/sys/fileupload/upload" method="POST" enctype="multipart/form-data">
			<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
			<div class="row fileupload-buttonbar" style="height: 10px;" >
				<div class="span7">
					<!-- The fileinput-button span is used to style the file input field as button -->
                        <span  class="btn btn-success fileinput-button" style="display: none;">
                            <i class="icon-plus icon-white"></i>
                            <span>上传图片</span>
                            <input id="oUploadBtn" type="file" name="files[]" multiple>
                        </span>
					<%--<button type="submit" class="btn btn-primary start">--%>
						<%--<i class="icon-upload icon-white"></i>--%>
						<%--<span>Start upload</span>--%>
					<%--</button>--%>
					<%--<button type="reset" class="btn btn-warning cancel">--%>
						<%--<i class="icon-ban-circle icon-white"></i>--%>
						<%--<span>Cancel upload</span>--%>
					<%--</button>--%>
					<%--<button type="button" class="btn btn-danger delete">--%>
						<%--<i class="icon-trash icon-white"></i>--%>
						<%--<span>Delete</span>--%>
					<%--</button>--%>
					<%--<input type="checkbox" class="toggle">--%>
				</div>
				<!-- The global progress inform¢ation -->
				<div class="span5 fileupload-progress fade">
					<!-- The global progress bar -->
					<div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
						<div class="bar" style="width:0%;"></div>
					</div>
					<!-- The extended global progress information -->
					<div class="progress-extended">&nbsp;</div>
				</div>
			</div>
			<!-- The loading indicator is shown during file processing -->
			<div class="fileupload-loading"></div>
			<!-- The table listing the files available for upload/download -->
			<table id="uploadTable" role="presentation" class=""><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery" id="uploadTableBody"></tbody></table>
		</form>

	</div>

	<form:form id="inputForm" modelAttribute="activity" action="${ctx}/edu/activity/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="img" />
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="title">缩略图:</label>
			<div class="controls">

				<span id="uploadBtn" class="btn btn-success"  <c:if test="${not empty activity.img}">style="display: none"</c:if>>
                            <i class="icon-plus icon-white"></i>
                            <span>上传图片</span>
				</span>
				<img id="portrait" <c:if test="${not empty activity.img}">src="${ctxImg}/${activity.img}"</c:if> />

				<c:if test="${not empty activity.img}">
					<button id="delPortraitBtn1"  class="btn btn-danger"  >
						<i class="icon-trash icon-white"></i>
						<span>删除</span>
					</button>
					<script>
						$("#delPortraitBtn1").on("click",function(event){
							$("#uploadBtn").show();
							$("#delPortraitBtn1").hide();
							$("#portrait").attr("src","");

							event.preventDefault();
						});
					</script>
				</c:if>


				<button id="delPortraitBtn"  class="btn btn-danger" style="display: none;">
					<i  class="icon-trash icon-white"></i>
					<span>删除</span>
				</button>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="title">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="summary">简介:</label>
			<div class="controls">
				<form:input path="summary" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="status">状态:</label>
			<div class="controls">
				<form:radiobutton path="status" value="0" checked="checked"   />未发布
				<form:radiobutton path="status" value="1" />已发布
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="top">是否置顶:</label>
			<div class="controls">
				<form:radiobutton path="top" value="0" checked="checked"  />否
				<form:radiobutton path="top" value="1" />是
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="remarks">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="content">详情:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/edu/activity" />
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="edu:activity:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>

	</form:form>

	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
            {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-upload fade">
            <td class="preview"><span class="fade"></span></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else if (o.files.valid && !i) { %}
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>Start</span>
                </button>
                {% } %}</td>
            {% } else { %}
            <td colspan="2"></td>
            {% } %}
            <td class="cancel">{% if (!i) { %}
                <button class="btn btn-warning">
                    <i class="icon-ban-circle icon-white"></i>
                    <span>Cancel</span>
                </button>
                {% } %}</td>
        </tr>
        {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            {% if (file.error) { %}
            <td></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else { %}
            <td class="preview" width=150px;>{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img id="uploadImg" src="{%=file.thumbnail_url%}"></a>
                {% } %}</td>

            {% } %}
            <td class="delete" width=150px;>
                <button id="uploadDelBtn" class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                        <i class="icon-trash icon-white"></i>
                    <span>删除</span>
                </button>
            </td>
            <td>&nbsp;</td>
        </tr>
        {% } %}
    </script>

	<script src="${ctxStatic}/fileupload/jquery.ui.widget.js"></script>
	<script src="${ctxStatic}/fileupload/tmpl.min.js"></script>
	<script src="${ctxStatic}/fileupload/load-image.min.js"></script>
	<script src="${ctxStatic}/fileupload/canvas-to-blob.min.js"></script>
	<script src="${ctxStatic}/fileupload/bootstrap-image-gallery.min.js"></script>
	<script src="${ctxStatic}/fileupload/jquery.iframe-transport.js"></script>
	<script src="${ctxStatic}/fileupload/jquery.fileupload.js"></script>
	<script src="${ctxStatic}/fileupload/jquery.fileupload-fp.js"></script>
	<script src="${ctxStatic}/fileupload/jquery.fileupload-ui.js"></script>
	<script src="${ctxStatic}/fileupload/locale.js"></script>
	<script src="${ctxStatic}/fileupload/main.js"></script>
</body>
</html>
