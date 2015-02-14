<@admin.page title="用户管理">
<@admin.conArea title="系统管理>>用户管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td style="width:100px;"><label>用户名:</label></td>
		<td><input class="mini-textbox" required="true" id="userName" name="userName" style="width:150px;"/>&nbsp;&nbsp;<@admin.actBtn name="用户名校验" actionName="/admin/user.do?command=usernamecheck" event="Check" width="100"/></td>
	</tr>
	<tr>
		<td><label>真实姓名:</label></td>
		<td><input class="mini-textbox" required="true" name="realName" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>绑定IP:</label></td>
		<td><input class="mini-textbox" required="false" name="bindIP" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>用户排序:</label></td>
		<td><input class="mini-textbox" required="true" name="userOrder" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>角色:</label></td>
		<td><input class="mini-combobox" required="true" name="roleId" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=ROLE" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否分局员工:</label></td>
		<td><input class="mini-combobox" required="true" name="isEmployee" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否签收人员:</label></td>
		<td><input class="mini-combobox" required="true" name="isSignRole" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
	<tr>
		<td><label>是否锁定:</label></td>
		<td><input class="mini-combobox" required="true" name="isLock" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="4">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/user.do?command=create" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为用户新建页面，请按照上述内容填写用户信息<br>
用户名是登录系统的凭证，要求系统内唯一且不可以修改<br>
用户初始登录密码默认为123，不需要在此填写<br>
选择锁定时，用户不能登录<br>
填写完成后，点击【保存】按钮即可
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var userNameCheckFlag=false;
	
	var form = new mini.Form("form1");
	
	function Check(){
		var userName=mini.get("userName");
		
		if(userName.getValue()==""){
			mini.alert("请填写用户名");
			return ;
		}
		
		useruserNameCheckFlag=false;
		
		$.ajax({
			url: "/admin/user.do?command=usernamecheck",
			data: { userName : userName.getValue()},
			cache: false,
			success: function(data){
				try{
					if(data.status=="true"){
						form.unmask();
						userNameCheckFlag=true;
						mini.alert("用户名校验成功，可以使用");
                   	}else{
                   		form.unmask();
						mini.alert("用户名重复，请更换后重试");
                   	}		
				}catch(e){
                	form.unmask();
                	mini.alert(data);
				}
			}
		});
	}
	
	function Save() {
	
		var data = form.getData();
		
		form.validate();
		
		if (form.isValid() == false) {
			return;
		}
		
		if(userNameCheckFlag == false) {
			mini.alert("用户名唯一性校验失败，请重试");
			return;
		}
		
		var json = mini.encode(data);
		
		form.loading("操作中，请稍后......");
		
		$.ajax({
			url: "/admin/user.do?command=create",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.alert("保存成功");
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