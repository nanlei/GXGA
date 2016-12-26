<@admin.page title="我的回音壁">
<@admin.conArea title="个人中心>>我的回音壁" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>首页是否显示:</label></td>
		<td><input id="isPublic" name="isPublic" class="mini-combobox" style="width:150px;" textField="text" valueField="id" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr>
		<td><label>留言标题:</label></td>
		<td><input class="mini-textbox" required="true" name="mailSubject" style="width:330px;" /></td>
	</tr>
	<tr id="leaderRow">
		<td><label>局领导:</label></td>
		<td><input id="leaderId" name="leaderId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/common.do?command=getEmployeesByDepartmentId&departmentId=21" dataField="data" showNullItem="true" allowInput="true" required="true"/></td>
	</tr>
	<tr id="deptAdminRow">
		<td><label>部门管理员:</label></td>
		<td><input id="deptAdminId" name="deptAdminId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/common.do?command=getDeptAdminEmployees" dataField="data" showNullItem="true" allowInput="true" required="true"/></td>
	</tr>
	<tr>
		<td><label>回复期限:</label></td>
		<td><input class="mini-datepicker" required="true" name="dueDate" style="width:150px;" format="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailContent" style="width:330px;height:90px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/selfservice.do?command=mailboxcreate" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为回音壁留言发布页面，请按照上述内容填写相关信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/selfservice.do?command=mailboxcreate",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("回音壁留言发布成功");
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