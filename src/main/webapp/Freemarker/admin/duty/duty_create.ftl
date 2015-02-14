<@admin.page title="分局值班">
<@admin.conArea title="网上办公>>分局值班>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="dutyDate" style="width:150px;" format="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<td><label>值班领导:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyManager" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>值班长:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyLeader" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>值班民警:</label></td>
		<td><input class="mini-textbox" required="true" name="dutyPolice" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/duty.do?command=create" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为分局值班新建页面，请按照上述内容填写相关信息<br>
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
			url: "${base}/admin/duty.do?command=create",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.alert("保存成功");
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