<@admin.page title="会议管理">
<@admin.conArea title="网上办公>>会议管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>会议主题:</label></td>
		<td><input class="mini-textbox" required="true" name="meetingSubject" style="width:300px;" /></td>
	</tr>
	<tr>
		<td><label>会议室:</label></td>
		<td><input class="mini-textbox" required="true" name="meetingRoom" style="width:300px;" /></td>		
	</tr>
	<tr>
		<td><label>部门:</label></td>
		<td><input id="departmentId" class="mini-combobox" required="true" name="departmentId" style="width:300px;" textField="text" valueField="id"  url="/admin/const.do?constant=DEPARTMENT" dataField="data" showNullItem="true" allowInput="true" /></td>		
	</tr>
	<tr>
		<td>开始时间:</td>
		<td><input class="mini-datepicker" required="true" name="meetingStartTime" style="width:300px;" format="yyyy-MM-dd HH:mm" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
	</tr>
	<tr>
		<td>结束时间:</td>
		<td><input class="mini-datepicker" required="true" name="meetingEndTime" style="width:300px;" format="yyyy-MM-dd HH:mm" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/meeting.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为会议新建页面，请按照上述内容填写相关信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/meeting.do?command=create",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("会议发布成功");
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
</@admin.script>
</@admin.page>