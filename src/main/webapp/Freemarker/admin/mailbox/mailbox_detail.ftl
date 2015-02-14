<@admin.page title="局长信箱">
<@admin.conArea title="网上办公>>局长信箱>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>留言标题:</label></td>
		<td><input class="mini-textbox" required="true" name="mailSubject" style="width:330px;" value="${(mail.mailSubject)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailContent" style="width:330px;height:90px;" value="${(mail.mailContent)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" name="createByName" style="width:330px;" value="${(mail.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="createByTime" style="width:330px;" value="${(mail.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="createByIP" style="width:330px;" value="${(mail.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailComment" style="width:330px;height:90px;" value="${(mail.mailComment)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复人:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByName" style="width:330px;" value="${(mail.commentByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复时间:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByTime" style="width:330px;" value="${(mail.commentByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByIP" style="width:330px;" value="${(mail.commentByIP)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为局长信箱留言详情页面
</@admin.conArea>
<@admin.script>
</@admin.script>
</@admin.page>