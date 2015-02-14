<@admin.page title="链接管理">
<@admin.conArea title="系统管理>>链接管理>>修改" id="form1">
<input class="mini-hidden" name="linkId" value="${(link.linkId)?default('')}" />
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>链接地址:</label></td>
		<td><input class="mini-textbox" required="true" name="linkUrl" style="width:150px;" value="${(link.linkUrl)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>链接描述:</label></td>
		<td><input class="mini-textbox" required="true" name="linkDescription" style="width:150px;" value="${(link.linkDescription)?default('')}"/></td>
	</tr>
	<tr>
		<td>链接类型：</td>
		<td><input id="linkType" class="mini-combobox" required="true" name="linkType" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=LINKTYPE" dataField="data" showNullItem="true" allowInput="false" value="${(link.linkType)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="linkOrder" style="width:150px;" value="${(link.linkOrder)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/link.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为链接修改页面，请按照上述内容填写修改后的链接信息，然后点击【保存】按钮即可。
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
    		url: "/admin/link.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "链接修改成功",
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