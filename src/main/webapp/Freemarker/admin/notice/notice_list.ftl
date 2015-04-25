<@admin.page title="通知通报">
<@admin.conArea title="前台综合>>通知通报>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>标题:</td>
		<td><input class="mini-textbox" required="false" name="articleTitle"  style="width:150px;"/></td>
		<td>状态:</td>
		<td><input id="articleStatus" class="mini-combobox" required="false" name="articleStatus" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=ARTICLESTATUS" dataField="data" showNullItem="true" allowInput="true" /></td>
	</tr>
	<tr>
		<td>创建时间:</td>
		<td colspan="3">
			<input class="mini-datepicker" required="false" name="createByTimeStart" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
			&nbsp;~&nbsp;
			<input class="mini-datepicker" required="false" name="createByTimeEnd" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/notice.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="新建" actionName="/admin/notice.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/notice.do?command=updatedispatcher" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="签收" actionName="/admin/notice.do?command=signpre" event="Sign" icon="icon-ok"/>
	<@admin.actBtn name="删除" actionName="/admin/notice.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/notice.do?command=search"  idField="articleId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="articleTitle" width="200" headerAlign="center" allowSort="true">标题</div>
        <div field="articleOrder" width="60" headerAlign="center" align="center" allowSort="true">排序</div>
        <div field="articleStatus" width="60" headerAlign="center" align="center" allowSort="false">状态</div>
        <div field="articleDate" width="70" headerAlign="center" align="center" allowSort="true" dateFormat="yyyy-MM-dd">文章日期</div>
        <div field="createByName" width="70" headerAlign="center" align="center" allowSort="false">更新人</div>
        <div field="createByTime" width="100" headerAlign="center" align="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">更新时间</div>
        <div field="createByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
        <div field="pageView" width="60" headerAlign="center" align="center" allowSort="false">浏览次数</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("createByTime", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/notice.do?command=createpre",
			title: "新建通知通报", width: 900, height: 600,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/notice.do?command=updatedispatcher&articleId="+rows[0].articleId,
				title: "修改通知通报："+rows[0].articleTitle, width: 900, height: 610,
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
    		
    		idsStr.push(r.articleId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/notice.do?command=delete",
    		data: { articleIds: ids },
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