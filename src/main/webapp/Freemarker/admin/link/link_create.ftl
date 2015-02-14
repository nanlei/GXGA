<@admin.page title="链接管理">
<@admin.conArea title="系统管理>>链接管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>链接地址:</label></td>
		<td><input class="mini-textbox" required="true" name="linkUrl" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>链接描述:</label></td>
		<td><input class="mini-textbox" required="true" name="linkDescription" style="width:150px;" /></td>
	</tr>
	<tr>
		<td>链接类型：</td>
		<td><input id="linkType" class="mini-combobox" required="true" name="linkType" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=LINKTYPE" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="linkOrder" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/link.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为链接新建页面，请按照上述内容填写链接信息，然后点击【保存】按钮即可。
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
    		url: "/admin/link.do?command=create",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("链接新建成功");
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
</@admin.script>
</@admin.page>