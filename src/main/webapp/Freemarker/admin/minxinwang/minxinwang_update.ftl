<@admin.page title="民心网修改">
<@admin.conArea title="网上办公>>民心网>>修改" id="form1">
<input class="mini-hidden" name="transactionId" value="${(transaction.transactionId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td colspan="4"><h2 style="text-align:center">诉求问题请示单</h2></td>
	</tr>
	<tr>
		<td><label>受理编号:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionNo" style="width:722px;" value="${transaction.transactionNo}" /></td>
	</tr>
	<tr>
		<td><label>投诉人姓名:</label></td>
		<td><input class="mini-textbox" required="true" name="config_name" style="width:300px;" value="${config.config_name}" /></td>
		<td><label>投诉人电话:</label></td>
		<td><input class="mini-textbox" required="true" name="config_phone" style="width:300px;" value="${config.config_phone}"/></td>
	</tr>
	<tr>
		<td><label>投诉人地址:</label></td>
		<td><input class="mini-textbox" required="true" name="config_address" style="width:300px;" value="${config.config_address}"/></td>
		<td><label>投诉人Mail:</label></td>
		<td><input class="mini-textbox" required="true" name="config_email" style="width:300px;" value="${config.config_email}"/></td>
	</tr>
	<tr>
		<td><label>投诉时间:</label></td>
		<td><input class="mini-datepicker" required="true" name="config_time" style="width:300px;" format="yyyy-MM-dd" value="${config.config_time}"/></td>
		<td><label>拟办时间:</label></td>
		<td><input class="mini-datepicker" required="true" name="config_disposeTime" style="width:300px;" format="yyyy-MM-dd" value="${config.config_disposeTime}"/></td>
	</tr>
	<tr>
		<td><label>投诉标题:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionTitle" style="width:722px;" value="${transaction.transactionTitle}"/></td>
	</tr>
	<tr>
		<td><label>投诉内容:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_content" style="width:722px;height:100px;" value="${config.config_content}"/></td>
	</tr>
	<tr>
		<td><label>责任部门:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="config_department" style="width:722px;" value="${config.config_department}"/></td>
	</tr>
	<tr>
		<td><label>拟办意见:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_comment" style="width:722px;height:100px;" value="${config.config_comment}"/></td>
	</tr>
	<tr>
		<td><label>分管领导:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="config_leader" style="width:722px;" value="${config.config_leader}"/></td>
	</tr>
	<tr>
		<td><label>领导意见:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_leaderComment" style="width:722px;height:100px;" value="${config.config_leaderComment}"/></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionOrder" style="width:722px;" value="${transaction.transactionOrder}"/></td>
	</tr>
	<tr>
		<td><label>状态:</label></td>
		<td colspan="3"><input class="mini-combobox" required="true" name="transactionStatus" style="width:722px;" value="${transaction.transactionStatus}" textField="text" valueField="id" url="${base}/data/externaltransaction_sts.txt"/></td>
	</tr>
	<tr>
		<td colspan="4"><h3 style="width:800px">注：各承办单位及承办人员要对投诉人的信息进行保密，不得向被投诉人和无关人员泄露有关投诉人的信息，如出现上述情况将对承办单位负责人实行问责。</h3></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/minxinwang.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为民心网诉求问题请示单修改页面。请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
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
    		url: "${base}/admin/minxinwang.do?command=update",
    		data: { object: json},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("民心网诉求问题请示单修改成功");
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