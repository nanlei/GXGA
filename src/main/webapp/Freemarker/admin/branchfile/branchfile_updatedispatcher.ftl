<@admin.page title="修改分局文件">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<@admin.hasPermission actionName="/admin/branchfile.do?command=updatepre">
<#if article.articleBizType="NOR">
<div title="普通文件" url="${base}/admin/branchfile.do?command=updatepre&articleId=${article.articleId}" refreshOnClick="true"></div>
<#elseif article.articleBizType="RED">
<div title="红头文件" url="${base}/admin/branchfile.do?command=redheadupdatepre&articleId=${article.articleId}" refreshOnClick="true"></div>
</#if>
</@admin.hasPermission>
<@admin.hasPermission actionName="/admin/branchfile.do?command=updatedetailsearch">
<div title="附件明细" url="${base}/admin/branchfile.do?command=updatedetailsearch&articleId=${article.articleId}" refreshOnClick="true"></div>
</@admin.hasPermission>
</div>
</@admin.page>