<@admin.page title="每日警情修改" js=["/components/ckfinder/ckfinder.js"]>
<@admin.conArea title="前台综合>>每日警情>>修改" id="form1">
<input class="mini-hidden" name="articleId" value="${(article.articleId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>文章标题:</label></td>
		<td><input class="mini-textbox" required="true" name="articleTitle" style="width:300px;" value="${article.articleTitle?default('')}"/></td>
	</tr>
	<tr>
		<td><label>文章排序:</label></td>
		<td><input class="mini-textbox" required="true" name="articleOrder" style="width:150px;" value="${article.articleOrder?default('')}"/></td>
	</tr>
	<tr>
		<td><label>文章日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="articleDate" style="width:150px;" value="${article.articleDate?default('')}" format="yyyy-MM-dd" showTime="true" /></td>
	</tr>
	<tr>
		<td colspan="2"><@admin.ckeditor id="articleContent" name="articleContent" value="${article.articleContent?default('请输入内容（每日警情）')}" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/policecase.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为每日警情修改页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
若要添加附件，然后在【图片明细】页面进行勾选。
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
    		url: "${base}/admin/policecase.do?command=update",
    		data: { object : json, articleContent : editor.getData() },
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("每日警情文本修改成功");
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