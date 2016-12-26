<@admin.page title="回音壁">
<@admin.conArea title="网上办公>>回音壁>>详情" id="form1">
<input class="mini-hidden" name="mailId" value="${(mail.mailId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>是否首页显示:</label></td>
		<td><input id="isPublic" name="isPublic" class="mini-combobox" readonly="readonly" style="width:150px;" textField="text" valueField="id" value="${(mail.isPublic)?default('')}" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr>
		<td><label>留言标题:</label></td>
		<td><input id="mailSubject"class="mini-textbox" required="true" readonly="readonly" name="mailSubject" style="width:330px;" value="${(mail.mailSubject)?default('')}"/></td>
	</tr>
	<tr id="leaderRow">
		<td><label>局领导:</label></td>
		<td><input id="leaderId" name="leaderId" class="mini-combobox" readonly="readonly" style="width:150px;" textField="text" valueField="id"  value="${(mail.leaderId)?default('')}" url="${base}/admin/common.do?command=getEmployeesByDepartmentId&departmentId=21" dataField="data" showNullItem="true" allowInput="true" required="true"/></td>
	</tr>
	<tr id="deptAdminRow">
		<td><label>部门管理员:</label></td>
		<td><input id="deptAdminId" name="deptAdminId" class="mini-combobox" readonly="readonly" style="width:150px;" textField="text" valueField="id"  value="${(mail.deptAdminId)?default('')}" url="${base}/admin/common.do?command=getDeptAdminEmployees" dataField="data" showNullItem="true" allowInput="true" required="true"/></td>
	</tr>
	<tr>
		<td><label>回复期限:</label></td>
		<td><input id="dueDate" class="mini-datepicker" required="true" name="dueDate" readonly="readonly" style="width:150px;" format="yyyy-MM-dd" value="${mail.dueDate?default('')}"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input id="mailContent" class="mini-textarea" required="true" readonly="readonly" name="mailContent" style="width:330px;height:90px;" value="${(mail.mailContent)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input id="createByName" class="mini-textbox" required="true" readonly="readonly" name="createByName" style="width:330px;" value="${(mail.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input id="createByTime" class="mini-textbox" required="true" readonly="readonly" name="createByTime" style="width:330px;" value="${(mail.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input id="createByIP" class="mini-textbox" required="true" readonly="readonly" name="createByIP" style="width:330px;" value="${(mail.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailComment" style="width:330px;height:90px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="回复" actionName="/admin/mailbox.do?command=reply" event="Reply" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为回音壁留言回复页面
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	var isPublic = mini.get('isPublic');
	var mailSubject = mini.get('mailSubject');
	var leaderId = mini.get('leaderId');
	var deptAdminId = mini.get('deptAdminId');
	var dueDate = mini.get('dueDate');
	var mailContent = mini.get('mailContent');
	var createByName = mini.get('createByName');
	var createByTime = mini.get('createByTime');
	var createByIP =mini.get('createByIP');
	
	isPublic.disable();
	mailSubject.disable();
	leaderId.disable();
	deptAdminId.disable();
	dueDate.disable();
	mailContent.disable();
	createByName.disable();
	createByTime.disable();
	createByIP.disable();
	
	function Reply() {
		var data = form.getData();
    
    	form.validate();
            
        if (form.isValid() == false) {
        	return;
        }
        var json = mini.encode(data);
    	
    	form.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/mailbox.do?command=reply",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "留言回复成功",
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
    						message: "留言已回复，无需重复",
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