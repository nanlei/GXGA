<@admin.page title="修改角色">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<div title="角色信息" url="${base}/admin/role.do?command=updatepre&roleId=${roleId}" refreshOnClick="true"></div>
<div title="角色明细" url="${base}/admin/role.do?command=updatedetailsearch&roleId=${roleId}" refreshOnClick="true"></div>
</div>
</@admin.page>