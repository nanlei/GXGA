<@admin.page title="待办事项">
<@admin.conArea title="个人中心>>待办事项>>查询" id="fieldset1">
<@admin.con id="datacon1">

<@admin.searchArea colspan="8">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/selfservice.do?command=useractionsearch" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="处理" actionName="/admin/selfservice.do?command=useractiondispose" event="Dispose" icon="icon-edit"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true" 
	url="${base}/admin/selfservice.do?command=useractionsearch"  idField="actionId" multiSelect="true">
	<div property="columns">
		<div type="checkcolumn"></div>
		<div type="indexcolumn" headerAlign="center">序号</div>
		<div field="actionType" width="120" headerAlign="center" align="center" allowSort="true">事由</div>    
		<div field="ref" width="100" headerAlign="center" align="center" allowSort="true">内容</div>
		<div field="actionTime" width="140" headerAlign="center" align="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">请求时间</div>
	</div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("actionId", "asc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data);
		
		grid.load({ condition: json });
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Dispose() {
		var rows = grid.getSelecteds();
        
        if (rows.length==1) {
        	mini.open({
        		url: "/admin/selfservice.do?command=useractiondisposepre&actionId="+rows[0].actionId,
        		title: "处理待办事项：" + rows[0].actionType+" "+rows[0].ref, width: 400, height: 230,
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
</@admin.script>
</@admin.page>