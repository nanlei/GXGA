<@admin.page title="职位管理">
<@admin.conArea title="人力资源>>职位管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>职位名称:</td>
		<td><input class="mini-textbox" id="positionName" name="positionName" style="width:150px;" /></td>
		<td>职位类别:</td>
		<td><input class="mini-combobox" id="positionCategory" name="positionCategory" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITIONCATEGORY" showNullItem="true" allowInput="true"/></td>
		<td>职位类型:</td>
		<td><input class="mini-combobox" id="positionType" name="positionType" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITIONTYPE" showNullItem="true" allowInput="true"/></td>
	</tr>
	<tr>
		<td>职位编码:</td>
		<td><input class="mini-textbox" id="positionCode" name="positionCode" style="width:150px;" /></td>
		<td>职位排序:</td>
		<td><input class="mini-textbox" id="positionOrder" name="positionOrder" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/position.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/position.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/position.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/position.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" 
	allowResize="true" url="${base}/admin/position.do?command=search"  idField="positionId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="positionName" width="120" headerAlign="center" align="center" allowSort="true">职位名称</div>    
		<div field="positionCategory" width="100" headerAlign="center" align="center" allowSort="true">职位类别</div>
		<div field="positionType" width="100" headerAlign="center" align="center" allowSort="true">职位类型</div>
		<div field="positionCode" width="100" headerAlign="center" align="center" allowSort="true">职位编码</div>
		<div field="positionOrder" width="100" headerAlign="center" align="center" allowSort="true">职位排序</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("positionOrder", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/position.do?command=createpre",
			title: "新建职位", width: 430, height: 400,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "${base}/admin/position.do?command=updatepre&positionId="+rows[0].positionId,
        		title: "修改职位", width: 430, height: 400,
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
			idsStr.push(r.positionId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/position.do?command=delete",
			data: { positionIds: ids },
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
</@admin.script>
</@admin.page>