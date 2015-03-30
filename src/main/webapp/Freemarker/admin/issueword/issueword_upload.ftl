<@admin.page title="昨日要情上传">
<@admin.conArea title="前台综合>>昨日要情>>WORD>>上传" id="form1" fileUpload="true" action="/admin/issueword.do?command=upload">
<@admin.con id="datacon1">
	<tr>
		<td><label>日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="issueDate" style="width:150px;" format="yyyy-MM-dd" showTime="true" /></td>
	</tr>
	<tr>
		<td><label>文件选择:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="Fdata" style="width:150px;" required="true"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="上传" actionName="/admin/issueword.do?command=upload" event="Upload" />
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为昨日要情Word上传页面，请选择附件，然后点击【上传】按钮即可。
</@admin.conArea>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
    function Upload() {
    	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    } 
</@admin.script>
</@admin.page>