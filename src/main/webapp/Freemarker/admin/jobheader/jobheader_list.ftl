<@admin.page title="专项工作管理">
<@admin.conArea title="专项工作>>专项工作管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>标题:</label></td>
		<td><input class="mini-textbox" required="false" name="jobTitle" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="7">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/jobheader.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/jobheader.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/jobheader.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/jobheader.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">     
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/jobheader.do?command=search"  idField="jobId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn" headerAlign="center">序号</div>
        <div field="jobTitle" width="120" headerAlign="center" align="center" allowSort="true">标题</div>
        <div field="jobImageUrl" width="120" headerAlign="center" align="center" allowSort="true">图片URL</div>
        <div field="jobOrder" width="120" headerAlign="center" align="center" allowSort="true">排序</div>
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
            url: "${base}/admin/jobheader.do?command=createpre",
            title: "专项工作新建", width: 400, height: 290,
            ondestroy: function (action) {
                Search();
            }
        });
    }
    
    function Edit() {
        var rows = grid.getSelecteds();
        
       if (rows.length==1) {
            mini.open({
                url: "${base}/admin/jobheader.do?command=updatepre&jobId="+rows[0].jobId,
                title: "专项工作修改", width: 400, height: 440,
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
    			message: "该专项工作下所有分类和文章也将同时被删除且无法恢复，确定操作吗？",
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
    		idsStr.push(r.jobId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/jobheader.do?command=delete",
    		data: { jobIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("专项工作删除成功");
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