<@admin.page title="综合考评新建" js=["/components/ckfinder/ckfinder.js"]>
<@admin.conArea title="前台综合>>综合考评>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td><label>文章标题:</label></td>
		<td><input class="mini-textbox" required="true" name="articleTitle" style="width:300px;" /></td>
	</tr>
	<tr>
		<td><label>文章排序:</label></td>
		<td><input class="mini-textbox" required="true" name="articleOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>文章日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="articleDate" style="width:150px;" format="yyyy-MM-dd" showTime="true" /></td>
	</tr>
	<tr>
		<td colspan="2"><@admin.ckeditor id="articleContent" name="articleContent" value="请输入内容（综合考评）" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/evaluation.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为综合考评新建页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
若要添加附件，请先保存，然后在【修改】页面进行勾选。
</@admin.conArea>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
    function Save() {
    	var data = form.getData();
    	
    	form.validate();
    	
    	if (form.isValid() == false) { 
    		return;
    	}
    	
    	var json = mini.encode(data);
    	
    	form.loading("保存中，请稍后......");
    	
    	$.ajax({
    		url: "/admin/evaluation.do?command=create",
    		data: { object: json, articleContent : editor.getData()},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("综合考评新建成功");
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