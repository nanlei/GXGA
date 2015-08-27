<@admin.page title="通知通报新建" js=["/components/ckfinder/ckfinder.js"]>
<@admin.conArea title="前台综合>>通知通报>>红头文件>>新建" id="form1">
<@admin.con id="datacon1">
	<tr>
		<td colspan="2"><img src="${base}/images/redhead_notice.png"></td>
	</tr>
	<tr>
		<td><label>单位:</label></td>
		<td><input class="mini-textbox" required="true" name="topUnit" style="width:300px;" />&nbsp;&nbsp;(例：分局办公室)</td>
	</tr>
	<tr>
		<td><label>文件号:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeNo" style="width:300px;" />&nbsp;&nbsp;(例：第XX号)</td>
	</tr>
	<tr>
		<td><label>顶部日期:</label></td>
		<td><input class="mini-textbox" required="true" name="topDate" style="width:300px;" />&nbsp;&nbsp;(例：XXXX年X月X日)</td>
	</tr>
	<tr>
		<td><label>文章标题:</label></td>
		<td><input class="mini-textbox" required="true" name="articleTitle" style="width:300px;" />&nbsp;&nbsp;(例：关于XX通报)</td>
	</tr>
	<tr>
		<td><label>主办单位:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeHost" style="width:300px;" />&nbsp;&nbsp;(例：信访科)</td>
	</tr>
	<tr>
		<td><label>联系人:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeContact" style="width:300px;" />&nbsp;&nbsp;(例：XXX)</td>
	</tr>
	<tr>
		<td><label>联系电话:</label></td>
		<td><input class="mini-textbox" required="true" name="noticePhone" style="width:300px;" />&nbsp;&nbsp;(例：XXXX)</td>
	</tr>
	<tr>
		<td><label>发布时间:</label></td>
		<td><input class="mini-textbox" required="true" name="bottomDate" style="width:300px;" />&nbsp;&nbsp;(例：XXXX年X月X日)</td>
	</tr>
	<tr>
		<td><label>文章排序:</label></td>
		<td><input class="mini-textbox" required="true" name="articleOrder" style="width:150px;" /></td>
	</tr>
	<tr>
		<td><label>文章日期:</label></td>
		<td><input class="mini-datepicker" required="true" name="articleDate" style="width:150px;" format="yyyy-MM-dd" showTime="true" />&nbsp;&nbsp;(请和上面日期保持一致)</td>
	</tr>
	<tr>
		<td colspan="2"><@admin.ckeditor id="articleContent" name="articleContent" value="请输入内容（通知通报）" /></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/notice.do?command=redheadcreate" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为通知通报新建页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
若要添加附件，请先保存，然后在【修改】页面进行勾选。<br>
若要添加需要签收的部门，请先保存，然后在【修改】页面进行勾选。
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
    		url: "${base}/admin/notice.do?command=redheadcreate",
    		data: { object: json, articleContent : editor.getData()},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("通知通报红头文件新建成功");
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