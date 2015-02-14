<@admin.page title="紧急通知">
<@admin.conArea title="前台综合>>紧急通知>>新建" id="form1" fileUpload="true" action="/admin/emergencynotice.do?command=create">
<@admin.con id="datacon1">
	<tr>
		<td><label>标题:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeTitle" style="width:180px;" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeOrder" style="width:180px;" /></td>
	</tr>
	<tr>
		<td><label>图片:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="FdataImage" style="width:180px;" required="true"/></td>
	</tr>
	<tr>
		<td><label>附件:</label></td>
		<td><input id="fileupload2" class="mini-htmlfile" name="FdataAttachment" style="width:180px;" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/emergencynotice.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为紧急通知新建页面，请按照上述内容填写相关内容，然后点击【保存】按钮即可。
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