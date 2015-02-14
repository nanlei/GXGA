<@admin.page title="职位管理">
<@admin.conArea title="人力资源>>职位管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>职位名称:</label></td>
		<td><input class="mini-textbox" required="true" name="positionName" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>职位类别:</label></td>
		<td><input class="mini-combobox" required="true" name="positionCategory" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITIONCATEGORY" showNullItem="false" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>职位类型:</label></td>
		<td><input class="mini-combobox" required="true" name="positionType" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITIONTYPE" showNullItem="false" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>职位编码:</label></td>
		<td><input class="mini-textbox" required="true" id="positionCode" name="positionCode" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>职位排序:</label></td>
		<td><input class="mini-textbox" required="true" name="positionOrder" style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/position.do?command=create" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为职位新建页面，请按照上述内容填写相关信息<br>
职位类别和职位类型可以在常量管理中设置，注意常量类型保持统一即可<br>
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
			url: "${base}/admin/position.do?command=create",
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