<@admin.page title="用户管理">
<@admin.conArea title="系统管理>>用户管理>>新建" id="form1">
<input class="mini-hidden" name="userId" value="${(user.userId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>用户名:</label></td>
		<td>${(user.userName)?default('')}</td>
	</tr>
	<tr>
		<td><label>真实姓名:</label></td>
		<td><input class="mini-textbox" required="true" name="realName" value="${(user.realName)?default('')}" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>绑定IP:</label></td>
		<td><input class="mini-textbox" required="false" name="bindIP" value="${(user.bindIP)?default('')}" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>用户排序:</label></td>
		<td><input class="mini-textbox" required="true" name="userOrder" value="${(user.userOrder)?default('')}" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>角色:</label></td>
		<td><input class="mini-combobox" required="true" name="roleId" textField="text" value="${(user.roleId)?default('')}" valueField="id" dataField="data" url="${base}/admin/const.do?constant=ROLE" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否分局员工:</label></td>
		<td><#if user.isEmployee?default('')="Y">是<#else>否</#if></td>
	</tr>
	<tr>
		<td><label>是否签收人员:</label></td>
		<td><input class="mini-combobox" required="true" name="isSignRole" textField="text" value="${(user.isSignRole)?default('')}" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否锁定:</label></td>
		<td><input class="mini-combobox" required="true" name="isLock" textField="text" value="${(user.isLock)?default('')}" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/user.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为用户修改页面，请按照上述内容填写修改后的用户信息<br>
用户名要求系统内唯一，修改时仍然要求点击【用户名校验】按钮进行校验<br>
用户登录密码不可以修改，只能在上级页面进行【密码初始化】<br>
选择锁定时，用户不能登录<br>
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
			url: "/admin/user.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "用户修改成功",
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