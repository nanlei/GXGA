<@admin.page title="分局大事记索引管理">
<@admin.conArea title="分局概况>>大事记索引管理>>修改" id="form1">
<input class="mini-hidden" name="memorabiliaId" value="${(memorabiliaIndex.memorabiliaId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>内容:</label></td>
		<td><input class="mini-textbox" required="true" name="memorabiliaContent" value="${(memorabiliaIndex.memorabiliaContent)?default('')}" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="memorabiliaOrder" value="${(memorabiliaIndex.memorabiliaOrder)?default('')}" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/memorabiliaindex.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为大事记索引新建页面，请按照上述内容填写链接信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/memorabiliaindex.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
						form.unmask();
    					mini.showMessageBox({
    	 					title: "提示",    
    						message: "大事记索引修改成功",
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