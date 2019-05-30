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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/monthlystar.do">每月之星</a>
    	</div>
		<div class="left">
			<h2 class="st"><span>每月之星</span></h2>
			<ul class="newslist">
				<#list monthlyStarList.list as article>
				<li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/monthlystar.do?command=info&id=${article.articleId}">${article.articleTitle}</a></li>
				</#list>
			</ul>
			<@p.paging monthlyStarList />
		</div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">每月之星</h2>
				<ul>
					<li class="on"><a href="${base}/front/monthlystar.do">每月之星</a></li>
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