<@admin.page title="紧急通知">
<@admin.conArea title="前台综合>>紧急通知>>修改" id="form1" fileUpload="true" action="/admin/emergencynotice.do?command=update">
<input class="mini-hidden" name="noticeId" value="${(notice.noticeId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>标题:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeTitle" style="width:180px;" value="${(notice.noticeTitle)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeOrder" style="width:180px;" value="${(notice.noticeOrder)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>图片:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="FdataImage" style="width:180px;" required="false"/></td>
	</tr>
	<tr>
		<td><label>附件:</label></td>
		<td><input id="fileupload2" class="mini-htmlfile" name="FdataAttachment" style="width:180px;" required="false"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/emergencynotice.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为紧急通知修改页面，请按照上述内容填写和选择相关内容<br>
若不修改图片和附件，请将它们留空即可<br>
修改完毕后，然后点击【保存】按钮即可
</@admin.conArea>
<@admin.conArea title="图片预览" id="form3">
<img src="${base}${notice.noticeImageUrl}" width="124" height="83" title="非原有比例"></img>
</@admin.conArea>
<@admin.conArea title="附件下载" id="form3">
<a href="${base}${notice.noticeAttachmentUrl}">下载</a>
</@admin.conArea>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
    function Save() {
    	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    } 
</@admin.script>
</@admin.page>