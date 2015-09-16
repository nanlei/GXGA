<@admin.page title="局长信箱">
<@admin.conArea title="网上办公>>局长信箱>>日志>>查询" id="form1">
<input class="mini-hidden" name="transactionId" value="${(transactionId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td>日志时间:</td>
		<td colspan="3">
			<input class="mini-datepicker" required="false" name="auditByTimeStart" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
			&nbsp;~&nbsp;
			<input class="mini-datepicker" required="false" name="auditByTimeEnd" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/officalmailbox.do?command=auditsearch" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	

</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/officalmailbox.do?command=auditsearch"  idField="auditId" multiSelect="true" 
    <#if ftlUtil.hasPermission('/admin/officalmailbox.do?command=auditdetail','${loginUser.roleId}')>
    onshowrowdetail="onShowRowDetail" autoHideRowDetail="true" enableHotTrack="false">
    <#else>
    >
    </#if>
    <div property="columns">
    	<#if ftlUtil.hasPermission('/admin/officalmailbox.do?command=auditdetail','${loginUser.roleId}')>
    	<div type="expandcolumn"></div>
    	</#if>
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="auditByName" width="100" headerAlign="center" align="center" allowSort="false">操作人</div>
        <div field="auditByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">日志时间</div>
        <div field="auditByIP" width="100" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.conArea title="页面提示" id="form2">
本页面为局长信箱通知单日志页面。点击加号可以看到日志变更详细信息。<br>
</@admin.conArea>
<style type="text/css">
.detailForm td
{
    line-height:22px;
    font-size:13px;
    font-family:Verdana;
    border:1px solid black;
}
.detailForm
{
  border-collapse:collapse;
}
</style>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("auditId", "asc");
	
	function Search() {
	
		var form = new mini.Form("#form1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({condition:json});
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function onShowRowDetail(e){
		var grid = e.sender;
        var row = e.record;

        var td = grid.getRowDetailCellEl(row);

        $.ajax({
            url: "${base}/admin/officalmailbox.do?command=auditdetail&auditId="+row.auditId,
            success: function (data) {
                td.innerHTML = "";

                var header='<table class="detailForm" style="width:100%; border:1px sold;">'
                          +'<tr style="text-align:center;font-weight:bold;"><td style="width:33%;">字段名</td><td style="width:33%;">原值</td><td style="width:33%;">新值</td></tr>';
                var rows = '';
                var footer = '</table>';
                var list=data.auditConfig;
                for(var i=0;i<list.length;i++){
                	rows = rows + '<tr style="text-align:center"><td>'+list[i].fieldName+'</td><td>'+list[i].oldValue+'</td><td>'+list[i].newValue+'</td></tr>';
                }
                
                td.innerHTML = header+rows+footer;
            }
        });
	}
</@admin.script>
</@admin.page>