<@admin.page title="分类文章管理" js=["/components/ckfinder/ckfinder.js"]>
<@admin.conArea title="部门综合>>分类文章管理>>新建" id="form1">
<input class="mini-hidden" name="articleId" value="${(article.articleId)?default('')}" />
<@admin.con id="datacon1">
<#if loginUser.departmentId==0>
	<tr>
		<td>部门:</td>
		<td><input class="mini-treeselect" id="departmentId" required="true" name="departmentId" value="${(article.departmentId)?default('')}" onValueChanged="onDepartmentChanged" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="false" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
	</tr>
	<tr>
		<td>分类：</td>
		<td><input class="mini-combobox" id="dcId" required="true" name="dcId" value="${(article.dcId)?default('')}" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/departmentcategory.do?command=getcategorybydepartment&departmentId=${article.departmentId}" showNullItem="true" allowInput="false" /></td>
	</tr>
<#else>
<input class="mini-hidden" name="departmentId" value="${(loginUser.departmentId)?default('')}" />
	<tr>
		<td>分类：</td>
		<td><input class="mini-combobox" id="dcId" required="true" name="dcId" value="${(article.dcId)?default('')}" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/departmentcategory.do?command=getcategorybydepartment&departmentId=${loginUser.departmentId}" showNullItem="true" allowInput="false" /></td>
	</tr>
</#if>
	<tr>
		<td><label>文章标题:</label></td>
		<td><input class="mini-textbox" required="true" name="articleTitle" value="${(article.articleTitle)?default('')}" style="width:300px;" /></td>
	</tr>
	<tr>
		<td><label>文章排序:</label></td>
		<td><input class="mini-textbox" required="true" name="articleOrder" value="${(article.articleOrder)?default('')}" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>视频所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" value="${(video.categoryId)?default('')}" textField="text" valueField="id"  url="${base}/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="false"/></td>
	</tr>
	<tr>
		<td><label>视频:</label></td>
		<td><input id="videoId" name="videoId" class="mini-combobox" style="width:150px;" value="${(article.videoId)?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/video.do?command=ajax&categoryId=${(video.categoryId)?default('')}" showNullItem="true" allowInput="false" required="false"/></td>
	</tr>
	<tr>
		<td colspan="2"><@admin.ckeditor id="articleContent" name="articleContent" value="${(article.articleContent)?default('请输入内容（部门综合）')}" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/branchdepartment.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为分类文章新建页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
若要添加附件，请先保存，然后在【修改】页面进行勾选。
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	if(mini.get("videoId").getValue()==0){
		mini.get("videoId").setValue('');
	}

	<#if loginUser.departmentId==0>
	function onDepartmentChanged(){
		var departmentId=mini.get("departmentId");
		var id=departmentId.getValue();
		
		var dcId=mini.get("dcId");
		
		dcId.setValue("");
		
		var url="${base}/admin/departmentcategory.do?command=getcategorybydepartment&departmentId="+id;
		
		dcId.setUrl(url);
    	dcId.select(0);
	}
	</#if>

	function onCategoryChanged(e) {
		var categoryId=mini.get("categoryId");
    	var videoId=mini.get("videoId");

    	var id=categoryId.getValue();
    	
    	videoId.setValue("");
    	
    	var url="${base}/admin/video.do?command=ajax&categoryId="+id;
    	
    	videoId.setUrl(url);
    	videoId.select(0);
    }
		
    function Save() {
    	var data = form.getData();
    	
    	form.validate();
    	
    	if (form.isValid() == false) { 
    		return;
    	}
    	
    	var json = mini.encode(data);
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/branchdepartment.do?command=update",
    		data: { object: json, articleContent : editor.getData()},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("文章修改成功");
    				}
    			}catch(e){
    				form.unmask();
    				mini.alert(text);
    			}
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    			form.unmask();
    			mini.alert(jqXHR.responseText);
    		}
    	});
    }
</@admin.script>
</@admin.page>