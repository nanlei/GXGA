<@admin.page title="机构设置">
<@admin.conArea title="分局概况>>分局大事记" id="fieldset1">
<@admin.searchArea colspan="1">
<@admin.searchRightArea>
	<@admin.actBtn name="保存文章" actionName="/admin/branchoverview.do?command=updatememorabilia" event="Save" icon="icon-save"/>
</@admin.searchRightArea>
</@admin.searchArea>
<@admin.con id="datacon1">
<#-- articleType & articleCode -->
<input class="mini-hidden" name="articleType" id="articleType" value="FJGK"/>
<input class="mini-hidden" name="articleCode" id="articleCode" value="FGDSJ"/>
<table style="width:100%;">
	<tr>
		<td><span>最后更新人:</span></td><td><input class="mini-textbox" value="${memorabilia.createByName?default('')}" allowInput="false" style="width:150px;" /></td>
		<td><span>更新时间:</span></td><td><input class="mini-textbox" value="${memorabilia.createByTime?default('')}" allowInput="false" style="width:150px;" /></td>
		<td><span>更新IP:</span></td><td><input class="mini-textbox" value="${memorabilia.createByIP?default('')}" allowInput="false" style="width:150px;" /></td>
		<td><span>浏览次数:</span></td><td><input class="mini-textbox" value="${memorabilia.pageView?default('0')}" allowInput="false" style="width:150px;" /></td>
	</tr>
</table>
<fieldset id="fieldset2" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset2');"><img src="/images/admin/arrow_u.gif" title="隐藏" border="0">图片选择器</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<div id="imageSearch">
<table style="width:100%;">
	<tr>
		<td><span>图片名称:</span></td>
		<td><input id="imageName" class="mini-textbox" name="imageName" style="width:150px;" /></td>
		<td><span>图片描述:</span></td>
		<td><input id="imageDescription" class="mini-textbox" name="imageDescription" style="width:150px;" /></td>
		<td><span>所属目录:</span></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=CATEGORY" onValueChanged="onCategoryChanged" showNullItem="true" allowInput="true"/></td>
	</tr>
</table>
<@admin.searchArea colspan="6">
<@admin.searchRightArea>	
	<@admin.actBtn name="查询" actionName="/admin/image.do?command=unselectedsearch" event="ImageSearch"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</div>
<div>
    <div style="text-align:center;padding-left:5px;padding-right:5px;">
        <table cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">未选择图片：</h4>
                    <@admin.hasPermission actionName="/admin/image.do?command=unselectedsearch">
                    <div id="imageSerachGrid" class="mini-datagrid" style="width:450px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="${base}/admin/image.do?command=unselectedsearch&articleType=FJGK&articleCode=FJDSJ">
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
            	<input type="button" value=">>" onclick="addSelectedImage()" style="width:50px;margin-bottom:2px;"/><br/>
            	</@admin.hasPermission>
            	<@admin.hasPermission actionName="/admin/article.do?command=deleteimage">
            	<input type="button" value="<<" onclick="removeSelectedImage()" style="width:50px;"/>
            	</@admin.hasPermission>
                </td>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">已选择图片：</h4>
                    <@admin.hasPermission actionName="/admin/image.do?command=selectedsearch">
                    <div id="imageSelectedList" class="mini-datagrid" style="width:450px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="${base}/admin/image.do?command=selectedsearch&articleType=FJGK&articleCode=FJDSJ">     
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
</div>
</ul>
</fieldset>

<fieldset id="fieldset3" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset3');"><img src="/images/admin/arrow_u.gif" title="隐藏" border="0">附件选择器</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">

<div id="attachmentSearch">
<table style="width:100%;">
	<tr>
		<td><span>附件名称:</span></td>
		<td><input id="attachmentName" class="mini-textbox" name="attachmentName" style="width:150px;" /></td>
		<td><span>附件描述:</span></td>
		<td><input id="attachmentDescription" class="mini-textbox" name="attachmentDescription" style="width:150px;" /></td>
		<td><span>所属目录:</span></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=CATEGORY" onValueChanged="onCategoryChanged" showNullItem="true" allowInput="true"/></td>
	</tr>
</table>
<@admin.searchArea colspan="6">
<@admin.searchRightArea>	
	<@admin.actBtn name="查询" actionName="/admin/attachment.do?command=unselectedsearch" event="AttachmentSearch"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</div>
<div>
    <div style="text-align:center;padding-left:5px;padding-right:5px;">
        <table cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">未选择附件：</h4>
                    <@admin.hasPermission actionName="/admin/attachment.do?command=unselectedsearch">
                    <div id="attachmentSerachGrid" class="mini-datagrid" style="width:450px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="${base}/admin/attachment.do?command=unselectedsearch&articleType=FJGK&articleCode=FJDSJ">
                        <div property="columns">
                            <div type="checkcolumn" ></div>
                            <div field="attachmentName" width="100" headerAlign="center" allowSort="true">附件名称</div>
                            <div field="attachmentDescription" width="180" headerAlign="center" allowSort="true">附件描述</div>
                        </div>
                    </div>
                    </@admin.hasPermission>
                </td>
                <td style="padding:5px;" align="center">
                <@admin.hasPermission actionName="/admin/article.do?command=addattachment">
            	<input type="button" value=">>" onclick="addSelectedAttachment()" style="width:50px;margin-bottom:2px;"/><br/>
            	</@admin.hasPermission>
            	<@admin.hasPermission actionName="/admin/article.do?command=deleteattachment">
            	<input type="button" value="<<" onclick="removeSelectedAttachment()" style="width:50px;"/>
            	</@admin.hasPermission>
                </td>
                <td align="center">
                    <h4 style="margin:0;line-height:22px;font-size:13px;text-align:left;">已选择附件：</h4>
                    <@admin.hasPermission actionName="/admin/attachment.do?command=selectedsearch">
                    <div id="attachmentSelectedList" class="mini-datagrid" style="width:450px;height:180px;" showPageSize="false" showPageIndex="false" pagerStyle="padding:2px;" multiSelect="true" url="${base}/admin/attachment.do?command=selectedsearch&articleType=FJGK&articleCode=FJDSJ">
                        <div property="columns">
                        	<div type="checkcolumn" ></div>
                            <div field="attachmentName" width="100" headerAlign="center" allowSort="true">附件名称</div>    
                            <div field="attachmentDescription" width="180" headerAlign="center" allowSort="true">附件描述</div>
                        </div>
                    </div>
                    </@admin.hasPermission>
                </td>
            </tr>
        </table>
    </div>
</div>

</ul>
</fieldset>

<fieldset id="fieldset4" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset4');"><img src="/images/admin/arrow_u.gif" title="隐藏" border="0">文本编辑器</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">

<@admin.ckeditor id="articleContent" name="articleContent" value="${memorabilia.articleContent?default('请输入内容')}" />

</ul>
</fieldset>
</@admin.con>
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var articleType=mini.get("articleType");
	var articleCode=mini.get("articleCode");

	ImageSearch();
	AttachmentSearch();

	//Image JS
	function ImageSearch() {
		var serachGrid = mini.get("imageSerachGrid");
		var selectedList = mini.get("imageSelectedList");
		
		var cnoform1 = new mini.Form("#imageSearch");
		
		var condata1 = cnoform1.getData();
		var conjson1 = mini.encode(condata1);
		
		serachGrid.load({condition: conjson1});
		
		serachGrid.gotoPage(serachGrid.pageIndex,serachGrid.pageSize);
		
		selectedList.load();
		
		selectedList.gotoPage(selectedList.pageIndex,selectedList.pageSize);
	}

	function addSelectedImage(){
		var serachGrid = mini.get("imageSerachGrid");
		var selectedList = mini.get("imageSelectedList");
		
		var rows = serachGrid.getSelecteds();
		
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
				data: { imageIds: ids , articleType: articleType.getValue(), articleCode: articleCode.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							ImageSearch();
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
			mini.alert("请至少选择一个图片进行添加");
		}
	}
	
	function removeSelectedImage(){
		var serachGrid = mini.get("imageSerachGrid");
		var selectedList = mini.get("imageSelectedList");
		
		var rows = selectedList.getSelecteds();
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.imageId);
			}
			
			var ids = idsStr.join(',');
			
			serachGrid.loading("操作中，请稍后......");
			
			$.ajax({
				url: "${base}/admin/article.do?command=deleteimage",
				data: { imageIds: ids , articleType: articleType.getValue(), articleCode: articleCode.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							ImageSearch();
							mini.alert("图片删除成功");
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
			mini.alert("请至少选择一个图片删除");
		}
	}
	
	//Attachment JS
	function AttachmentSearch() {
		var serachGrid = mini.get("attachmentSerachGrid");
		var selectedList = mini.get("attachmentSelectedList");
		
		var cnoform1 = new mini.Form("#attachmentSearch");
		
		var condata1 = cnoform1.getData();
		var conjson1 = mini.encode(condata1);
		
		serachGrid.load({condition: conjson1});
		
		serachGrid.gotoPage(serachGrid.pageIndex,serachGrid.pageSize);
		
		selectedList.load();
		
		selectedList.gotoPage(selectedList.pageIndex,selectedList.pageSize);
	}
	
	function addSelectedAttachment(){
		var serachGrid = mini.get("attachmentSerachGrid");
		var selectedList = mini.get("attachmentSelectedList");
		
		var rows = serachGrid.getSelecteds();
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.attachmentId);
			}
			
			var ids = idsStr.join(',');
			
			serachGrid.loading("操作中，请稍后......");
			
			$.ajax({
				url: "${base}/admin/article.do?command=addattachment",
				data: { attachmentIds: ids , articleType: articleType.getValue(), articleCode: articleCode.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							AttachmentSearch();
							mini.alert("附件添加成功");
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
			mini.alert("请至少选择一个附件进行添加");
		}
	}
	
	function removeSelectedAttachment(){
		var serachGrid = mini.get("attachmentSerachGrid");
		var selectedList = mini.get("attachmentSelectedList");
		
		var rows = selectedList.getSelecteds();
		
		if (rows.length > 0) {
			var idsStr = [];
			
			for (var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				idsStr.push(r.attachmentId);
			}
			
			var ids = idsStr.join(',');
			
			serachGrid.loading("操作中，请稍后......");
			
			$.ajax({
				url: "${base}/admin/article.do?command=deleteattachment",
				data: { attachmentIds: ids , articleType: articleType.getValue(), articleCode: articleCode.getValue()},
				cache:false,
				success: function (data) {
					try{
						if(data.status=="true"){
							selectedList.unmask();
							serachGrid.unmask();
							AttachmentSearch();
							mini.alert("图片删除成功");
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
			mini.alert("请至少选择一个附件删除");
		}
	}
	
	function Save(){
    	$.ajax({
    		url: "${base}/admin/branchoverview.do?command=updatememorabilia",
    		data: { articleContent : editor.getData() },
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					mini.alert("分局大事记修改成功");
    				}
    			}catch(e){
    				mini.alert(text);
    			}
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    			mini.alert(jqXHR.responseText);
    		}
    	});	
	}
</@admin.script>
</@admin.page>