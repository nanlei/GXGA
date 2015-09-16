<@admin.page title="局长信箱新建">
<@admin.conArea title="网上办公>>局长信箱>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td colspan="4"><h2 style="text-align:center">高&nbsp;&nbsp;新&nbsp;&nbsp;园&nbsp;&nbsp;区&nbsp;&nbsp;分&nbsp;&nbsp;局</h2>
                        <h2 style="text-align:center">网上事项转办通知单</h2>
                        <h3 style="text-align:center">局长信箱<input class="mini-textbox" required="true" name="config_no" style="width:110px;" value="${config.config_no}" emptyText="例:【2014】第8号" /</h3></td>
	</tr>
	<tr>
		<td><label>编号:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionNo" style="width:100%;" emptyText="请输入编号" value="${transaction.transactionNo}"/></td>
	</tr>
	<tr>
		<td width="90px"><label>单位:</label></td>
		<td><input class="mini-textbox" required="true" name="config_unit" style="width:100%;" emptyText="例：办公室" value="${config.config_unit}"/></td>
		<td width="90px" style="text-align:right;"><label>时间:</label></td>
		<td><input class="mini-textbox" required="true" name="config_date" style="width:100%;" emptyText="例：2014年11月19日" value="${config.config_date}"/></td>
	</tr>
	<tr>
		<td><label>标题:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionTitle" style="width:100%;" value="${transaction.transactionTitle}"/></td>
	</tr>
	<tr>
		<td><label>办理事项:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_content" style="width:100%;height:100px;" value="${config.config_content}"/></td>
	</tr>
	<tr>
		<td><label>办公室意见:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_comment" style="width:100%;height:100px;" value="${config.config_comment}"/></td>
	</tr>
	<tr>
		<td><label>领导批示:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_leaderComment" style="width:100%;height:100px;" value="${config.config_leaderComment}"/></td>
	</tr>
	<tr>
		<td><label>办理结果:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_result" style="width:100%;height:100px;" value="${config.config_result}"/></td>
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
本页面为局长信箱通知单详情页面<br>
</@admin.conArea>
<@admin.script>

</@admin.script>
</@admin.page>