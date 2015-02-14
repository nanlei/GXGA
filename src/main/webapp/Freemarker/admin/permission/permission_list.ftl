<@admin.page title="权限管理">
<@admin.conArea title="系统管理>>权限管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>权限名称:</td>
		<td><input class="mini-textbox" name="permissionName"  style="width:150px;"/></td>
		<td>权限描述:</td>
		<td><input class="mini-textbox" name="permissionDescription" style="width:150px;"/></td>
		<td>URL查询:</td>
		<td><input class="mini-textbox" name="permissionUrl" style="width:150px;"/></td>
		<td>是否菜单:</td>
		<td><input id="isMenu" class="mini-combobox" name="isMenu" style="width:150px;" textField="text" valueField="id" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false"/></td>
	</tr>
	<tr>
		<td>所属目录:</td>
		<td><input id="categoryId" class="mini-combobox"  name="categoryId" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged"/></td>
		<td>所属菜单:</td>
		<td><input id="upperId" class="mini-combobox" name="upperId" style="width:150px;" textField="text" valueField="id" dataField="data" showNullItem="true" allowInput="true"/></td>
	</tr>
<@admin.searchArea colspan="10">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/permission.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="新建" actionName="/admin/permission.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/permission.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/permission.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/permission.do?command=search"  idField="permissionId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="permissionName" width="110" headerAlign="center" allowSort="true">权限名称</div>
        <div field="permissionUrl" width="180" headerAlign="center" allowSort="true">权限URL</div>
        <div field="permissionDescription" width="150" headerAlign="center" allowSort="true">权限描述</div>
        <div field="permissionOrder" width="80" headerAlign="center" align="center" allowSort="true">权限排序</div>
        <div field="categoryName" width="80" headerAlign="center" align="center" allowSort="false">所属目录</div>
        <div field="isMenu" width="50" headerAlign="center" align="center" allowSort="true">是否菜单</div>
        <div field="upperPermissionName" width="80" headerAlign="center" align="center" allowSort="false">所属菜单</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("permissionId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "/admin/permission.do?command=createpre",
			title: "新建权限", width: 400, height: 400,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "/admin/permission.do?command=updatepre&permissionId="+rows[0].permissionId,
				title: "修改权限", width: 400, height: 400,
				ondestroy: function (action) {
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
    			message: "请注意：与之关联的权限组中也将移除该权限。确定删除吗？",
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
    		
    		idsStr.push(r.permissionId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "/admin/permission.do?command=delete",
    		data: { permissionIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("权限删除成功");
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
    
    function onCategoryChanged(e){
    	var categoryId=mini.get("categoryId");
    	var upperId=mini.get("upperId");

    	var id=categoryId.getValue();
    	
    	upperId.setValue("");
    	
    	var url="/admin/permission.do?command=ajax&categoryId="+id;
    	
    	upperId.setUrl(url);
    	upperId.select(0);
    }
</@admin.script>
</@admin.page>