<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
        <!--top end-->
        <@p.banner/>
        <!--banner end-->
        <@p.nav on="index" />
    </div>
    <!--topbg end-->
    <div class="mc">
        <div class="location">
            <a href="${base}/index.do">首页</a> > 专项工作 >
            <span>${job.jobTitle}</span>
        </div>
		<div class="left">
			<#list jobCategoryList as jobCategory>
            <h2 class="st"><a href="${base}/front/job.do?command=sublist&jobCategoryId=${jobCategory.jobCategoryId}&jobId=${job.jobId}" class="sde_more">更多+</a><span>${jobCategory.jobCategoryTitle}</span></h2>
            <ul class="newslist newslist2">
            <#assign subList=ftlUtil.getArticleListByJobCategoryId('${jobCategory.jobCategoryId}') />
        		<#list subList as sub>
        		<li><span class="date2">【${sub.createByTime}】</span><a href="${base}/front/job.do?command=article&articleId=${sub.articleId}&jobId=${job.jobId}">${sub.articleTitle}</a></li>
        		</#list>
        	</ul>            
        	</#list>
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
    <script src="${base}/js/scrolltopcontrol.js"></script>
</body>
</@p.page>