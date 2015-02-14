<@admin.page title="修改权限组">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<div title="权限组信息" url="/admin/permissiongroup.do?command=updatepre&permissionGroupId=${permissionGroupId}" refreshOnClick="true"></div>
<div title="权限组明细" url="/admin/permissiongroup.do?command=updatedetailsearch&permissionGroupId=${permissionGroupId}" refreshOnClick="true"></div>
</div>
</@admin.page>