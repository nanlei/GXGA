<@admin.page title="民意网新建">
<@admin.conArea title="网上办公>>民意网>>新建" id="form1">
<fieldset id="fieldset1" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset1');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">转办通知单</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<@admin.con id="datacon1">
	<tr>
		<td colspan="4"><h1 style="text-align:center">高新园区公安分局网上事项转办通知单</h1></td>
	</tr>
	<tr>
		<td colspan="4" style="text-align:center">单号：<input class="mini-textbox" required="true" name="config_noticeNo" style="width:200px;" emptyText="例：(2014)第35号" /></td>
	</tr>
	<tr>
		<td width="60"><label>签发单位：</label></td>
		<td style="text-align:left"><input class="mini-textbox" required="true" name="config_noticeUnit" style="width:200px;" emptyText="例：分局办公室" /></td>
		<td colspan="2" style="text-align:right">签发日期：<input class="mini-textbox" required="true" name="config_noticeDate" style="width:200px;" emptyText="例：2014年6月12日"/></td>
	</tr>
	<tr>
		<td><label>承办单位:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="config_noticeHost" style="width:100%"  emptyText="例：治安大队"/></td>
	</tr>
	<tr>
		<td><label>转办事由:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_noticeContent" style="width:100%;height:70px" /></td>
	</tr>
	<tr>
		<td><label>部门<br>领导<br>审批</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_noticeLeaderComment" style="width:100%;height:150px" /></td>
	</tr>
	<tr>
		<td><label>答复人：</label></td>
		<td style="text-align:left"><input class="mini-textbox" required="true" name="config_noticeReply" style="width:200px;" emptyText="例：张三" /></td>
		<td colspan="2" style="text-align:right">答复时间：<input class="mini-textbox" required="true" name="config_noticeReplyTime" style="width:200px;" emptyText="例：2014年6月12日"/></td>
	</tr>
</@admin.con>
</ul>
</fieldset>
<fieldset id="fieldset2" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset2');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">民意网单</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<@admin.con id="datacon2">
	<tr>
		<td colspan="4"><div style="text-align:center"><img src="${base}/images/admin/minyiwang.png" /></div></td>
	</tr>
	<tr>
		<td width="60"><label>受理编号:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionNo" style="width:100%;" emptyText="请输入受理编号" /></td>
	</tr>
	<tr>
		<td><label>标题:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionTitle" style="width:100%;" /></td>
	</tr>
	<tr>
		<td><label>网友姓名:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="config_name" style="width:100%;" /></td>
	</tr>
	<tr>
		<td><label>联系方式:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="config_contact" style="width:100%;" /></td>
	</tr>
	<tr>
		<td><label>内容:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_content" style="width:100%;height:100px;" /></td>
	</tr>
	<tr>
		<td><label>答复:</label></td>
		<td colspan="3"><input class="mini-textarea" required="true" name="config_reply" style="width:100%;height:100px;" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td colspan="3"><input class="mini-textbox" required="true" name="transactionOrder" style="width:100%;" /></td>
	</tr>
</@admin.con>
</ul>
</fieldset>
<br>
<@admin.con id="datacon4">
<@admin.searchArea colspan="4">
<@admin.searchRightArea>
	<@admin.actBtn name="保存" actionName="/admin/minyiwang.do?command=create" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为民意网转办通知单新建页面，请按照上述内容进行编辑，然后点击【保存】按钮即可。<br>
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
    		url: "${base}/admin/minyiwang.do?command=create",
    		data: { object: json},
    		type: "POST",
    		cache: false,
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					form.unmask();
    					mini.alert("民意网转办通知单新建成功");
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