<@admin.page title="局长信箱">
<@admin.conArea title="个人中心>>局长信箱>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>留言内容:</td>
		<td><input class="mini-textbox" required="false" name="mailSubject"  style="width:150px;" /></td>
		<td>发布时间:</td>
		<td><input class="mini-datepicker" required="false" name="createByTime" style="width:150px;" /></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/selfservice.do?command=mailboxsearch" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/selfservice.do?command=mailboxcreatepre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/selfservice.do?command=mailboxupdatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/selfservice.do?command=mailboxdelete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/selfservice.do?command=mailboxsearch"  idField="mailId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="mailSubject" width="220" headerAlign="center" align="center" allowSort="true">留言标题</div>
        <div field="statusName" width="50" headerAlign="center" align="center" allowSort="true">状态</div>
        <div field="createByName" width="70" headerAlign="center" align="center" allowSort="false">发布人</div>
        <div field="createByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">发布时间</div>
        <div field="createByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
        <div field="commentByName" width="70" headerAlign="center" align="center" allowSort="false">回复人</div>
        <div field="commentByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">回复时间</div>
        <div field="commentByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("mailId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/selfservice.do?command=mailboxcreatepre",
			title: "发布留言", width: 570, height: 320,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts!='NEW'){
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}else{
				mini.open({
					url: "${base}/admin/selfservice.do?command=mailboxupdatepre&mailId="+rows[0].mailId,
					title: "修改留言："+rows[0].mailSubject, width: 570, height: 320,
					ondestroy: function (action) {
						Search();
	                }
	            });
			}
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
    		
    		idsStr.push(r.mailId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/mailbox.do?command=delete",
    		data: { mailIds: ids },
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