<@admin.page title="公众交流平台修改">
<@admin.conArea title="网上办公>>公众交流平台>>修改" id="form1">
<input class="mini-hidden" name="transactionId" value="${(transaction.transactionId)?default('')}"/>
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
	<@admin.actBtn name="保存" actionName="/admin/pubcomm.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为公众交流平台回执单修改页面。请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
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
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/pubcomm.do?command=update",
    		data: { object: json},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("公众交流平台回执单修改成功");
    				}
    			}catch(e){
    				form.unmask();
    				mini.alert(text);
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