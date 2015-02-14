<@admin.page title="常量管理">
<@admin.conArea title="系统管理>>常量管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>常量类型:</label></td>
		<td><input class="mini-textbox" required="true" name="constantType" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>常量值:</label></td>
		<td><input class="mini-textbox" required="true" name="constantValue" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>常量名称:</label></td>
		<td><input class="mini-textbox" required="true" name="constantName" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="constantOrder" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否锁定:</label></td>
		<td><input class="mini-combobox" required="true" name="isLock" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/>
		</td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/constant.do?command=create" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为系统常量新建页面，请按照上述内容填写相关信息<br>
程序按照常量类型获取该类型下所有常量，请注意命名统一性<br>
常量值用于系统数据存储，常量名称用于页面显示<br>
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
			url: "/admin/constant.do?command=create",
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