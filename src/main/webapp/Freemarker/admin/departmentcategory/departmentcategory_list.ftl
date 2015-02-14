<@admin.page title="分类管理">
<@admin.conArea title="部门综合>>分类管理>>查询" id="form1">
<@admin.con id="datacon1">
<#if loginUser.departmentId==0>
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" required="false" name="departmentId" value="1" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="false" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
<#else>
<input class="mini-hidden" name="departmentId" value="${(loginUser.departmentId)?default('')}" />
</#if>
<@admin.searchArea colspan="7">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/departmentcategory.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/departmentcategory.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/departmentcategory.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/departmentcategory.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">     
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/departmentcategory.do?command=search"  idField="dcId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn" headerAlign="center">序号</div>
        <div field="departmentName" width="120" headerAlign="center" align="center" allowSort="true">部门</div>
        <div field="dcName" width="120" headerAlign="center" align="center" allowSort="true">目录名称</div>
        <div field="dcOrder" width="120" headerAlign="center" align="center" allowSort="true">目录排序</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
    
    mini.parse();

    var grid = mini.get("datagrid1");
    Search();
	grid.sortBy("dcOrder", "asc");
	
    function Search() {
    
    	var form = new mini.Form("#datacon1");
    	
    	var data = form.getData();
        var json = mini.encode(data);
        
        grid.load({ condition: json });
        
        grid.gotoPage(grid.pageIndex,grid.pageSize);
        
    }
    
    function Add() {
        mini.open({
            url: "${base}/admin/departmentcategory.do?command=createpre",
            title: "分类新建", width: 400, height: 290,
            ondestroy: function (action) {
                Search();
            }
        });
    }
    
    function Edit() {
        var rows = grid.getSelecteds();
        
       if (rows.length==1) {
            mini.open({
                url: "${base}/admin/departmentcategory.do?command=updatepre&dcId="+rows[0].dcId+"&departmentId="+rows[0].departmentId,
                title: "编辑分类", width: 400, height: 290,
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
    			message: "该分类下的文章也将同时被删除，确定删除吗？",
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
    		idsStr.push(r.dcId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/departmentcategory.do?command=delete",
    		data: { dcIds: ids, departmentId : "${(departmentId)?default('${loginUser.departmentId}')}" },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("分类删除成功");
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