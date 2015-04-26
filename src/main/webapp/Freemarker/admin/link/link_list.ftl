<@admin.page title="链接管理">
<@admin.conArea title="系统管理>>链接管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>链接地址:</td>
		<td><input class="mini-textbox" required="false" name="linkUrl"  style="width:150px;" /></td>
		<td>链接描述:</td>
		<td><input class="mini-textbox" required="false" name="linkDescription" style="width:150px;" /></td>
		<td>链接类型:</td>
		<td><input id="linkType" class="mini-combobox" required="false" name="linkType" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=LINKTYPE" dataField="data" showNullItem="true" allowInput="true" /></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/link.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/link.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/link.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/link.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/link.do?command=search"  idField="linkId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="linkUrl" width="220" headerAlign="center" allowSort="true">链接地址</div>
        <div field="linkDescription" width="120" headerAlign="center" allowSort="true">链接描述</div>
        <div field="linkType" width="100" headerAlign="center" align="center" allowSort="true">链接类型</div>
        <div field="linkOrder" width="100" headerAlign="center" align="center" allowSort="true">排序</div>
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
	
	grid.sortBy("linkId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/link.do?command=createpre",
			title: "新建链接", width: 400, height: 300,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/link.do?command=updatepre&linkId="+rows[0].linkId,
				title: "修改链接", width: 400, height: 300,
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
    		
    		idsStr.push(r.linkId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/link.do?command=delete",
    		data: { linkIds: ids },
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