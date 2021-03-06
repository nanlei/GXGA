<@admin.page title="部门管理">
<@admin.conArea title="人力资源>>部门管理>>修改" id="form1">
<input class="mini-hidden" name="departmentId" value="${(department.departmentId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>部门名称:</label></td>
		<td><input class="mini-textbox" required="true" name="departmentName" style="width:150px;" value="${department.departmentName?default('')}"/></td>
	</tr>
	<tr>
		<td><label>部门类型:</label></td>
		<td><input class="mini-combobox" required="true" name="departmentType" value="${department.departmentType?default('')}" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=DEPARTMENTTYPE" showNullItem="false" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>部门编码:</label></td>
		<td><input class="mini-textbox" required="true" name="departmentCode" style="width:150px;" value="${department.departmentCode?default('')}"/></td>
	</tr>
	<tr>
		<td><label>所属上级:</label></td>
		<td><input class="mini-treeselect" id="upperId" name="upperId" required="true" style="width:150px;" value="${department.upperId?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>是否叶子节点:</label></td>
		<td><input class="mini-combobox" required="true" name="isLeaf" style="width:150px;" value="${department.isLeaf?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true"/></td>
	</tr>
	<tr>
		<td><label>部门排序:</label></td>
		<td><input class="mini-textbox" required="true" name="departmentOrder" style="width:150px;" value="${department.departmentOrder?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/department.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为职位修改页面，请按照上述内容填写修改后的相关信息<br>
职位类别和职位类型可以在常量管理中设置，注意常量类型保持统一即可<br>
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
			url: "${base}/admin/department.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "部门修改成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
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
	
	function closeWindow(){
    	window.CloseOwnerWindow();
    }
    
    function onCloseClick(e) {
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    }
</@admin.script>
</@admin.page>