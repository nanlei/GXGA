<@admin.page title="通知通报">
<@admin.conArea title="前台综合>>通知通报>>签收明细" id="form1">
<input class="mini-hidden" name="articleId" value="${(articleId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td>状态:</td>
		<td><input id="departmentId" class="mini-combobox" required="false" name="status" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=SIGNSTATUS" dataField="data" showNullItem="true" allowInput="true" /></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/notice.do?command=signsearch" event="Search" icon="icon-search"/>
	<@admin.actBtn name="添加部门" actionName="/admin/notice.do?command=signadddepartmentpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="移除部门" actionName="/admin/notice.do?command=signdeletedepartment" event="Delete" icon="icon-remove"/>
</@admin.searchLeftArea>  
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/notice.do?command=signsearch"  idField="asId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="departmentName" width="60" headerAlign="center" align="center" allowSort="true">签收部门</div>
        <div field="comment" width="200" headerAlign="center" align="center" allowSort="true">签收意见</div>
        <div field="status" width="60" headerAlign="center" align="center" allowSort="false">状态</div>
        <div field="signByName" width="70" headerAlign="center" align="center" allowSort="false">签收人</div>
        <div field="signByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">签收时间</div>
        <div field="signByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("asId", "asc");
	
	function Search() {
		var form = new mini.Form("#form1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "/admin/notice.do?command=signadddepartmentpre&articleId=${articleId}",
			title: "添加签收部门", width: 400, height: 290,
			ondestroy: function (action) {
				Search();
			}
		});
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
    		
    		idsStr.push(r.departmentId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "/admin/notice.do?command=signdeletedepartment",
    		data: { articleId : ${articleId}, departmentIds : ids },
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
</@admin.script>
</@admin.page>