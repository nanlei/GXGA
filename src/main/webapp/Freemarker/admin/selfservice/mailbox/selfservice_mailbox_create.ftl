<@admin.page title="局长信箱">
<@admin.conArea title="个人中心>>局长信箱" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>留言标题:</label></td>
		<td><input class="mini-textbox" required="true" name="mailSubject" style="width:330px;" /></td>
	</tr>
	<tr id="leader">
		<td><label>局领导:</label></td>
		<td><input id="leaderId" name="leaderId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/common.do?command=getEmployeesByDepartmentId&departmentId=21" dataField="data" showNullItem="true" allowInput="true" required="true"/></td>
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
本页面为局长信箱留言发布页面，请按照上述内容填写相关信息，然后点击【保存】按钮即可。
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
    					mini.alert("留言发布成功");
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