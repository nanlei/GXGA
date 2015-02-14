<@admin.page title="上级文件添加签收部门">
<@admin.conArea title="前台综合>>上级文件>>添加签收部门" id="form1">
<input class="mini-hidden" name="articleId" value="${(articleId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" id="departmentId" required="false" name="departmentId" style="width:140px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false" onValueChanged="onDepartmentChanged"/></td>
	</tr>
	<tr id="leader">
		<td><label>局领导:</label></td>
		<td><input id="leaderId" name="leaderId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/common.do?command=getEmployeesByDepartmentId&departmentId=21" dataField="data" showNullItem="true" allowInput="true" required="false"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/superiorfile.do?command=signadddepartment" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为上级文件添加签收部门页面，请选择部门，然后点击【保存】按钮即可。
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	$("#leader").hide();
	
	function onDepartmentChanged(){
		var departmentId=mini.get("departmentId");
		if(departmentId.getText()=="局领导"||departmentId.getValue()==21){
			$("#leader").show();
		} else {
			$("#leader").hide();
		}
	}
	
    function Save() {
    	var data = form.getData();
    	
    	form.validate();
    	
    	if (form.isValid() == false) { 
    		return;
    	}
    	
    	var json = mini.encode(data);
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/superiorfile.do?command=signadddepartment",
    		data: { object: json},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("签收部门添加成功");
    				}else if(data.status=="exists"){
    					form.unmask();
    					mini.alert("该部门已经添加，无需重复");    				
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