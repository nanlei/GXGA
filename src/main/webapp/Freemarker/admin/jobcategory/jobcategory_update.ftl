<@admin.page title="专项工作分类管理">
<@admin.conArea title="专项工作>>专项工作分类管理>>修改" id="form1">
<input class="mini-hidden" name="jobCategoryId" value="${(jobCategory.jobCategoryId)?default('')}" />

<@admin.con id="datacon1">
	<tr>
		<td><label>所属专项工作:</label></td>
		<td><input class="mini-combobox" required="true" name="jobId" style="width:150px;" value="${(jobCategory.jobId)?default('')}" textField="text" valueField="id"  url="${base}/admin/jobheader.do?command=getjobheader" dataField="data" showNullItem="true" allowInput="false" /></td>
	</tr>
	<tr>
		<td><label>分类标题:</label></td>
		<td><input class="mini-textbox" required="true" name="jobCategoryTitle" style="width:150px;" value="${(jobCategory.jobCategoryTitle)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>分类排序:</label></td>
		<td><input class="mini-textbox" required="true" name="jobCategoryOrder" style="width:150px;" value="${(jobCategory.jobCategoryOrder)?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/jobcategory.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为专项工作分类修改页面，请按照上述内容填写修改后的相关信息，然后点击【保存】按钮即可。
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
    		url: "${base}/admin/jobcategory.do?command=update",
    		data: { object: json },
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("专项工作分类修改成功");
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