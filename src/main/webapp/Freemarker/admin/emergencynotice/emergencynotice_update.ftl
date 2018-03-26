<@admin.page title="紧急通知">
<@admin.conArea title="前台综合>>紧急通知>>修改" id="form1" fileUpload="true" action="/admin/emergencynotice.do?command=update">
<input class="mini-hidden" name="noticeId" value="${(notice.noticeId)?default('')}"/>
<@admin.con id="datacon1">
	<tr>
		<td><label>标题:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeTitle" style="width:180px;" value="${(notice.noticeTitle)?default('')}" /></td>
	</tr>
	<tr>
		<td><label>排序:</label></td>
		<td><input class="mini-textbox" required="true" name="noticeOrder" style="width:180px;" value="${(notice.noticeOrder)?default('')}"/></td>
	</tr>
	<tr>
		<td><label>状态:</label></td>
		<td><input class="mini-radiobuttonlist" required="true" name="noticeStatus" style="width:180px;" value="${(notice.noticeStatus)?default('')}" repeatItems="1" repeatLayout="table" repeatDirection="vertical" textField="text" valueField="id" url="${base}/data/emergencynotice_sts.txt"/></td>
	</tr>
	<tr>
		<td><label>模式定义:</label></td>
		<td><input class="mini-combobox" id="noticeMode" required="true" name="noticeMode" style="width:180px;" value="${notice.linkModule?default('SELF')}" repeatItems="1" repeatLayout="table" repeatDirection="vertical" textField="text" valueField="id" url="${base}/data/emergencynotice_mode.txt" onValueChanged="onModeChanged"/></td>
	</tr>
	<tr id="image">
		<td><label>图片:</label></td>
		<td><input id="fileupload1" class="mini-htmlfile" name="FdataImage" style="width:180px;" required="false"/></td>
	</tr>
	<tr id="attachment">
		<td><label>附件:</label></td>
		<td><input id="fileupload2" class="mini-htmlfile" name="FdataAttachment" style="width:180px;" required="false"/></td>
	</tr>
	<tr id="jobTr">
		<td><label>专项工作:</label></td>
		<td><input id="job" class="mini-combobox" name="job" style="width:180px;" showNullItem="true" allowInput="false" value="${notice.linkId?default('')}"/></td>
	</tr>
<@admin.searchArea colspan="2">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/emergencynotice.do?command=update" event="Save"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为紧急通知修改页面，请按照上述内容填写和选择相关内容<br/>
模式选择【本类-图片附件】时，若不修改图片和附件，请将它们留空即可<br/>
模式选择【链接-专项工作】时，必须选择一个专项工作<br/>
修改完毕后，然后点击【保存】按钮即可
</@admin.conArea>
<#if notice.linkModule?default('SELF')='SELF'>
<@admin.conArea title="图片预览" id="form3">
<img src="${base}${notice.noticeImageUrl}" width="124" height="83" title="非原有比例"></img>
</@admin.conArea>
<@admin.conArea title="附件下载" id="form3">
<a href="${base}${notice.noticeAttachmentUrl}">下载</a>
</@admin.conArea>
</#if>
<@admin.script>
	mini.parse();
    
    var form = new mini.Form("form1");
    
    var linkId = ${notice.linkId?default('0')};

    $("#image").hide();
    $("#attachment").hide();
    $("#jobTr").hide();
    
    initUpdate();
    
    function initUpdate(){
    	var noticeMode = mini.get("noticeMode");
    	if (noticeMode.getValue() == "LINK-JOB") {
            $("#image").hide();
            $("#attachment").hide();
            $("#jobTr").show();
            prepareJob();
    	}
    }
    
    function prepareJob() {
    	var job=mini.get("job");
    	
    	job.setValue("");
    	
    	var url="/admin/ajax.do?command=job";
    	
    	job.setUrl(url);
    	
    	if (linkId!=0) {
    	  job.setValue(linkId);
    	}
    }
    
    function onModeChanged(){
		var noticeMode = mini.get("noticeMode");
		if (noticeMode.getValue() == "SELF") {
            $("#image").show();
            $("#attachment").show();
            $("#jobTr").hide();
		} else if (noticeMode.getValue() == "LINK-JOB") {
            $("#image").hide();
            $("#attachment").hide();
            $("#jobTr").show();
            prepareJob();
		}
	}
	
    function modeValidate() {
		var noticeMode = mini.get("noticeMode");
		if (noticeMode.getValue() == "SELF") {
            mini.get("fileupload1").setRequired(true);
            mini.get("fileupload2").setRequired(true);
            mini.get("job").setRequired(false);
		} else if (noticeMode.getValue() == "LINK-JOB") {
            mini.get("fileupload1").setRequired(false);
            mini.get("fileupload2").setRequired(false);
            mini.get("job").setRequired(true);
		}    	
    }
    
    
    function Save() {

        modeValidate(); 
           	
    	form.validate();
    	
    	if (form.isValid() == false) {
    		return;
    	}
   	
		$("#form1").submit();
    } 
</@admin.script>
</@admin.page>