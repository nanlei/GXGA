<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
        <!--top end-->
        <@p.banner/>
        <!--banner end-->
        <@p.nav on="index"/>
    </div>
    <!--topbg end-->
    <div class="mc">
        <div class="location">
            <a href="${base}/index.do">首页</a> > 专项工作 > <a href="${base}/front/job.do?jobId=${job.jobId}">${job.jobTitle}</a> >
            <span>${jobCategory.jobCategoryTitle}</span>
        </div>
		<div class="left">
			<#if (articleList.list?size>0)>
			<ul class="newslist">
				<#list articleList.list as article>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/job.do?command=article&articleId=${article.articleId}&jobId=${job.jobId}">${article.articleTitle}</a></li>
				</#list>
			</ul>
			<@p.paging articleList /> 
			</#if>
		</div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">专项工作</h2>
                <ul>
                	<#list jobList as j>
                	<li <#if job.jobId==j.jobId>class="on"</#if>><a href="${base}/front/job.do?jobId=${j.jobId}">${j.jobTitle}</a></li>
                	</#list>
                </ul>
            </div>
            <h2 class="mb_20"><img src="${base}/images/srnavb.jpg" /></h2>
            <@p.duty/>
			<@p.rank/>
            <@p.imageLink/>
        </div>
        <!--right end-->
        <@p.topic/>
    </div>
    <!--beginning of bottom -->
    <@p.bottom/>
    <!--end of bottom -->
</body>
</@p.page>