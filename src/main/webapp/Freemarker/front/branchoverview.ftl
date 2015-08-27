<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="branchoverview"/>
    </div>
    <!--topbg end-->
    <div class="mc">
    	<div class="location">
    		<a href="${base}/index.do">首页</a> > 分局概况 > 
    		<span>
    			<#if article.articleCode='JGSZ'>机构设置
            	<#elseif article.articleCode='LDBZ'>领导班子
            	<#elseif article.articleCode='FJRYB'>分局荣誉榜
            	<#elseif article.articleCode='FJDSJ'>分局大事记
            	<#else>
            	</#if>
            </span>
        </div>    
        <div class="left">
            <h2 class="st">
            	<span>
            		<#if article.articleCode='JGSZ'>机构设置
            		<#elseif article.articleCode='LDBZ'>领导班子
            		<#elseif article.articleCode='FJRYB'>分局荣誉榜
            		<#elseif article.articleCode='FJDSJ'>分局大事记
            		<#else>
            		</#if>
            	</span>
            </h2>
            <div class="sac">
                ${article.articleContent}
            </div>    
        </div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">分局概况</h2>
                <ul>
                    <li <#if article.articleCode='JGSZ'>class="on"</#if>><a href="${base}/front/branchoverview.do?code=JGSZ">机构设置</a></li>
                    <li <#if article.articleCode='LDBZ'>class="on"</#if>><a href="${base}/front/branchoverview.do?code=LDBZ">领导班子</a></li>
                    <li <#if article.articleCode='FJRYB'>class="on"</#if>><a href="${base}/front/branchoverview.do?code=FJRYB">分局荣誉榜</a></li>
                    <li <#if article.articleCode='FJDSJ'>class="on"</#if>><a href="${base}/front/branchoverview.do?code=FJDSJ">分局大事记</a></li>                   
                </ul>
            </div>
            <h2 class="mb_20" ><img src="${base}/images/srnavb.jpg" /></h2>
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