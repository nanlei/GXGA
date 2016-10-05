<@admin.page title="修改政工通知">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/politicalnotice.do?command=updatepre">
<div title="文本信息" url="/admin/politicalnotice.do?command=updatepre&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/politicalnotice.do?command=updatedetailsearch">
<div title="附件明细" url="/admin/politicalnotice.do?command=updatedetailsearch&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>