<@admin.page title="分局值班">
<@admin.conArea title="网上办公>>分局值班>>修改" id="form1">
<input class="mini-hidden" name="dutyId" value="${(duty.dutyId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="dutyDate" style="width:150px;" format="yyyy-MM-dd" value="${duty.dutyDate?default('')}" /></td>
	</tr>
	<tr>
		<td><label>值班领导:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyManager" style="width:150px;" value="${duty.dutyManager?default('')}" /></td>
	</tr>
	<tr>
		<td><label>值班长:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyLeader" style="width:150px;" value="${duty.dutyLeader?default('')}" /></td>
	</tr>
	<tr>
		<td><label>值班民警:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyPolice" style="width:150px;" value="${duty.dutyPolice?default('')}" /></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/duty.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为分局值班修改页面，请按照上述内容填写修改后的相关信息<br>
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
			url: "${base}/admin/duty.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "值班信息修改成功",
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