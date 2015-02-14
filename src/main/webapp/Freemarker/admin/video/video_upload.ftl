<@admin.page title="视频上传">
<@admin.conArea title="系统管理>>视频管理>>上传" id="form1" fileUpload="true" action="${base}/admin/video.do?command=upload">
<@admin.con id="datacon1">
	<tr>
		<td><label>视频名称:</label></td>
		<td><input class="mini-textbox" required="true" name="videoName" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>视频描述:</label></td>
		<td><input class="mini-textbox" required="true" name="videoDescription" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>视频排序:</label></td>
		<td><input class="mini-textbox" required="true" name="videoOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="true"/></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="上传" actionName="/admin/video.do?command=upload" event="Upload" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为视频上传页面，请按照上述内容填写视频信息，然后点击【上传】按钮即可。
</@admin.conArea>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
    function Upload() {
    	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    } 
</@admin.script>
</@admin.page>