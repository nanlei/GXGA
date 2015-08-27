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
                <a href="${base}/index.do">首页</a> > 搜索
            </div>    
        <div class="left">
            <h2 class="st">
            	<span>搜索结果</span>
            </h2>
            <#if (articleList.list?size>0)>
            <ul class="newslist">
                <#list articleList.list as article>
                	<#if (article.articleCode?length>1)>
                		<#if article.articleType=='FJGK'>
                		<li><span class="date2"></span><a href="${base}/front/branchoverview.do?code=${article.articleCode}">${article.articleTitle}</a></li>
                		<#elseif article.articleType=='TALENT'>
                		<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/talent.do?command=info&talentId=${article.articleId}">${article.articleTitle}</a></li>
                		</#if>
                	<#elseif (article.dcId?? && article.dcId!=0)>
                	<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/department.do?command=article&articleId=${article.articleId}&departmentCode=${article.departmentCode}">${article.articleTitle}</a></li>
                	<#elseif (article.jobCategoryId?? && article.jobCategoryId!=0)>
                	<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/job.do?command=article&articleId=${article.articleId}&jobId=${article.jobId}">${article.articleTitle}</a></li>
                	<#else>
                	<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}${article.articleCode}</a></li>
                	</#if>
                </#list>
            </ul>
            <@p.paging articleList/>
            </#if>
        </div>
        <!--left end-->
        <div class="s_right">
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