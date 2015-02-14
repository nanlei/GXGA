<@admin.page title="外部IP管理">
<@admin.conArea title="系统管理>>外部IP管理>>修改" id="form1">
<input class="mini-hidden" name="externalIPId" value="${(externalIP.externalIPId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>IP地址:</label></td>
		<td><input class="mini-textbox" required="true" name="externalIP" style="width:150px;" value="${(externalIP.externalIP)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>名称:</label></td>
		<td><input class="mini-textbox" required="true" name="externalIPName" style="width:150px;" value="${(externalIP.externalIPName)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>描述:</label></td>
		<td><input class="mini-textbox" required="true" name="externalIPDescription" style="width:150px;" value="${(externalIP.externalIPDescription)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="externalIPOrder" style="width:150px;" value="${(externalIP.externalIPOrder)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>是否锁定:</label></td>
		<td><input class="mini-combobox" required="true" name="isLock" textField="text" valueField="id" dataField="data" url="/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;" value="${(externalIP.isLock)?default('')}"/>
		</td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/externalip.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为外部IP修改页面，请按照上述内容填写修改后的相关信息<br>
设定的IP可以访问本站所有授权信息<br>
选择锁定时，该IP只能访问本站首页<br>
填写完成后，点击【保存】按钮即可
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
		
		form.loading("操作中，请稍后......");
		
		$.ajax({
			url: "/admin/externalip.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "外部IP修改成功",
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