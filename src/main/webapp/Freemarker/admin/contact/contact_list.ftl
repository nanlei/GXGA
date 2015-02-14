<@admin.page title="通讯录">
<@admin.conArea title="人力资源>>通讯录>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>姓名:</td>
		<td><input class="mini-textbox" required="false" name="name"  style="width:150px;"/></td>
		<td>部门:</td>
		<td><input id="departmentId" class="mini-combobox" required="false" name="departmentId" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=DEPARTMENT" dataField="data" showNullItem="true" allowInput="false" /></td>
		<td>科室:</td>
		<td><input class="mini-textbox" required="false" name="subDepartment"  style="width:150px;"/></td>
		<td>职位:</td>
		<td><input id="positionId" class="mini-combobox" required="false" name="positionId" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=POSITION" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td>内线:</td>
		<td><input class="mini-textbox" required="false" name="internalNo"  style="width:150px;"/></td>
		<td>外线:</td>
		<td><input class="mini-textbox" required="false" name="externalNo"  style="width:150px;"/></td>
		<td>手机:</td>
		<td><input class="mini-textbox" required="false" name="mobile"  style="width:150px;"/></td>
		<td>虚拟号:</td>
		<td><input class="mini-textbox" required="false" name="virtualNo"  style="width:150px;"/></td>
	</tr>
	<tr>
		<td>联通手机:</td>
		<td><input class="mini-textbox" required="false" name="unicomNo"  style="width:150px;"/></td>
		<td>联通虚拟号:</td>
		<td><input class="mini-textbox" required="false" name="unicomVirtualNo"  style="width:150px;"/></td>
		<td>房间号:</td>
		<td><input class="mini-textbox" required="false" name="officeNo"  style="width:150px;"/></td>
		<td>宅电:</td>
		<td><input class="mini-textbox" required="false" name="homePhone"  style="width:150px;"/></td>		
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/contact.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="新建" actionName="/admin/contact.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/contact.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/contact.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="true"
    url="${base}/admin/contact.do?command=search"  idField="contactId" multiSelect="true" frozenStartColumn="0" frozenEndColumn="6"
    showColumnsMenu="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="departmentName" width="100" headerAlign="center" align="center" allowSort="true">部门</div>
        <div field="subDepartment" width="100" headerAlign="center" align="center" allowSort="true">科室</div>
        <div field="name" width="100" headerAlign="center" align="center" allowSort="false">姓名</div>
        <div field="positionName" width="100" headerAlign="center" align="center" allowSort="false">职位</div>
        <div field="contactOrder" width="70" headerAlign="center" align="center" allowSort="false">排序</div>
        <div field="internalNo" width="120" headerAlign="center" align="center" allowSort="false">内线</div>
        <div field="externalNo" width="120" headerAlign="center" align="center" allowSort="false">外线</div>
        <div field="mobile" width="120" headerAlign="center" align="center" allowSort="false">手机</div>
        <div field="virtualNo" width="120" headerAlign="center" align="center" allowSort="false">虚拟号</div>
        <div field="unicomNo" width="120" headerAlign="center" align="center" allowSort="false">联通手机</div>
        <div field="unicomVirtualNo" width="120" headerAlign="center" align="center" allowSort="false">联通手虚拟号</div>
        <div field="officeNo" width="120" headerAlign="center" align="center" allowSort="false">房间号</div>
        <div field="homePhone" width="120" headerAlign="center" align="center" allowSort="false">宅电</div>
        <div field="createByName" width="120" headerAlign="center" align="center" allowSort="false">更新人</div>
        <div field="createByTime" width="150" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">更新时间</div>
        <div field="createByIP" width="150" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("contactOrder", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/contact.do?command=createpre",
			title: "新建通讯录", width: 400, height: 550,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/contact.do?command=updatepre&contactId="+rows[0].contactId,
				title: "修改通讯录："+rows[0].name, width: 400, height: 600,
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
    		
    		idsStr.push(r.contactId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/contact.do?command=delete",
    		data: { contactIds: ids },
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