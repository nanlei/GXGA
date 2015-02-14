<@admin.page title="设备维修">
<@admin.conArea title="网上办公>>设备维修>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>所属部门：</td>
		<td><input class="mini-treeselect" required="false" name="departmentId" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
		<td>设备名称:</td>
		<td><input class="mini-textbox" required="false" name="assetName"  style="width:150px;" /></td>
		<td>发布人:</td>
		<td><input class="mini-textbox" required="false" name="createByName"  style="width:150px;" /></td>
		<td>发布时间:</td>
		<td><input class="mini-datepicker" required="false" name="createByTime" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/assetrepair.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="查看" actionName="/admin/assetrepair.do?command=detail" event="Detail" icon="icon-find"/>
	<@admin.actBtn name="回复" actionName="/admin/assetrepair.do?command=replypre" event="Reply" icon="icon-goto"/>
	<@admin.actBtn name="新建" actionName="/admin/assetrepair.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="处理" actionName="/admin/assetrepair.do?command=dispose" event="Dispose" icon="icon-ok"/>
	<@admin.actBtn name="修改" actionName="/admin/assetrepair.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/assetrepair.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/assetrepair.do?command=search"  idField="arId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="arCode" width="90" headerAlign="center" align="center" allowSort="true">维修代码</div>
        <div field="departmentName" width="100" headerAlign="center" align="center" allowSort="true">所属部门</div>
        <div field="assetName" width="140" headerAlign="center" align="center" allowSort="true">设备名称</div>
        <div field="assetLocation" width="80" headerAlign="center" align="center" allowSort="true">设备所在地</div>
        <div field="statusName" width="50" headerAlign="center" align="center" allowSort="true">状态</div>
        <div field="createByName" width="50" headerAlign="center" align="center" allowSort="false">发布人</div>
        <div field="createByTime" width="110" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">发布时间</div>
        <div field="createByIP" width="60" headerAlign="center" align="center" allowSort="false">IP</div>
        <div field="feedbackByName" width="50" headerAlign="center" align="center" allowSort="false">回复人</div>
        <div field="feedbackByTime" width="110" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">回复时间</div>
        <div field="feedbackByIP" width="60" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("arId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Detail(){
		var rows = grid.getSelecteds();
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/assetrepair.do?command=detail&arId="+rows[0].arId,
				title: "查看维修申请："+rows[0].arCode, width: 320, height: 470,
				ondestroy: function (action) {
					Search();
	            }
	        });		
		} else {
			mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据查看",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});		
		}
	}
	
	function Reply(){
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts!='RUN'){
				mini.open({
					url: "${base}/admin/assetrepair.do?command=replypre&arId="+rows[0].arId,
					title: "回复维修申请："+rows[0].arCode, width: 400, height: 420,
					ondestroy: function (action) {
						Search();
		            }
		        });		
			}else{
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}		
		} else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据进行操作",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        } 
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/assetrepair.do?command=createpre",
			title: "发布留言", width: 420, height: 320,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Dispose(){
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts=='NEW'){
		    	grid.loading("操作中，请稍后......");
		    	
		    	$.ajax({
		    		url: "${base}/admin/assetrepair.do?command=dispose",
		    		data: { arId: rows[0].arId },
		    		success: function (data) {
		    			try{
		    				if(data.status=="true"){
		    					grid.unmask();
		    					Search();
		    					mini.alert("操作成功");
		    				}else if(data.status=="notnew"){
		    					grid.unmask();
		    					Search();
		    					mini.alert("当前状态不允许执行此操作");
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
			}else{
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}
        } else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据进行操作",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        } 	
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts!='NEW'){
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}else{
				mini.open({
					url: "${base}/admin/assetrepair.do?command=updatepre&arId="+rows[0].arId,
					title: "修改维修申请："+rows[0].arCode, width: 400, height: 320,
					ondestroy: function (action) {
						Search();
	                }
	            });
			}
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
    		
    		idsStr.push(r.arId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/assetrepair.do?command=delete",
    		data: { arIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("数据删除成功");
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