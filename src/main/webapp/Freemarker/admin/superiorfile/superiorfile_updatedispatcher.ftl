<@admin.page title="修改上级文件">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/superiorfile.do?command=updatepre">
<div title="文本信息" url="${base}/admin/superiorfile.do?command=updatepre&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/superiorfile.do?command=updatedetailsearch">
<div title="附件明细" url="${base}/admin/superiorfile.do?command=updatedetailsearch&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/superiorfile.do?command=signsearchpre">
<div title="签收明细" url="${base}/admin/superiorfile.do?command=signsearchpre&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>