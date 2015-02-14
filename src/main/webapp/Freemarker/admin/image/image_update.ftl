<@admin.page title="图片修改">
<@admin.conArea title="系统管理>>图片管理>>修改" id="form1" fileUpload="true" action="/admin/image.do?command=update">
<input class="mini-hidden" name="imageId" value="${(image.imageId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>图片名称:</label></td>
		<td><input class="mini-textbox" required="true" name="imageName" style="width:150px;" value="${image.imageName}" /></td>
	</tr>
	<tr>
		<td><label>图片描述:</label></td>
		<td><input class="mini-textbox" required="true" name="imageDescription" style="width:150px;" value="${image.imageDescription}" /></td>
	</tr>
	<tr>
		<td><label>图片排序:</label></td>
		<td><input class="mini-textbox" required="true" name="imageOrder" style="width:150px;" value="${image.imageOrder}" /></td>
	</tr>
	<tr>
		<td><label>所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" value="${image.categoryId}" textField="text" valueField="id"  url="/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="true"/></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="false"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/image.do?command=update" event="Update" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为图片修改页面，请按照上述内容填写修改后图片信息，然后点击【上传】按钮即可。<br>
注意：若此处不选择文件，则不替换已有图片，只修改相关信息。
</@admin.conArea>
<@admin.conArea title="图片预览" id="form3">
<img src="${base}${image.imageUrl}" width="350" height="120" title="非原有比例"></img>
</@admin.conArea>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
	var category = mini.get("categoryId");
    
    category.disable();
    
    function Update() {
    	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    }
</@admin.script>
</@admin.page>