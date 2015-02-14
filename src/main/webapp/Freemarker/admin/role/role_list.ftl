<@admin.page title="角色管理">
<@admin.conArea title="系统管理>>角色管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
    <tr>
       	<td>角色名称:</td>
		<td><input class="mini-textbox" name="roleName"  style="width:150px;"/></td>
		<td>角色描述:</td>
		<td><input class="mini-textbox" name="roleDescription"  style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/role.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="新建" actionName="/admin/role.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/role.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/role.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" 
    	url="${base}/admin/role.do?command=search"  idField="roleId" multiSelect="true">
        <div property="columns">
            <div type="checkcolumn"></div>
            <div type="indexcolumn" headerAlign="center">序号</div>
            <div field="roleName" width="140" headerAlign="center" allowSort="true">角色名称</div>    
            <div field="roleDescription" width="140" headerAlign="center" allowSort="true">角色描述</div>                          
            <div field="roleOrder" width="80" headerAlign="center" align="center" allowSort="true">角色排序</div>
        </div>
    </div>
</@admin.dataArea> 
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("roleId", "desc");
	
	function Search() {
	
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
        
    function Add() {
    	mini.open({
    		url: "${base}/admin/role.do?command=createpre",
    		title: "新建角色", width: 420, height: 290,
    		ondestroy: function (action) {
    			Search();
    		}
    	});
    }
    
    function Edit() {
    	var rows = grid.getSelecteds();
    	
    	if (rows.length==1) {
    		mini.open({
    			url: "${base}/admin/role.do?command=updatedispatcher&roleId="+rows[0].roleId,
    			title: "角色编辑 : "+rows[0].roleName, width: 840, height: 530,
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
    	
    	for (var i = 0, l = rows.length; i < l; i++) {
			var r = rows[i];
			if(r.roleId==1){
				mini.alert('超级管理员不能删除');
				return;
			}
		}
    	
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
			idsStr.push(r.roleId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/role.do?command=delete",
			data: { roleIds: ids },
			success: function (data) {
				try{
					if(data.status=="true"){
						grid.unmask();
						Search();
						mini.alert("删除成功");
					}else if(data.status="exists"){
						grid.unmask();
						mini.alert("相关角色被用户使用，无法删除");
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