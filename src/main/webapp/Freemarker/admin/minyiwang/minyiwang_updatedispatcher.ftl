<@admin.page title="修改民意网单">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/minyiwang.do?command=updatepre">
<div title="基本信息" url="${base}/admin/minyiwang.do?command=updatepre&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/minyiwang.do?command=audit">
<div title="日志" url="${base}/admin/minyiwang.do?command=audit&transactionId=${transactionId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>