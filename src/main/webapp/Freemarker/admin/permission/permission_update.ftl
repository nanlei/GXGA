<@admin.page title="权限修改">
<@admin.conArea title="系统管理>>权限管理>>修改" id="form1">
<input class="mini-hidden" name="permissionId" value="${(permission.permissionId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>权限名称:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionName" style="width:150px;" value="${(permission.permissionName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>权限URL:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionUrl" style="width:150px;" value="${(permission.permissionUrl)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>权限描述:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionDescription" style="width:150px;" value="${(permission.permissionDescription)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>权限排序:</label></td>
		<td><input class="mini-textbox" required="true" name="permissionOrder" style="width:150px;" value="${(permission.permissionOrder)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" value="${(permission.categoryId)?default('')}" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="true"/></td>
	</tr>
	<tr>
		<td><label>是否菜单:</label></td>
		<td><input id="isMenu" name="isMenu" class="mini-combobox" value="${(permission.isMenu)?default('')}" style="width:150px;" onValueChanged="onIsMenuChanged" textField="text" valueField="id" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="true"/></td>
	</tr>
	<tr id="menuInput">
		<td><label>所属菜单:</label></td>
		<td><input id="upperId" name="upperId" class="mini-combobox" value="${(permission.upperId)?default('')}" style="width:150px;" textField="text" valueField="id" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/permission.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为权限修改页面，请按照上述内容填写修改后的权限信息，然后点击【保存】按钮即可。
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	var category = mini.get("categoryId");
	var isMenu=mini.get("isMenu");
	var upPermission = mini.get("upperId");
	
	SetData();
	
	function onCategoryChanged(e) {
		var categoryId=mini.get("categoryId");
    	var upperId=mini.get("upperId");

    	var id=categoryId.getValue();
    	
    	upperId.setValue("");
    	
    	var url="/admin/permission.do?command=ajax&categoryId="+id;
    	
    	upperId.setUrl(url);
    	upperId.select(0);
    }
    
    function onIsMenuChanged(e){
    	var YN=isMenu.getValue();
    	if(YN=="Y"){
    		$("#menuInput").hide();
    		$("#menuInput").attr("value","");
    	}else{
    		$("#menuInput").show();
    		mini.get("upperId").setRequired(true);
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
    		url: "/admin/permission.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "权限修改成功",
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
    				mini.alert(text);
    			}
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    			form.unmask();
    			mini.alert(jqXHR.responseText);
    		}
    	});
    }
    
    function SetData() {
    	
    	var categoryId = category.getValue();
    	
    	upPermission.setValue("");
    	
    	var url = "/admin/permission.do?command=ajax&categoryId="+categoryId;
    	
    	upPermission.setUrl(url);
    	
    	var upPermissionDatas=upPermission.getData();
    	
    	var tipUpPermission;
    	
    	var tmpUpperId;
    	
    	for(var j=0;j<upPermissionDatas.length;j++){
    		var upd=upPermissionDatas[j];
    		
    		if("${(permission.upperId)?default('')}"==upd.id){
    			tmpUpperId=upd.id;
    			tipUpPermission=upd.text;
    			break;
    		}
    	}
    	
    	upPermission.setValue(tmpUpperId);
    	upPermission.setText(tipUpPermission);
    	
    	var YN=isMenu.getValue();
    	if(YN=="Y"){
    		$("#menuInput").hide();
    		$("#menuInput").attr("value","");
    	}else{
    		$("#menuInput").show();
    		mini.get("upperId").setRequired(true);
    	}
    }
    
	function closeWindow(){
    	window.CloseOwnerWindow();
    }
</@admin.script>
</@admin.page>