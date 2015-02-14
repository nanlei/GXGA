<#assign base="${springMacroRequestContext.getContextPath()}" />
<#assign talentTypeName=ftlUtil.getConstantNameByTypeAndValue('TALENT','${articleCode}')/>
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
            <a href="${base}/index.do">首页</a>  > <a href="${base}/front/talent.do">人才市场</a> > 
            <span>${talentTypeName}</span>
        </div>
		<div class="left">
			<h2 class="st">
				<span>${talentTypeName}</span>
			</h2>
			<#if (subList.list?size>0)>
			<ul class="newslist">
				<#list subList.list as article>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/talent.do?command=info&talentId=${article.articleId}">${article.articleTitle}</a></li>
				</#list>
			</ul>
			<@p.paging subList/>
			</#if>
		</div>
        <!--left end-->
        <div class="s_right">
            <h2>
                <img src="${base}/images/srnavt.jpg" />
            </h2>
            <div class="rnav">
                <h2 class="srnt">人才市场</h2>
                <ul>
                    <#list categoryList as category>
                    <li <#if category.articleCode=articleCode>class="on"</#if>><a href="${base}/front/talent.do?command=sublist&category=${category.articleCode}">${category.articleCodeName}</a></li>
                    </#list>
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