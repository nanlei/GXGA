<@admin.page title="修改专项行动文章">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/jobline.do?command=updatepre">
<div title="文本信息" url="/admin/jobline.do?command=updatepre&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/jobline.do?command=updatedetailsearch">
<div title="附件明细" url="/admin/jobline.do?command=updatedetailsearch&articleId=${articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>