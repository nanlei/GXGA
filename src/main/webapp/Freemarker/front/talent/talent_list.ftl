<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="talent"/>
    </div>
    <!--topbg end-->
    <div class="mc">
        <div class="location">
            <a href="${base}/index.do">首页</a>  > <span>人才市场</span>
        </div>
        <div class="left">
        	<#list categoryList as category>
        	<h2 class="st"><a href="${base}/front/talent.do?command=sublist&category=${category.articleCode}" class="sde_more">更多+</a><span>${category.articleCodeName}</span></h2>
        	<ul class="newslist newslist2">
        	<#assign subList=ftlUtil.getArticleListByTypeAndCode('TALENT','${category.articleCode}') />
        		<#list subList as sub>
        		<li><span class="date2">【${sub.createByTime}】</span><a href="${base}/front/talent.do?command=info&talentId=${sub.articleId}">${sub.articleTitle}</a></li>
        		</#list>
        	</ul>
        	</#list>
        </div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">人才市场</h2>
                <ul>
                    <#list categoryList as category>
                    <li><a href="${base}/front/talent.do?command=sublist&category=${category.articleCode}">${category.articleCodeName}</a></li>
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