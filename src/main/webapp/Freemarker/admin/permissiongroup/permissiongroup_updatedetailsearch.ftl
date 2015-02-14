<@admin.page title="权限组信息">
<@admin.conArea title="系统管理>>权限组管理>>权限组添加权限" id="fieldset1">
<input class="mini-hidden" name="permissionGroupId" id="permissionGroupId" value="${(permissionGroupId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><span>权限名称:</span></td>
		<td><input id="permissionName" class="mini-textbox" name="permissionName" style="width:150px;" /></td>
		<td><span>权限URL:</span></td>
		<td><input id="permissionUrl" class="mini-textbox" name="permissionUrl" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><span>所属目录:</span></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" dataField="data" url="/admin/const.do?constant=CATEGORY" onValueChanged="onCategoryChanged" showNullItem="true" allowInput="true"/></td>
		<td><span>所属菜单:</span></td>
		<td><input id="upperId" name="upperId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" showNullItem="true" allowInput="true"/></td>
	</tr>
<@admin.searchArea colspan="10">
<@admin.searchRightArea>	
	<@admin.actBtn name="查询" actionName="/admin/permissiongroup.do?command=unselectedsearch" event="Search"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="权限选择器" id="form2">
    <div style="text-align:center;padding-left:5px;padding-right:5px;">
        <table cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">未选择权限：</h4>
                    <div id="serachGrid" class="mini-datagrid" style="width:350px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="/admin/permissiongroup.do?command=unselectedsearch&permissionGroupId=${(permissionGroupId)?default('')}">
                        <div property="columns">
                            <div type="checkcolumn" ></div>
                            <div field="permissionName" width="130" headerAlign="center" allowSort="true">权限名称</div>
                            <div field="permissionUrl" width="150" headerAlign="center" allowSort="true">权限URL</div>
                        </div>
                    </div>
                </td>
                <td style="padding:5px;" align="center">
            	<input type="button" value=">>" onclick="addSelecteds()" style="width:50px;margin-bottom:2px;"/><br/>
            	<input type="button" value="<<" onclick="removeSelecteds()" style="width:50px;"/>
                </td>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">已选择权限：</h4>
                    <div id="selectedList" class="mini-datagrid" style="width:350px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="/admin/permissiongroup.do?command=selectedsearch&permissionGroupId=${(permissionGroupId)?default('')}">     
                        <div property="columns">
                        	<div type="checkcolumn" ></div>
                            <div field="permissionName" width="130" headerAlign="center" allowSort="true">权限名称</div>    
                            <div field="permissionUrl" width="150" headerAlign="center" allowSort="true">权限URL</div>
                        </div>
                    </div>                       
                </td>
            </tr>
        </table>
    </div>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form3">
上半部的查询选择功能可对【权限选择器】中的【未选择权限】进行查询<br/>
查询选择器中的【>>】按钮可以向该权限组中添加权限，【<<】按钮可以从该权限组中移除权限
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var serachGrid = mini.get("serachGrid");
	var selectedList = mini.get("selectedList");
	var permissionGroupId = mini.get("permissionGroupId");
	
	function onCategoryChanged(e) {
		var categoryId=mini.get("categoryId");
    	var upperId=mini.get("upperId");

    	var id=categoryId.getValue();
    	
    	upperId.setValue("");
    	
    	var url="/admin/permission.do?command=ajax&categoryId="+id;
    	
    	upperId.setUrl(url);
    	upperId.select(0);
    }
	
	Search();
	
	function Search() {
		var cnoform1 = new mini.Form("#datacon1");
		
		var condata1 = cnoform1.getData();
		var conjson1 = mini.encode(condata1);
		
		serachGrid.load({condition: conjson1});
		
		serachGrid.gotoPage(serachGrid.pageIndex,serachGrid.pageSize);
		
		selectedList.load();
		
		selectedList.gotoPage(selectedList.pageIndex,selectedList.pageSize);
	}
	
	function addSelecteds() {
		
		var rows = serachGrid.getSelecteds();
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.permissionId);
			}
			
			var ids = idsStr.join(',');
			
			serachGrid.loading("操作中，请稍后......");
			
			$.ajax({
				url: "/admin/permissiongroup.do?command=addpermission",
				data: { permissionIds: ids , permissionGroupId: permissionGroupId.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							Search();
							mini.alert("权限添加成功");
						}
					}catch(e){
						selectedList.unmask();
						serachGrid.unmask();
						mini.alert(data);
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
					grid.unmask();
					mini.alert(jqXHR.responseText);
				}
			});
		} else {
			mini.alert("请至少选择一条数据进行添加");
		}
	}
	
	function removeSelecteds() {
		var rows = selectedList.getSelecteds();
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.permissionId);
			}
			
			var ids = idsStr.join(',');
			
			selectedList.loading("操作中，请稍后......");
			
			$.ajax({
				url: "/admin/permissiongroup.do?command=deletepermission",
				data: { permissionIds: ids , permissionGroupId: permissionGroupId.getValue() },
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							Search();
							mini.alert("权限删除成功");
						}
					}catch(e){
						selectedList.unmask();
						serachGrid.unmask();
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
					grid.unmask();
					mini.alert(jqXHR.responseText);
				}
			});
		} else {
			mini.alert("请至少选择一条数据删除");
		}
	}
</@admin.script>
</@admin.page>