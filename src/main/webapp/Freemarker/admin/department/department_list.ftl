<@admin.page title="部门管理">
<@admin.conArea title="人力资源>>部门管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>部门名称:</td>
		<td><input class="mini-textbox" id="departmentName" name="departmentName" style="width:150px;" /></td>
		<td>部门类型:</td>
		<td><input class="mini-combobox" id="departmentType" name="departmentType" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=DEPARTMENTTYPE" showNullItem="true" allowInput="true"/></td>
		<td>部门编码:</td>
		<td><input class="mini-textbox" id="departmentCode" name="departmentCode" style="width:150px;" /></td>
	</tr>
	<tr>
		<td>所属上级:</td>
		<td><input class="mini-treeselect" id="upperId" name="upperId" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
		<td>是否叶子节点:</td>
		<td><input class="mini-combobox" id="isLeaf" name="isLeaf" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true"/></td>
		<td>部门排序:</td>
		<td><input class="mini-textbox" id="departmentOrder" name="departmentOrder" style="width:150px;" /></td>

	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/department.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/department.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/department.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/department.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" 
	url="${base}/admin/department.do?command=search"  idField="departmentId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="departmentName" width="120" headerAlign="center" align="center" allowSort="true">部门名称</div>    
		<div field="departmentType" width="100" headerAlign="center" align="center" allowSort="true">部门类型</div>
		<div field="departmentCode" width="100" headerAlign="center" align="center" allowSort="true">部门编码</div>
		<div field="upperDepartmentName" width="100" headerAlign="center" align="center" allowSort="true">所属上级</div>
		<div field="isLeaf" width="100" headerAlign="center" align="center" allowSort="true">是否叶子节点</div>
		<div field="departmentOrder" width="100" headerAlign="center" align="center" allowSort="true">部门排序</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("departmentOrder", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "/admin/department.do?command=createpre",
			title: "部门新建", width: 430, height: 400,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "/admin/department.do?command=updatepre&departmentId="+rows[0].departmentId,
        		title: "部门修改", width: 430, height: 400,
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
    	
 		for (var i = 0, l = rows.length; i < l; i++) {
			var deptId=rows[i].departmentId;
			if(deptId==0 || deptId==1 || deptId==21){
				mini.alert("不允许删除该部门");
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
			idsStr.push(r.departmentId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "/admin/department.do?command=delete",
			data: { departmentIds: ids },
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

    function onCloseClick(e) {
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    }
</@admin.script>
</@admin.page>