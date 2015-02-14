<@admin.page title="加班用餐">
<@admin.conArea title="网上办公>>加班用餐>>详情" id="form1">
<input class="mini-hidden" name="omId" value="${(overtimeMeal.omId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td><label>所属部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" readonly="readonly" style="width:150px;" value="${(overtimeMeal.departmentId)?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>加班日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="overtimeDate" readonly="readonly" value="${(overtimeMeal.overtimeDate)?default('')}" style="width:150px;" format="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<td><label>备注:</label></td>
		<td><input class="mini-textbox" required="true" name="remark" readonly="readonly" style="width:150px;" value="${(overtimeMeal.remark)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" name="createByName" readonly="readonly" style="width:150px;" value="${(overtimeMeal.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="createByTime" readonly="readonly" style="width:150px;" value="${(overtimeMeal.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="createByIP" readonly="readonly" style="width:150px;" value="${(overtimeMeal.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复:</label></td>
		<td><input class="mini-textbox" required="true" name="feedback" style="width:150px;" value="${(overtimeMeal.feedback)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="回复" actionName="/admin/overtimemeal.do?command=reply" event="Reply" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为加班用餐回复页面
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	function Reply() {
		var data = form.getData();
    
    	form.validate();
            
        if (form.isValid() == false) {
        	return;
        }
        var json = mini.encode(data);
    	
    	form.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/overtimemeal.do?command=reply",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "回复成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
					}else if(data.status=="run"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "已回复，无需重复",
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