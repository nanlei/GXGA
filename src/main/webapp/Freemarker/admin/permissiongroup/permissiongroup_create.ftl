<@admin.page title="权限组新建">
<@admin.conArea title="系统管理>>权限组管理>>新建" id="form1">
<input class="mini-hidden" name="permissionGroupId"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:200px;"><label>权限组名称:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionGroupName" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>权限组描述:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionGroupDescription" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>权限组排序:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionGroupOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>新建角色时是否初始化该权限组:</label></td>
		<td><input class="mini-combobox"  name="isInit" style="width:150px;" textField="text" valueField="id" dataField="data" url="/admin/const.do?constant=YN" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/permissiongroup.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为权限组新建页面，请按照上述内容填写权限组信息，然后点击【保存】按钮即可。
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
    		url: "/admin/permissiongroup.do?command=create",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("权限组新建成功");
    				}
    			}catch(e){
    				form.unmask();
    				mini.alert(text);
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