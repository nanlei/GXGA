<@admin.page title="密码修改">
<@admin.conArea title="个人中心>>密码修改" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>原密码:</label></td>
		<td><input class="mini-password" required="true" name="oldPassword" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>新密码:</label></td>
		<td><input class="mini-password" required="true" name="newPassword" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>确认新密码:</label></td>
		<td><input class="mini-password" required="true" name="newPasswordConfirm" style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/selfservice.do?command=passwordupdate" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为密码修改页面，请按照上述内容填写相关信息<br>
填写完成后，点击【保存】按钮即可。
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
		
		form.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/selfservice.do?command=passwordupdate",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						showMessage("密码修改成功");
					}else if(data.status=="oldPwdWrong"){
						form.unmask();
						showMessage("原密码错误，请重新填写");
					}else if(data.status=="pwdNotEqual"){
						form.unmask();
						showMessage("两次输入的新密码不同，请重新填写");
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

	function showMessage(msg){
		mini.showMessageBox({
			title: "提示",
			message: msg,
			buttons: ["ok"],
			iconCls: "mini-messagebox-info",
			callback: function(action){
				if(action=="ok"||action=="close"){
					//closeWindow();
				}
			}
		});
	}

	function closeWindow(){
    	window.CloseOwnerWindow();
    }
</@admin.script>
</@admin.page>