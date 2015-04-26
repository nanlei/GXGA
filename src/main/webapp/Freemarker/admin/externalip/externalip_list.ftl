<@admin.page title="外部IP管理">
<@admin.conArea title="系统管理>>外部IP管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>IP地址:</td>
		<td><input class="mini-textbox" id="externalIP" name="externalIP" style="width:150px;" /></td>
		<td>名称:</td>
		<td><input class="mini-textbox" id="externalIPName" name="externalIPName"  style="width:150px;" /></td>
		<td>描述:</td>
		<td><input class="mini-textbox" id="externalIPDescription" name="externalIPDescription" style="width:150px;" /></td>
	</tr>
	<tr>
		<td>是否锁定:</td>
		<td><input class="mini-combobox" required="false" name="isLock" textField="text" valueField="id" dataField="data" url="/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>		
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/externalip.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/externalip.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/externalip.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/externalip.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" url="/admin/externalip.do?command=search"  idField="externalIPId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="externalIP" width="120" headerAlign="center" align="center" allowSort="true">IP</div>    
		<div field="externalIPName" width="100" headerAlign="center" align="center" allowSort="true">名称</div>
		<div field="externalIPDescription" width="100" headerAlign="center" align="center" allowSort="true">描述</div>
		<div field="externalIPOrder" width="100" headerAlign="center" align="center" allowSort="true">排序</div>
		<div field="isLock" width="100" headerAlign="center" align="center" allowSort="true">是否锁定</div>
		<div field="createByName" width="100" headerAlign="center" align="center" allowSort="false">创建人</div>
		<div field="createByTime" width="100" headerAlign="center" align="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("externalIPId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/externalip.do?command=createpre",
			title: "新建外部IP", width: 420, height: 400,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "${base}/admin/externalip.do?command=updatepre&externalIPId="+rows[0].externalIPId,
        		title: "修改外部IP", width: 420, height: 400,
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
			idsStr.push(r.externalIPId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/externalip.do?command=delete",
			data: { externalIPIds: ids },
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