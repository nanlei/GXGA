<@admin.page title="用户管理">
<@admin.conArea title="系统管理>>用户管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>用户名:</td>
		<td><input class="mini-textbox" id="userName" name="userName"  style="width:150px;"/></td>
		<td>真实姓名:</td>
		<td><input class="mini-textbox" id="realName" name="realName" style="width:150px;"/></td>
		<td>是否锁定:</td>
		<td><input class="mini-combobox" id="isLock" name="isLock" style="width:150px;" textField="text" valueField="id" url="${base}/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="false"/></td>
		<td>是否签收人员:</td>
		<td><input class="mini-combobox" id="isSignRole" name="isSignRole" style="width:150px;" textField="text" valueField="id" url="${base}/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="false"/></td>
	</tr>
	<tr>
		<td>绑定IP:</td>
		<td><input class="mini-textbox" id="bindIP" name="bindIP"  style="width:150px;"/></td>		
		<td>角色:</td>
		<td><input id="roleId" name="roleId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=ROLE" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="false"/></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/user.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/user.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/user.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/user.do?command=delete" event="Delete" icon="icon-remove"/>
	<@admin.actBtn name="密码重置" actionName="/admin/user.do?command=passwordreset" event="PasswordReset" icon="icon-user"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" url="${base}/admin/user.do?command=search"  idField="userId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="userName" width="120" headerAlign="center" align="center" allowSort="true">用户名</div>    
		<div field="realName" width="100" headerAlign="center" align="center" allowSort="true">真实姓名</div>
		<div field="bindIP" width="100" headerAlign="center" align="center" allowSort="true">绑定IP</div>
		<div field="lastLoginIP" width="100" headerAlign="center" align="center" allowSort="true">上次登录IP</div>
		<div field="lastLoginTime" width="140" headerAlign="center" align="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">上次登录时间</div>
		<div field="roleName" width="100" headerAlign="center" align="center" allowSort="false">角色</div>
		<div field="isEmployee" width="70" headerAlign="center" align="center" allowSort="true">是否分局员工</div>
		<div field="isSignRole" width="70" headerAlign="center" align="center" allowSort="true">是否签收人员</div>
		<div field="isLock" width="70" headerAlign="center" align="center" allowSort="true">是否锁定</div>
		<div field="userOrder" width="60" headerAlign="center" align="center" allowSort="true">排序</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("userOrder", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "/admin/user.do?command=createpre",
			title: "新建用户", width: 450, height: 450,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "/admin/user.do?command=updatepre&userId="+rows[0].userId,
        		title: "修改用户", width: 450, height: 450,
        		ondestroy: function (action) {
        			Search();
        		}
        	});
        } else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选中一条数据",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        }
    }
    
    function Delete() {
    	var rows = grid.getSelecteds();
    	
    	if (rows.length > 0) {            
    		mini.showMessageBox({
    			title: "提示", 
    			message: "确定删除吗？",
    			buttons: ["ok","no"],
    			iconCls: "mini-messagebox-info", 
    			callback: function(action){
    				if(action=="ok"){
    					doDelete(rows);
    				}
   		 		}
   		 	});
   		 } else {
   		 	mini.showMessageBox({
   		 		title:"提示",
   		 		message:"请至少选择一条数据",
   		 		buttons:["ok"],
   		 		iconCls:"mini-messagebox-info"
   		 	});
   		 }
	}
            
	function doDelete(rows){
		var idsStr = [];
		
		for (var i = 0, l = rows.length; i < l; i++) {
			var r = rows[i];
			idsStr.push(r.userId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "/admin/user.do?command=delete",
			data: { userIds: ids },
			success: function (data) {
				try{
					if(data.status=="true"){
						grid.unmask();
						Search();
						mini.alert("删除成功");
					}
				}catch(e){
					grid.unmask();
					mini.alert(data);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				grid.unmask();
				mini.alert(jqXHR.responseText);
			}
		});
	}
	
	function PasswordReset(){
    	var rows = grid.getSelecteds();
    	
    	if (rows.length == 1) {            
			mini.showMessageBox({
    			title: "提示", 
    			message: "确定重置该用户密码吗？",
    			buttons: ["ok","no"],
    			iconCls: "mini-messagebox-info", 
    			callback: function(action){
    				if(action=="ok"){
    					doPasswordReset(rows);
    				}
   		 		}
   		 	});			
   		 } else {
   		 	mini.showMessageBox({
   		 		title:"提示",
   		 		message:"请只选择一条数据",
   		 		buttons:["ok"],
   		 		iconCls:"mini-messagebox-info"
   		 	});
   		 }	
	}
	
	function doPasswordReset(rows){
		$.ajax({
			url: "/admin/user.do?command=passwordreset",
			data: { userId: rows[0].userId },
			success: function (data) {
				try{
					if(data.status=="true"){
						grid.unmask();
						Search();
						mini.alert("密码重置成功");
					}
				}catch(e){
					grid.unmask();
					mini.alert(data);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				grid.unmask();
				mini.alert(jqXHR.responseText);
			}
		});	
	}
</@admin.script>
</@admin.page>