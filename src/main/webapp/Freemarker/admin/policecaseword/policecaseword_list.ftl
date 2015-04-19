<@admin.page title="警情研判">
<@admin.conArea title="前台综合>>警情研判>>WORD>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>日期:</td>
		<td colspan="3">
			<input class="mini-datepicker" required="false" name="wordDate" style="width:150px;" format="yyyy-MM-dd"/>
		</td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/policecaseword.do?command=search" event="Search" icon="icon-search" />
</@admin.searchLeftArea>  
<@admin.searchRightArea>
	<@admin.actBtn name="上传" actionName="/admin/policecaseword.do?command=uploadpre" event="Add" icon="icon-add" />
	<@admin.actBtn name="删除" actionName="/admin/policecaseword.do?command=delete" event="Delete" icon="icon-remove" />
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/policecaseword.do?command=search"  idField="wordId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="wordTitle" width="60" headerAlign="center" align="center" allowSort="false">标题</div>
        <div field="wordDate" width="60" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd">日期</div>
        <div field="filePath" width="200" headerAlign="center" allowSort="true">存储路径</div>
        <div field="wordOrder" width="70" headerAlign="center" align="center" allowSort="false">排序</div>
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
	
	grid.sortBy("w.wordId", "desc");

	function Add() {
		mini.open({
			url: "${base}/admin/policecaseword.do?command=uploadpre",
			title: "上传警情研判Word", width: 400, height: 350,
			ondestroy: function (action) {
				Search();
			}
		});
	}
		
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
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
    		
    		idsStr.push(r.wordId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/policecaseword.do?command=delete",
    		data: { wordIds: ids },
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