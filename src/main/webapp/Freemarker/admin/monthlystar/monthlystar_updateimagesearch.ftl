<@admin.page title="每月之星修改">
<@admin.conArea title="分局概况>>每月之星>>首页图片" id="fieldset1">
<input class="mini-hidden" name="articleId" id="articleId" value="${(articleId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><span>图片名称:</span></td>
		<td><input id="imageName" class="mini-textbox" name="imageName" style="width:150px;" /></td>
		<td><span>图片描述:</span></td>
		<td><input id="imageDescription" class="mini-textbox" name="imageDescription" style="width:150px;" /></td>
		<td><span>所属目录:</span></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" dataField="data" url="/admin/const.do?constant=CATEGORY" onValueChanged="onCategoryChanged" showNullItem="true" allowInput="true"/></td>
	</tr>
<@admin.searchArea colspan="6">
<@admin.searchRightArea>	
	<@admin.actBtn name="查询" actionName="/admin/image.do?command=unselectedsearch" event="Search"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="图片选择器" id="form2">
<div style="text-align:center;padding-left:5px;padding-right:5px;">
        <table cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">未选择图片：</h4>
                    <@admin.hasPermission actionName="/admin/image.do?command=unselectedsearch">
                    <div id="serachGrid" class="mini-datagrid" style="width:390px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="/admin/image.do?command=unselectedsearch&articleId=${(articleId)?default('')}">
                        <div property="columns">
                            <div type="checkcolumn" ></div>
                            <div field="imageName" width="100" headerAlign="center" allowSort="true">图片名称</div>
                            <div field="imageDescription" width="180" headerAlign="center" allowSort="true">图片描述</div>
                        </div>
                    </div>
                    </@admin.hasPermission>
                </td>
                <td style="padding:5px;" align="center">
                <@admin.hasPermission actionName="/admin/article.do?command=addimage">
            	<input type="button" value=">>" onclick="addSelecteds()" style="width:50px;margin-bottom:2px;"/><br/>
            	</@admin.hasPermission>
            	<@admin.hasPermission actionName="/admin/article.do?command=deleteimage">
            	<input type="button" value="<<" onclick="removeSelecteds()" style="width:50px;"/>
            	</@admin.hasPermission>
                </td>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">已选择图片(滚动时按图片上传先后为序，图片名称为人名)：</h4>
                    <@admin.hasPermission actionName="/admin/image.do?command=selectedsearch">
                    <div id="selectedList" class="mini-datagrid" style="width:390px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="/admin/image.do?command=selectedsearch&articleId=${(articleId)?default('')}">     
                        <div property="columns">
                        	<div type="checkcolumn" ></div>
                            <div field="imageName" width="100" headerAlign="center" allowSort="true">图片名称</div>    
                            <div field="imageDescription" width="180" headerAlign="center" allowSort="true">图片描述</div>
                        </div>
                    </div>
                    </@admin.hasPermission>
                </td>
            </tr>
        </table>
    </div>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form3">
图片选择器中的【>>】按钮可以向该文章中添加图片，【<<】按钮可以从该文章中移除图片
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var serachGrid = mini.get("serachGrid");
	var selectedList = mini.get("selectedList");
	var articleId = mini.get("articleId");
	
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
	    //var totalCount = selectedList.totalCount;
	    
	    //if(totalCount==1){
		    //mini.alert("仅能选择一张图片作为首页图片");
		    //return;	    
	    //}
		
		var rows = serachGrid.getSelecteds();
		
		//if(rows.length>1) {
		    //mini.alert("仅能选择一张图片作为首页图片");
		    //return;
		//}
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.imageId);
			}
			
			var ids = idsStr.join(',');
			
			serachGrid.loading("操作中，请稍后......");

			$.ajax({
				url: "${base}/admin/article.do?command=addimage",
				data: {imageIds: ids, articleId: articleId.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							Search();
							mini.alert("图片添加成功");
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
				idsStr.push(r.imageId);
			}
			
			var ids = idsStr.join(',');
			
			selectedList.loading("操作中，请稍后......");
			
			$.ajax({
				url: "${base}/admin/article.do?command=deleteimage",
				data: { imageIds: ids, articleId: articleId.getValue() },
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							Search();
							mini.alert("图片删除成功");
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