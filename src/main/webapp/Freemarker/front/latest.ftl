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
			<a href="${base}/index.do">首页</a> > <span>最新发布</span>
		</div>
		<div class="left">
			<h2 class="st">
				<span>最新发布</span>
			</h2>
			<#if (latestList.list?size>0)>
			<ul class="newslist">
				<#list latestList.list as article>
				<#if article.articleType='LAW'>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/law.do?command=info&lawId=${article.articleId}">${article.articleTitle}</a></li>
				<#elseif article.articleType='INFORMATIONSECURITY'>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/informationsecurity.do?command=info&informationSecurityId=${article.articleId}">${article.articleTitle}</a></li>
				<#else>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
				</#if>
				</#list>				
			</ul>
			<@p.paging latestList />
			</#if>
		</div>
        <!--left end-->
        <div class="s_right">
            <h2>
                <img src="${base}/images/srnavt.jpg" />
            </h2>
			<div class="rnav">
				<h2 class="srnt">最新发布</h2>
				<ul>
					<li class="on"><a href="${base}/front/latest.do">最新发布</a></li>
				</ul>
			</div>
            <h2 class="mb_20">
                <img src="${base}/images/srnavb.jpg" />
            </h2>
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