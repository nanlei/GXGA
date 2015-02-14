<@admin.page title="加班用餐">
<@admin.conArea title="网上办公>>加班用餐>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>所属部门:</label></td>
		<td><input class="mini-treeselect" required="true" name="departmentId" style="width:150px;" value="${(overtimeMeal.departmentId)?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td><label>加班日期:</label></td>
		<td><input class="mini-textbox" required="true" name="overtimeDate" style="width:150px;" value="${(overtimeMeal.overtimeDate)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>备注:</label></td>
		<td><input class="mini-textbox" required="true" name="remark" style="width:150px;" value="${(overtimeMeal.remark)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" name="createByName" style="width:150px;" value="${(overtimeMeal.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="createByTime" style="width:150px;" value="${(overtimeMeal.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="createByIP" style="width:150px;" value="${(overtimeMeal.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textbox" required="true" name="feedback" style="width:150px;" value="${(overtimeMeal.feedback)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复人:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByName" style="width:150px;" value="${(overtimeMeal.feedbackByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复时间:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByTime" style="width:150px;" value="${(overtimeMeal.feedbackByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="feedbackByIP" style="width:150px;" value="${(overtimeMeal.feedbackByIP)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为加班用餐详情页面
</@admin.conArea>
<@admin.script>
</@admin.script>
</@admin.page>