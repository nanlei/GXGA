<@admin.page title="角色管理">
<@admin.conArea title="系统管理>>角色管理>>更新" id="form1">
<input class="mini-hidden" name="roleId" value="${(role.roleId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>角色名称:</label></td>
		<td><input class="mini-textbox" required="true" name="roleName" style="width:150px;" value="${(role.roleName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>角色描述:</label></td>
		<td><input class="mini-textbox" required="true" name="roleDescription" style="width:150px;" value="${(role.roleDescription)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="roleOrder" style="width:150px;" value="${(role.roleOrder)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/role.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为角色修改页面，请按照上述内容填写修改后的角色信息，然后点击【保存】按钮即可。
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
    		url: "/admin/role.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "角色修改成功",
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
    	window.parent.CloseOwnerWindow();
    }
</@admin.script>
</@admin.page>