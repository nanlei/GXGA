<@admin.page title="查询公众交流平台单">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/pubcomm.do?command=detail">
<div title="基本信息" url="${base}/admin/pubcomm.do?command=detail&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/pubcomm.do?command=detailaudit">
<div title="日志" url="${base}/admin/pubcomm.do?command=detailaudit&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>