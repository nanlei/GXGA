<@admin.page title="图片上传">
<@admin.conArea title="系统管理>>图片管理>>上传" id="form1" fileUpload="true" action="/admin/image.do?command=upload">
<@admin.con id="datacon1">
	<tr>
		<td><label>图片名称:</label></td>
		<td><input class="mini-textbox" required="true" name="imageName" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>图片描述:</label></td>
		<td><input class="mini-textbox" required="true" name="imageDescription" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>图片排序:</label></td>
		<td><input class="mini-textbox" required="true" name="imageOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="true"/></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="上传" actionName="/admin/image.do?command=upload" event="Upload" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为图片上传页面，请按照上述内容填写图片信息，然后点击【上传】按钮即可。
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