<@admin.page title="人员管理">
<@admin.conArea title="人力资源>>人员管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>姓名:</td>
		<td><input class="mini-textbox" id="employeeName" name="employeeName" style="width:150px;" /></td>
		
		<#--
		<td>性别:</td>
		<td><input class="mini-combobox" id="employeeGender" name="employeeGender" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=GENDER" showNullItem="true" allowInput="true" /></td>
		-->
	</tr>
	<tr>
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/employee.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="详细|修改" actionName="/admin/employee.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/employee.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" url="${base}/admin/employee.do?command=search"  idField="employeeId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="userName" width="50" headerAlign="center" align="center" allowSort="true">登录名</div>
		<div field="employeeName" width="50" headerAlign="center" align="center" allowSort="true">姓名</div>    
		<div field="employeeGender" width="30" headerAlign="center" align="center" allowSort="true">性别</div>
		<div field="departmentName" width="30" headerAlign="center" align="center" allowSort="true">部门</div>
		<div field="employeeIdNo" width="100" headerAlign="center" align="center" allowSort="true">身份证号</div>
		<div field="employeePoliceNo" width="100" headerAlign="center" align="center" allowSort="true">警号</div>
		<div field="isDeptAdmin" width="50" headerAlign="center" align="center" allowSort="true">是否部门管理员</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("employeeOrder", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}

	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "${base}/admin/employee.do?command=updatepre&employeeId="+rows[0].employeeId,
        		title: "人员修改", width: 940, height: 500,
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
			idsStr.push(r.employeeId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/employee.do?command=delete",
			data: { employeeIds: ids },
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