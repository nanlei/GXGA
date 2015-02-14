<@admin.page title="分类管理">
<@admin.conArea title="部门综合>>分类管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" required="true" <#if loginUser.departmentId!=0>enabled="false"</#if> name="departmentId" style="width:150px;" value="${(departmentId)?default('${loginUser.departmentId}')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="false" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false" onbeforenodeselect="beforenodeselect"/></td>
	</tr>
	<tr>
		<td style="width:100px;"><label>分类名称:</label></td>
		<td><input class="mini-textbox" required="true" name="dcName" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="dcOrder"  style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/departmentcategory.do?command=create" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为部门分类新建页面，请按照上述内容和要求填写相关信息<br>
填写完成后，点击【保存】按钮即可
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	function Save() {
	
		var data = form.getData();
		
		form.validate();
		
		if (form.isValid() == false) {
			return;
		}
		
		var json = mini.encode(data);
		
		form.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/departmentcategory.do?command=create",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.alert("保存成功");
					}
				}catch(e){
					form.unmask();
					mini.alert(data);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				form.unmask();
				mini.alert(jqXHR.responseText);
			}
		});
	}

 	function beforenodeselect(e) {
 		//禁止选中父节点
 		if (e.isLeaf == false) e.cancel = true;
 	}
</@admin.script>
</@admin.page>