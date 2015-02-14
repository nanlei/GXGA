<@admin.page title="会议管理">
<@admin.conArea title="网上办公>>会议管理>>修改" id="form1">
<input class="mini-hidden" name="meetingId" value="${(meeting.meetingId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>会议主题:</label></td>
		<td><input class="mini-textbox" required="true" name="meetingSubject" style="width:300px;" value="${(meeting.meetingSubject)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>会议室:</label></td>
		<td><input class="mini-textbox" required="true" name="meetingRoom" style="width:300px;" value="${(meeting.meetingRoom)?default('')}" /></td>		
	</tr>
	<tr>
		<td><label>部门:</label></td>
		<td><input id="departmentId" class="mini-combobox" required="true" name="departmentId" style="width:300px;" textField="text" valueField="id"  url="/admin/const.do?constant=DEPARTMENT" dataField="data" showNullItem="true" allowInput="true" value="${(meeting.departmentId)?default('')}"/></td>		
	</tr>
	<tr>
		<td>开始时间:</td>
		<td><input class="mini-datepicker" required="true" name="meetingStartTime" style="width:300px;" format="yyyy-MM-dd HH:mm" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false" value="${(meeting.meetingStartTime)?default('')}"/></td>
	</tr>
	<tr>
		<td>结束时间:</td>
		<td><input class="mini-datepicker" required="true" name="meetingEndTime" style="width:300px;" format="yyyy-MM-dd HH:mm" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false" value="${(meeting.meetingEndTime)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/meeting.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为会议修改页面，请按照上述内容填写修改后的信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/meeting.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "会议修改成功",
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