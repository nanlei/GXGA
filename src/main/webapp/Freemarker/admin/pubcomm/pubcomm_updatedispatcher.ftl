<@admin.page title="修改公众交流平台单">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/pubcomm.do?command=updatepre">
<div title="基本信息" url="${base}/admin/pubcomm.do?command=updatepre&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/pubcomm.do?command=audit">
<div title="日志" url="${base}/admin/pubcomm.do?command=audit&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>