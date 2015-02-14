<@admin.page title="专项工作新建">
<@admin.conArea title="专项工作>>专项工作管理>>新建" id="form1" fileUpload="true" action="/admin/jobheader.do?command=create">
<@admin.con id="datacon1">
	<tr>
		<td><label>标题:</label></td>
		<td><input id="fileupload1" class="mini-textbox" name="jobTitle" style="width:180px;" required="true" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input id="fileupload1" class="mini-textbox" name="jobOrder" style="width:180px;" required="true" /></td>
	</tr>
	<tr>
		<td><label>图片:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:180px;" required="true" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/jobheader.do?command=create" event="Create" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为专项工作新建页面，请选择图片文件，然后点击【新建】按钮即可。
</@admin.conArea>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
    function Create() {
    	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    } 
</@admin.script>
</@admin.page>