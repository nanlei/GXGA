<@admin.page title="回音壁">
<@admin.conArea title="网上办公>>回音壁>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>留言内容:</td>
		<td><input class="mini-textbox" required="false" name="mailSubject"  style="width:150px;" /></td>
		<td>发布人:</td>
		<td><input class="mini-textbox" required="false" name="createByName"  style="width:150px;" /></td>
		<td>发布时间:</td>
		<td><input class="mini-datepicker" required="false" name="createByTime" style="width:150px;" /></td>
		<td>首页是否显示:</td>
		<td><input id="isPublic" name="isPublic" class="mini-combobox" style="width:150px;" textField="text" valueField="id" url="/admin/const.do?constant=YN" dataField="data" showNullItem="true" allowInput="false" required="false"/></td>
	</tr>
	<tr>
		<td><b>与我相关：</b></td>
		<td><input id="searchMe" name="searchMe" class="mini-combobox" style="width:150px;" textField="text" valueField="id" url="${base}/data/searchme.txt" dataField="data" showNullItem="true" allowInput="false" required="false"/></td>
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/mailbox.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="评价" actionName="/admin/mailbox.do?command=evaluatepre" event="Evaluate" icon="icon-tip"/>
	<@admin.actBtn name="查看" actionName="/admin/mailbox.do?command=detail" event="Detail" icon="icon-find"/>
	<@admin.actBtn name="回复" actionName="/admin/mailbox.do?command=replypre" event="Reply" icon="icon-goto"/>
	<@admin.actBtn name="新建" actionName="/admin/mailbox.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="处理" actionName="/admin/mailbox.do?command=dispose" event="Dispose" icon="icon-ok"/>
	<@admin.actBtn name="修改" actionName="/admin/mailbox.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/mailbox.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/mailbox.do?command=search"  idField="mailId" multiSelect="true">
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

	function Evaluate(){
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts == 'RUN'){
				mini.open({
					url: "${base}/admin/mailbox.do?command=evaluatepre&mailId="+rows[0].mailId,
					title: "评价回音壁留言："+rows[0].mailSubject, width: 530, height: 520,
					ondestroy: function (action) {
						Search();
		            }
		        });		
			}else{
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}		
		} else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据进行操作",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        }
	}
	
	function Detail(){
		var rows = grid.getSelecteds();
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/mailbox.do?command=detail&mailId="+rows[0].mailId,
				title: "查看回音壁留言："+rows[0].mailSubject, width: 520, height: 520,
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
	
	function Reply(){
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts!='RUN'){
				mini.open({
					url: "${base}/admin/mailbox.do?command=replypre&mailId="+rows[0].mailId,
					title: "回复回音壁留言："+rows[0].mailSubject, width: 530, height: 520,
					ondestroy: function (action) {
						Search();
		            }
		        });		
			}else{
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}		
		} else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据进行操作",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        } 
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/mailbox.do?command=createpre",
			title: "发布回音壁留言", width: 570, height: 390,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Dispose(){
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			if(rows[0].sts=='NEW'){
		    	grid.loading("操作中，请稍后......");
		    	
		    	$.ajax({
		    		url: "${base}/admin/mailbox.do?command=dispose",
		    		data: { mailId: rows[0].mailId },
		    		success: function (data) {
		    			try{
		    				if(data.status=="true"){
		    					grid.unmask();
		    					Search();
		    					mini.alert("操作成功");
		    				}else if(data.status=="notnew"){
		    					grid.unmask();
		    					Search();
		    					mini.alert("当前状态不允许执行此操作");
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
			}else{
	        	mini.showMessageBox({
	        		title:"提示",
	        		message:"当前状态不允许执行此操作",
	        		buttons:["ok"],
	        		iconCls:"mini-messagebox-info"
	        	});				
			}
        } else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选择一条数据进行操作",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        } 	
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
					url: "${base}/admin/mailbox.do?command=updatepre&mailId="+rows[0].mailId,
					title: "修改留言："+rows[0].mailSubject, width: 570, height: 420,
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