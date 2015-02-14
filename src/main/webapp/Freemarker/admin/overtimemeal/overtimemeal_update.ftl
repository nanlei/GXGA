<@admin.page title="加班用餐">
<@admin.conArea title="网上办公>>加班用餐>>修改" id="form1">
<input class="mini-hidden" name="omId" value="${(overtimeMeal.omId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>所属部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" value="${(overtimeMeal.departmentId)?default('')}" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>加班日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="overtimeDate" value="${(overtimeMeal.overtimeDate)?default('')}" style="width:150px;" format="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<td><label>备注:</label></td>
		<td><input class="mini-textbox" required="true" name="remark" value="${(overtimeMeal.remark)?default('')}" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/overtimemeal.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为加班用餐申请修改页面，请按照上述内容填写修改后的信息，然后点击【保存】按钮即可。
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
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/overtimemeal.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "修改成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
					}else if(data.status=="notnew"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "申请处理中，无法修改",
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