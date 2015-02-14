<@admin.page title="权限组管理">
<@admin.conArea title="系统管理>>权限组管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>权限组名称:</td>
		<td><input class="mini-textbox" name="permissionGroupName"  style="width:150px;"/></td>
		<td>权限组描述:</td>
		<td><input class="mini-textbox" name="permissionGroupDescription" style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="10">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/permissiongroup.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/permissiongroup.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/permissiongroup.do?command=updatedispatcher" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/permissiongroup.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" url="/admin/permissiongroup.do?command=search"  idField="permissionGroupId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="permissionGroupName" width="160" headerAlign="center" allowSort="true">权限组名称</div>
		<div field="permissionGroupDescription" width="160" headerAlign="center" allowSort="true">权限组描述</div>
		<div field="permissionGroupOrder" width="80" headerAlign="center" align="center" allowSort="true">权限组排序</div>
		<div field="isInit" width="80" headerAlign="center" align="center" allowSort="true">新建角色时是否初始化</div>
	</div>
</div>
</@admin.dataArea> 
<@admin.script>
	
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("permissionGroupId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "/admin/permissiongroup.do?command=createpre",
			title: "新建权限组", width: 520, height: 300,
			ondestroy: function(action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "/admin/permissiongroup.do?command=updatedispatcher&permissionGroupId="+rows[0].permissionGroupId,
				title: "修改权限组 : "+rows[0].permissionGroupName, width: 840, height: 530,
				ondestroy: function(action) {
					Search();
				}
			});
		} else {
			mini.showMessageBox({
				title:"提示",
				message:"请只选择一条数据进行修改",
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
   		 		message:"请至少选择一条记录",
   		 		buttons:["ok"],
   		 		iconCls:"mini-messagebox-info"
   		 	});
   		 }
   	}

	function doDelete(rows){
		var idsStr = [];
		
		for (var i = 0, l = rows.length; i < l; i++) {
			var r = rows[i];
			idsStr.push(r.permissionGroupId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "/admin/permissiongroup.do?command=delete",
			data: { permissionGroupIds: ids },
			success: function (data) {
				try{
					if(data.status=="true"){
						grid.unmask();
						Search();
						mini.alert("权限组删除成功");
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
