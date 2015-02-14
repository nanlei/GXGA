<@admin.page title="设备维修">
<@admin.conArea title="网上办公>>设备维修>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>所属部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>设备名称:</label></td>
		<td><input class="mini-textbox" required="true" name="assetName" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>设备所在地:</label></td>
		<td><input class="mini-textbox" required="true" name="assetLocation" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>备注:</label></td>
		<td><input class="mini-textbox" required="true" name="remark" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/assetrepair.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为设备维修发布页面，请按照上述内容填写相关信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/assetrepair.do?command=create",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("发布成功");
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

    function onCloseClick(e) {
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    }
</@admin.script>
</@admin.page>