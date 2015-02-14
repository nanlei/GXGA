<@admin.page title="通讯录">
<@admin.conArea title="人力资源>>通讯录>>修改" id="form1">
<input class="mini-hidden" name="contactId" value="${(contact.contactId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>姓名:</label></td>
		<td><input class="mini-textbox" required="true" name="name" style="width:150px;" value="${contact.name?default('')}" /></td>
	</tr>
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" style="width:150px;" value="${contact.departmentId?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>科室:</label></td>
		<td><input class="mini-textbox" required="true" name="subDepartment"  style="width:150px;" value="${contact.subDepartment?default('')}" /></td>
	</tr>
	<tr>
		<td><label>职位:</label></td>
		<td><input id="positionId" class="mini-combobox" required="true" name="positionId" style="width:150px;" value="${contact.positionId?default('')}" textField="text" valueField="id"  url="${base}/admin/const.do?constant=POSITION" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="contactOrder"  style="width:150px;" value="${contact.contactOrder?default('')}" /></td>
	</tr>
	<tr>
		<td><label>内线:</label></td>
		<td><input class="mini-textbox" required="false" name="internalNo"  style="width:150px;" value="${contact.internalNo?default('')}" /></td>
	</tr>
	<tr>
		<td><label>外线:</label></td>
		<td><input class="mini-textbox" required="false" name="externalNo"  style="width:150px;" value="${contact.externalNo?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>手机:</label></td>
		<td><input class="mini-textbox" required="false" name="mobile"  style="width:150px;" value="${contact.mobile?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>虚拟号:</label></td>
		<td><input class="mini-textbox" required="false" name="virtualNo"  style="width:150px;" value="${contact.virtualNo?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>联通手机:</label></td>
		<td><input class="mini-textbox" required="false" name="unicomNo"  style="width:150px;" value="${contact.unicomNo?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>联通虚拟号:</label></td>
		<td><input class="mini-textbox" required="false" name="unicomVirtualNo"  style="width:150px;" value="${contact.unicomVirtualNo?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>房间号:</label></td>
		<td><input class="mini-textbox" required="false" name="officeNo"  style="width:150px;" value="${contact.officeNo?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>宅电:</label></td>
		<td><input class="mini-textbox" required="false" name="homePhone"  style="width:150px;" value="${contact.homePhone?default('')}" /></td>				
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/contact.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为通讯录修改页面，请按照上述内容和要求填写修改后的相关信息<br>
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
			url: "${base}/admin/contact.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "通讯录信息修改成功",
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
</@admin.script>
</@admin.page>