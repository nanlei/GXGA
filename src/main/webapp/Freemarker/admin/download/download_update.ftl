<@admin.page title="下载管理">
<@admin.conArea title="系统管理>>下载管理>>修改" id="form1" fileUpload="true" action="/admin/download.do?command=update">
<input class="mini-hidden" name="downloadId" value="${(download.downloadId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>文件描述:</label></td>
		<td><input class="mini-textbox" required="true" name="downloadDescription" style="width:150px;" value="${(download.downloadDescription)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="downloadOrder" style="width:150px;" value="${(download.downloadOrder)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="false" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/download.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为下载文件上传页面，请按照上述内容填写相关信息，然后点击【保存】按钮即可。
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