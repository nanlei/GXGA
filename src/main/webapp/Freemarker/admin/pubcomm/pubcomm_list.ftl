<@admin.page title="公众交流平台">
<@admin.conArea title="网上办公>>公众交流平台>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>编号:</td>
		<td><input class="mini-textbox" required="false" name="transactionNo"  style="width:150px;"/></td>
		<td>标题:</td>
		<td><input class="mini-textbox" required="false" name="transactionTitle" style="width:150px;"/></td>
		<td>状态:</td>
		<td><input id="transactionStatus" class="mini-combobox" required="false" name="transactionStatus" style="width:150px;" textField="text" valueField="id" url="${base}/data/externaltransaction_sts.txt" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td>更新时间:</td>
		<td colspan="3">
			<input class="mini-datepicker" required="false" name="modifiedByTimeStart" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
			&nbsp;~&nbsp;
			<input class="mini-datepicker" required="false" name="modifiedByTimeEnd" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/pubcomm.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="查看" actionName="/admin/pubcomm.do?command=detaildispatcher" event="Detail" icon="icon-find"/>
	<@admin.actBtn name="新建" actionName="/admin/pubcomm.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/pubcomm.do?command=updatedispatcher" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/pubcomm.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/pubcomm.do?command=search"  idField="transactionId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="transactionNo" width="70" headerAlign="center" align="center" allowSort="false">编号</div>
        <div field="transactionTitle" width="200" headerAlign="center" allowSort="true">标题</div>
        <div field="transactionStatus" width="60" headerAlign="center" align="center" allowSort="true">状态</div>
        <div field="transactionOrder" width="60" headerAlign="center" align="center" allowSort="true">排序</div>
        <div field="modifiedByName" width="70" headerAlign="center" align="center" allowSort="false">更新人</div>
        <div field="modifiedByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">更新时间</div>
        <div field="modifiedByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	
	Search();
	
	grid.sortBy("transactionOrder", "asc");
	
	function Search() {
	
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({condition:json});
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Detail(){
		var rows = grid.getSelecteds();
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/pubcomm.do?command=detaildispatcher&transactionId="+rows[0].transactionId,
				title: "查看公众交流平台回执单："+rows[0].transactionTitle+" ("+rows[0].transactionNo+")", width: 900, height: 600,
				ondestroy: function (action) {
					Search();
	            }
	        });		
		} else {
			mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据查看",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});		
		}
	}
		
	function Add() {
		mini.open({
			url: "${base}/admin/pubcomm.do?command=createpre",
			title: "新建公众交流平台回执单", width: 900, height: 600,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/pubcomm.do?command=updatedispatcher&transactionId="+rows[0].transactionId,
				title: "修改公众交流平台回执单："+rows[0].transactionTitle+" ("+rows[0].transactionNo+")", width: 900, height: 600,
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
    		
    		idsStr.push(r.transactionId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/pubcomm.do?command=delete",
    		data: { transactionIds: ids },
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