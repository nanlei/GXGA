<@admin.page title="我的回音壁">
<@admin.conArea title="个人中心>>我的回音壁>>评价" id="form1">
<input class="mini-hidden" name="mailId" value="${(mail.mailId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>是否首页显示:</label></td>
		<td><input id="isPublic" name="isPublic" class="mini-combobox" style="width:150px;" textField="text" valueField="id" value="${(mail.isPublic)?default('')}" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr>
		<td style="width:100px;"><label>留言标题:</label></td>
		<td><input id="mailSubject" class="mini-textbox" required="true" name="mailSubject" style="width:330px;" value="${(mail.mailSubject)?default('')}" allowInput="false"/></td>
	</tr>
	<tr id="leaderRow">
		<td><label>局领导:</label></td>
		<td><input id="leaderId" name="leaderId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  value="${(mail.leaderId)?default('')}" url="${base}/admin/common.do?command=getEmployeesByDepartmentId&departmentId=21" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr id="deptAdminRow">
		<td><label>部门管理员:</label></td>
		<td><input id="deptAdminId" name="deptAdminId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  value="${(mail.deptAdminId)?default('')}" url="${base}/admin/common.do?command=getDeptAdminEmployees" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input id="mailContent" class="mini-textarea" required="true" name="mailContent" style="width:330px;height:90px;" value="${(mail.mailContent)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>评价:</label></td>
		<td><input id="rank" name="rank" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  value="${(mail.rank)?default('')}" url="${base}/data/rank.txt" dataField="data" showNullItem="false" allowInput="false" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/selfservice.do?command=mailboxevaluate" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为回音壁留言评价页面，选择星级后，点击【保存】按钮即可
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");

	var isPublic = mini.get("isPublic");
	var mailSubject = mini.get("mailSubject");
	var leaderId = mini.get("leaderId");
	var deptAdminId = mini.get("deptAdminId");
	var mailContent = mini.get("mailContent");
    
    isPublic.disable();
    mailSubject.disable();
    leaderId.disable();
    deptAdminId.disable();
    mailContent.disable();
	
	function Save() {
		var data = form.getData();
    
    	form.validate();
            
        if (form.isValid() == false) {
        	return;
        }
        var json = mini.encode(data);
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/selfservice.do?command=mailboxevaluate",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "回音壁留言评价成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
					}else if(data.status=="notrun"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "留言尚未回复，无法评价",
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