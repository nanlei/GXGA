<@admin.page title="局长信箱">
<@admin.conArea title="网上办公>>局长信箱>>详情" id="form1">
<input class="mini-hidden" name="mailId" value="${(mail.mailId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td><label>留言标题:</label></td>
		<td><input class="mini-textbox" required="true" readonly="readonly" name="mailSubject" style="width:330px;" value="${(mail.mailSubject)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>留言内容:</label></td>
		<td><input class="mini-textarea" required="true" readonly="readonly" name="mailContent" style="width:330px;height:90px;" value="${(mail.mailContent)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布人:</label></td>
		<td><input class="mini-textbox" required="true" readonly="readonly" name="createByName" style="width:330px;" value="${(mail.createByName)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" readonly="readonly" name="createByTime" style="width:330px;" value="${(mail.createByTime)?default('')}" dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td><label>IP:</label></td>
		<td><input class="mini-textbox" required="true" readonly="readonly" name="createByIP" style="width:330px;" value="${(mail.createByIP)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>回复内容:</label></td>
		<td><input class="mini-textarea" required="true" name="mailComment" style="width:330px;height:90px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="回复" actionName="/admin/mailbox.do?command=reply" event="Reply" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为局长信箱留言回复页面
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	function Reply() {
		var data = form.getData();
    
    	form.validate();
            
        if (form.isValid() == false) {
        	return;
        }
        var json = mini.encode(data);
    	
    	form.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/mailbox.do?command=reply",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
					if(data.status=="true"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "留言回复成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
					}else if(data.status=="run"){
    					form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "留言已回复，无需重复",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});					
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

	function closeWindow(){
    	window.CloseOwnerWindow();
    }
</@admin.script>
</@admin.page>