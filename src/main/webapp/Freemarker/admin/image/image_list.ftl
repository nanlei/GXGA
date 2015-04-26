<@admin.page title="图片管理">
<@admin.conArea title="系统管理>>图片管理>>查询" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td>图片名称:</td>
		<td><input class="mini-textbox" required="false" name="imageName"  style="width:150px;"/></td>
		<td>图片描述:</td>
		<td><input class="mini-textbox" required="false" name="imageDescription" style="width:150px;"/></td>
		<td>创建人:</td>
		<td><input class="mini-textbox" required="false" name="createByName" style="width:150px;"/></td>
		<td>创建IP:</td>
		<td><input class="mini-textbox" required="false" name="createByIP" style="width:150px;"/></td>
	</tr>
	<tr>
		<td>所属目录:</td>
		<td><input id="categoryId" class="mini-combobox" required="false" name="categoryId" style="width:150px;" textField="text" valueField="id"  url="/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged"/></td>
		<td>创建时间:</td>
		<td colspan="3">
			<input class="mini-datepicker" required="false" name="createByTimeStart" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
			&nbsp;~&nbsp;
			<input class="mini-datepicker" required="false" name="createByTimeEnd" style="width:150px;" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		
	</tr>
<@admin.searchArea colspan="8">
<@admin.searchLeftArea>	
	<@admin.actBtn name="查询" actionName="/admin/image.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>  
<@admin.searchRightArea>	
	<@admin.actBtn name="新建" actionName="/admin/image.do?command=uploadpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/image.do?command=updatepre" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/image.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2"> 
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="/admin/image.do?command=search"  idField="imageId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"headerAlign="center">序号</div>
        <div field="imageName" width="110" headerAlign="center" allowSort="true">图片名称</div>
        <div field="imageUrl" width="150" headerAlign="center" allowSort="true">存储路径</div>
        <div field="imageOrder" width="80" headerAlign="center" align="center" allowSort="true">图片排序</div>
        <div field="categoryName" width="80" headerAlign="center" align="center" allowSort="false">所属目录</div>
        <div field="createByName" width="80" headerAlign="center" align="center" allowSort="false">创建人</div>
        <div field="createByTime" width="80" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
        <div field="createByIP" width="80" headerAlign="center" align="center" allowSort="false">IP</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
	mini.parse();
	
	var grid = mini.get("datagrid1");
	Search();
	
	grid.sortBy("imageId", "desc");
	
	function Search() {
		var form = new mini.Form("#datacon1");
		
		var data = form.getData();
		var json = mini.encode(data); 
		
		grid.load({ condition: json });
		
		grid.gotoPage(grid.pageIndex,grid.pageSize);
	}
	
	function Add() {
		mini.open({
			url: "${base}/admin/image.do?command=uploadpre",
			title: "新建图片", width: 400, height: 350,
			ondestroy: function (action) {
				Search();
			}
		});
	}
	
	function Edit() {
		var rows = grid.getSelecteds();
		
		if (rows.length==1) {
			mini.open({
				url: "${base}/admin/image.do?command=updatepre&imageId="+rows[0].imageId,
				title: "修改图片", width: 400, height: 520,
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
    			message: "请注意：与之关联的所有文章中也将移除该图片。确定删除吗？",
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
    		
    		idsStr.push(r.imageId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("删除中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/image.do?command=delete",
    		data: { imageIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("图片删除成功");
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