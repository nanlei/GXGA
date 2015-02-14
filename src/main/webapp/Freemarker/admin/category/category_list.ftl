<@admin.page title="目录管理">
<@admin.conArea title="系统管理>>目录管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><span style="padding-right:10px">目录名称:</span><input class="mini-textbox" name="categoryName"  style="width:150px;"/></td>
	</tr>
<@admin.searchArea colspan="1">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/category.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/category.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/category.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/category.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">     
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/category.do?command=search"  idField="categoryId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn" headerAlign="center">序号</div>
        <div field="categoryName" width="120" headerAlign="center" align="center" allowSort="true">目录名称</div>    
        <div field="categoryOrder" width="120" headerAlign="center" align="center" allowSort="true">目录排序</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
    
    mini.parse();

    var grid = mini.get("datagrid1");
    Search();
	grid.sortBy("categoryId", "desc");
	
    function Search() {
    
    	var form = new mini.Form("#datacon1");
    	
    	var data = form.getData();
        var json = mini.encode(data);
        
        grid.load({ condition: json });
        
        grid.gotoPage(grid.pageIndex,grid.pageSize);
        
    }
    
    function Add() {
        mini.open({
            url: "/admin/category.do?command=createpre",
            title: "目录新建", width: 400, height: 260,
            ondestroy: function (action) {
                Search();
            }
        });
    }
    
    function Edit() {
        var rows = grid.getSelecteds();
        
       if (rows.length==1) {
            mini.open({
                url: "/admin/category.do?command=updatepre&categoryId="+rows[0].categoryId,
                title: "编辑目录", width: 400, height: 260,
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
    		idsStr.push(r.categoryId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("操作中，请稍后......");;
    	
    	$.ajax({
    		url: "/admin/category.do?command=delete",
    		data: { categoryIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("目录删除成功");
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