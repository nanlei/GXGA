<@admin.page title="新建分局文件">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/branchfile.do?command=createpre">
<div title="普通文件" url="${base}/admin/branchfile.do?command=createpre" refreshOnClick="true"></div>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/branchfile.do?command=redheadcreatepre">
<div title="红头文件" url="${base}/admin/branchfile.do?command=redheadcreatepre" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>