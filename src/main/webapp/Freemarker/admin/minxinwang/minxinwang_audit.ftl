<@admin.page title="民心网">
<@admin.conArea title="网上办公>>民心网>>日志>>查询" id="form1">
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
	<@admin.actBtn name="查询" actionName="/admin/minxinwang.do?command=auditsearch" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="删除" actionName="/admin/minxinwang.do?command=auditdelete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/minxinwang.do?command=auditsearch"  idField="auditId" multiSelect="true" 
    <#if ftlUtil.hasPermission('/admin/minxinwang.do?command=auditdetail','${loginUser.roleId}')>
    onshowrowdetail="onShowRowDetail" autoHideRowDetail="true" enableHotTrack="false">
    <#else>
    >
    </#if>
    <div property="columns">
    	<#if ftlUtil.hasPermission('/admin/minxinwang.do?command=auditdetail','${loginUser.roleId}')>
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
本页面为民心网诉求问题请示单日志页面。点击加号可以看到日志变更详细信息，日志信息只能删除。<br>
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
            url: "${base}/admin/minxinwang.do?command=auditdetail&auditId="+row.auditId,
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
    		
    		idsStr.push(r.auditId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/minxinwang.do?command=auditdelete",
    		data: { auditIds: ids },
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