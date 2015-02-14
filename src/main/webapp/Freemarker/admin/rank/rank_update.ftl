<@admin.page title="采稿排名">
<@admin.conArea title="网上办公>>采稿排名>>修改" id="form1">
<input class="mini-hidden" name="rankId" value="${(rank.rankId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td>所属部门:</td>
		<td><input id="departmentId" class="mini-combobox" enabled="false" required="true" name="departmentId" style="width:150px;" value="${rank.departmentId?default('')}" textField="text" valueField="id"  url="${base}/admin/const.do?constant=DEPARTMENT" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>采稿数量:</label></td>
		<td><input class="mini-textbox" required="true" name="rankValue" style="width:150px;" value="${rank.rankValue?default('')}"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="rankOrder" style="width:150px;" value="${rank.rankOrder?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/rank.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为采稿排名修改页面，请按照上述内容填写修改后的相关信息<br>
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
			url: "${base}/admin/rank.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.alert("保存成功");
					}else if(data.status=="exists"){
						form.unmask();
						mini.alert("该部门数据已存在，无需重复添加");
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