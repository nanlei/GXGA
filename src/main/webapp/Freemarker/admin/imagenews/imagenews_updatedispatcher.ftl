<@admin.page title="修改图片新闻">
<div id="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
<div title="文本信息" url="/admin/imagenews.do?command=updatepre&articleId=${articleId}" refreshOnClick="true"></div>
<div title="图片明细" url="/admin/imagenews.do?command=updatedetailsearch&articleId=${articleId}" refreshOnClick="true"></div>
</div>
</@admin.page>