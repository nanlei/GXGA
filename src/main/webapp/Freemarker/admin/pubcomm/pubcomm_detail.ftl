<@admin.page title="公众交流平台详情">
<@admin.conArea title="网上办公>>公众交流平台>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td colspan="4"><h2 style="text-align:center">公众交流平台回执单</h2></td>
	</tr>
	<tr>
		<td><label>编号:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionNo" style="width:100%;" emptyText="请输入编号" value="${transaction.transactionNo}"/></td>
	</tr>
	<tr>
		<td width="90px"><label>投诉咨询对象:</label></td>
		<td><input class="mini-textbox" required="true" name="config_department" style="width:100%;" value="${config.config_department}"/></td>
		<td width="90px" style="text-align:right;"><label>投诉咨询类型:</label></td>
		<td><input class="mini-textbox" required="true" name="config_type" style="width:100%;" value="${config.config_type}"/></td>
	</tr>
	<tr>
		<td><label>标题:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionTitle" style="width:100%;" value="${transaction.transactionTitle}"/></td>
	</tr>
	<tr>
		<td><label>内容:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_content" style="width:100%;height:100px;" value="${config.config_content}"/></td>
	</tr>
	<tr>
		<td><label>领导审批:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_leaderComment" style="width:100%;height:100px;" value="${config.config_leaderComment}"/></td>
	</tr>
	<tr>
		<td><label>答复:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_replyContent" style="width:100%;height:100px;" value="${config.config_replyContent}"/></td>
	</tr>
	<tr>
		<td><label>答复人:</label></td>
		<td><input class="mini-textbox" required="true" name="config_reply" style="width:100%;" value="${config.config_reply}"/></td>
		<td width="90px" style="text-align:right;"><label>日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="config_date" style="width:100%;" format="yyyy-MM-dd" value="${config.config_date}"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionOrder" style="width:100%;" value="${transaction.transactionOrder}"/></td>
	</tr>
	<tr>
		<td><label>状态:</label></td>
		<td colspan="3"><input class="mini-combobox" required="true" name="transactionStatus" style="width:100%;" value="${transaction.transactionStatus}" textField="text" valueField="id" url="${base}/data/externaltransaction_sts.txt"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>

</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为公众交流平台回执单详情页面<br>
</@admin.conArea>
<@admin.script>

</@admin.script>
</@admin.page>