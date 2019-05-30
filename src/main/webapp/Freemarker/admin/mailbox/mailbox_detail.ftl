<@admin.page title="回音壁">
<@admin.conArea title="网上办公>>回音壁>>详情" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>留言标题:</label></td>
		<td><input class="mini-textbox" required="true" name="mailSubject" style="width:330px;" value="${(mail.mailSubject)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailContent" style="width:330px;height:90px;" value="${(mail.mailContent)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" name="createByName" style="width:330px;" value="${(mail.createByName)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="createByTime" style="width:330px;" value="${(mail.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="createByIP" style="width:330px;" value="${(mail.createByIP)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailComment" style="width:330px;height:90px;" value="${(mail.mailComment)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>回复人:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByName" style="width:330px;" value="${(mail.commentByName)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>回复时间:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByTime" style="width:330px;" value="${(mail.commentByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>回复期限:</label></td>
		<td><input class="mini-datepicker" required="true" name="dueDate" style="width:330px;" format="yyyy-MM-dd" value="${mail.dueDate?default('')}"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" name="commentByIP" style="width:330px;" value="${(mail.commentByIP)?default('')}" allowInput="false"/></td>
	</tr>
	<tr>
		<td><label>评价:</label></td>
		<td><input id="rank" name="rank" class="mini-combobox" style="width:330px;" textField="text" valueField="id"  value="${(mail.rank)?default('')}" url="${base}/data/rank.txt" dataField="data" showNullItem="false" allowInput="false" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为回音壁留言详情页面
</@admin.conArea>
<@admin.script>
    mini.parse();
	
	var form = new mini.Form("form1");

	var rank = mini.get("rank");
	
	rank.disable();
</@admin.script>
</@admin.page>