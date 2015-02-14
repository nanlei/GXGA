<@admin.page title="设备维修">
<@admin.conArea title="网上办公>>设备维修>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>所属部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" style="width:150px;" value="${(assetRepair.departmentId)?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>维修代码:</label></td>
		<td><input class="mini-textbox" required="true" name="arCode" style="width:150px;" value="${(assetRepair.arCode)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>设备名称:</label></td>
		<td><input class="mini-textbox" required="true" name="assetName" style="width:150px;" value="${(assetRepair.assetName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>设备所在地:</label></td>
		<td><input class="mini-textbox" required="true" name="assetLocation" style="width:150px;" value="${(assetRepair.assetLocation)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>备注:</label></td>
		<td><input class="mini-textbox" required="true" name="remark" style="width:150px;" value="${(assetRepair.remark)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" name="createByName" style="width:150px;" value="${(assetRepair.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="createByTime" style="width:150px;" value="${(assetRepair.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="createByIP" style="width:150px;" value="${(assetRepair.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textbox" required="true" name="feedback" style="width:150px;" value="${(assetRepair.feedback)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复人:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByName" style="width:150px;" value="${(assetRepair.feedbackByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复时间:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByTime" style="width:150px;" value="${(assetRepair.feedbackByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByIP" style="width:150px;" value="${(assetRepair.feedbackByIP)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为设备维修详情页面
</@admin.conArea>
<@admin.script>
</@admin.script>
</@admin.page>