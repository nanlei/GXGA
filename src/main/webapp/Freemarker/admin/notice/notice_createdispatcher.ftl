<@admin.page title="新建通知通报">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/notice.do?command=createpre">
<div title="普通文件" url="${base}/admin/notice.do?command=createpre" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/notice.do?command=redheadcreatepre">
<div title="红头文件" url="${base}/admin/notice.do?command=redheadcreatepre" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>