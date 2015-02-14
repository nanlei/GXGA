<@admin.page title="专项工作分类管理">
<@admin.conArea title="专项工作>>专项工作分类管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>所属专项工作:</label></td>
		<td><input class="mini-combobox" required="false" name="jobId" style="width:150px;" textField="text" valueField="id"  url="/admin/jobheader.do?command=getjobheader" dataField="data" showNullItem="true" allowInput="false" /></td>
		<td><label>分类标题:</label></td>
		<td><input class="mini-textbox" required="false" name="jobCategoryTitle" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="7">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/jobcategory.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/jobcategory.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/jobcategory.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/jobcategory.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">     
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/jobcategory.do?command=search"  idField="jobCategoryId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn" headerAlign="center">序号</div>
        <div field="jobTitle" width="120" headerAlign="center" align="center" allowSort="true">所属专项工作</div>
        <div field="jobCategoryTitle" width="120" headerAlign="center" align="center" allowSort="true">分类标题</div>
        <div field="jobCategoryOrder" width="120" headerAlign="center" align="center" allowSort="true">排序</div>
        <div field="createByName" width="70" headerAlign="center" align="center" allowSort="false">更新人</div>
        <div field="createByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">更新时间</div>
        <div field="createByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
    
    mini.parse();

    var grid = mini.get("datagrid1");
    Search();
	grid.sortBy("jobOrder", "asc");
	
    function Search() {
    
    	var form = new mini.Form("#datacon1");
    	
    	var data = form.getData();
        var json = mini.encode(data);
        
        grid.load({ condition: json });
        
        grid.gotoPage(grid.pageIndex,grid.pageSize);
        
    }
    
    function Add() {
        mini.open({
            url: "${base}/admin/jobcategory.do?command=createpre",
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
                url: "${base}/admin/jobcategory.do?command=updatepre&jobCategoryId="+rows[0].jobCategoryId,
                title: "分类修改", width: 400, height: 440,
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
    		idsStr.push(r.jobCategoryId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/jobcategory.do?command=delete",
    		data: { jobCategoryIds: ids },
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