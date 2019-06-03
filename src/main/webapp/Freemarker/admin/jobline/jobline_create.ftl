<@admin.page title="专项工作文章管理" js=["/components/ckfinder/ckfinder.js"]>
<@admin.conArea title="专项工作>>专项工作文章管理>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>专项行动：</label></td>
		<td><input class="mini-combobox" id="jobId" required="true" name="jobId" style="width:150px;" onValueChanged="onJobChanged" textField="text" valueField="id" dataField="data" url="${base}/admin/jobline.do?command=getjobheader" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>子类别:</label></td>
		<td><input id="jobCategoryId" class="mini-combobox" required="true" name="jobCategoryId" style="width:150px;" textField="text" valueField="id" dataField="data" showNullItem="true" allowInput="false"/></td>		
	</tr>
	<tr>
		<td><label>文章标题:</label></td>
		<td><input class="mini-textbox" required="true" name="articleTitle" style="width:300px;" /></td>
	</tr>
	<tr>
		<td><label>文章排序:</label></td>
		<td><input class="mini-textbox" required="true" name="articleOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>视频所属目录:</label></td>
		<td><input id="categoryId" name="categoryId" class="mini-combobox" style="width:150px;" textField="text" valueField="id"  url="${base}/admin/const.do?constant=CATEGORY" dataField="data" showNullItem="true" allowInput="true" onValueChanged="onCategoryChanged" required="false"/></td>
	</tr>
	<tr>
		<td><label>视频:</label></td>
		<td><input id="videoId" name="videoId" class="mini-combobox" style="width:150px;" textField="text" valueField="id" dataField="data" showNullItem="true" allowInput="false" required="false"/></td>
	</tr>
	<tr>
		<td colspan="2"><@admin.ckeditor id="articleContent" name="articleContent" value="请输入内容（专项工作）" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/jobline.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为专项工作文章新建页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
若要添加附件，请先保存，然后在【修改】页面进行勾选。
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");

	function onJobChanged(e) {
		var jobId=mini.get("jobId");
    	var jobCategoryId=mini.get("jobCategoryId");

    	var id=jobId.getValue();
    	
    	jobCategoryId.setValue("");
    	
    	var url="${base}/admin/jobline.do?command=ajax&jobId="+id;
    	
    	jobCategoryId.setUrl(url);
    	jobCategoryId.select(0);
    }
    
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
    		url: "${base}/admin/jobline.do?command=create",
    		data: { object: json, articleContent : editor.getData()},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("专项工作文章新建成功");
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