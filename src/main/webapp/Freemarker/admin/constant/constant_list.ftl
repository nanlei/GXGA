<@admin.page title="常量管理">
<@admin.conArea title="系统管理>>常量管理>>查询" id="fieldset1">
<@admin.con id="datacon1">
	<tr>
		<td>常量类型:</td>
		<td><input class="mini-textbox" id="constantType" name="constantType" style="width:150px;" /></td>
		<td>常量值:</td>
		<td><input class="mini-textbox" id="constantValue" name="constantValue"  style="width:150px;" /></td>
		<td>常量名称:</td>
		<td><input class="mini-textbox" id="constantName" name="constantName" style="width:150px;" /></td>
	</tr>
	<tr>	
		<td>是否锁定:</td>
		<td><input class="mini-combobox" required="false" name="isLock" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/></td>		
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/constant.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/constant.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/constant.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/constant.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" url="${base}/admin/constant.do?command=search"  idField="constantId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="constantType" width="120" headerAlign="center" align="center" allowSort="true">类型</div>    
		<div field="constantValue" width="100" headerAlign="center" align="center" allowSort="true">值</div>
		<div field="constantName" width="100" headerAlign="center" align="center" allowSort="true">名称</div>
		<div field="constantOrder" width="100" headerAlign="center" align="center" allowSort="true">排序</div>
		<div field="isLock" width="100" headerAlign="center" align="center" allowSort="true">是否锁定</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("constantOrder", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/constant.do?command=createpre",
			title: "新建常量", width: 420, height: 400,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "${base}/admin/constant.do?command=updatepre&constantId="+rows[0].constantId,
        		title: "修改常量", width: 420, height: 400,
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
			idsStr.push(r.constantId);
		}
		
		var ids = idsStr.join(',');
		
		grid.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/constant.do?command=delete",
			data: { constantIds: ids },
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