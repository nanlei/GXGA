<@admin.page title="查询民意网单">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/minyiwang.do?command=detail">
<div title="基本信息" url="${base}/admin/minyiwang.do?command=detail&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/minyiwang.do?command=detailaudit">
<div title="日志" url="${base}/admin/minyiwang.do?command=detailaudit&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>