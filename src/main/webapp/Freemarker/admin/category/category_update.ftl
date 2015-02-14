<@admin.page title="目录新建">
<@admin.conArea title="系统管理>>目录管理>>修改" id="form1">
<input class="mini-hidden" name="categoryId" value="${(category.categoryId)?default("")}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>目录名称:</label></td>
		<td><input class="mini-textbox" required="true" name="categoryName" value="${(category.categoryName)?default("")}"/></td>
	</tr>
	<tr>
		<td><label>目录排序:</label></td>
		<td><input class="mini-textbox" required="true" name="categoryOrder" value="${(category.categoryOrder)?default("")}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/category.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为目录修改页面，请按照上述内容填写修改后的目录信息，然后点击【保存】按钮即可。
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
			url: "/admin/category.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "目录修改成功",
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
					mini.alert(text);
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