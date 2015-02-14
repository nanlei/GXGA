<@admin.page title="值班计划">
<@admin.conArea title="网上办公>>值班计划>>上传" id="form1" fileUpload="true" action="/admin/dutyplan.do?command=create">
<@admin.con id="datacon1">
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" required="true" <#if loginUser.departmentId!=0>enabled="false"</#if> name="departmentId" style="width:150px;" value="${(departmentId)?default('${loginUser.departmentId}')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="false" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false" onbeforenodeselect="beforenodeselect"/></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="上传" actionName="/admin/dutyplan.do?command=create" event="Create" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为值班计划表上传页面，请选择附件，然后点击【上传】按钮即可。
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

 	function beforenodeselect(e) {
 		//禁止选中父节点
 		if (e.isLeaf == false) e.cancel = true;
 	}
</@admin.script>
</@admin.page>