<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="download"/>
    </div>
    <!--topbg end-->
    <div class="mc">    
            <div class="location">
                <a href="${base}/index.do">首页</a> > 下载中心
            </div>    
        <div class="left">
            <h2 class="st">
            	<span>下载中心</span>
            </h2>
            <#if (downloadList.list?size>0)>
            <ul class="newslist">
                <#list downloadList.list as download>
                	<li><span class="date2">【${download.createByTime}】</span><a href="${base}/front/download.do?command=download&downloadId=${download.downloadId}">${download.downloadDescription}</a></li>
                </#list>
            </ul>
            <@p.paging downloadList/>
            </#if>
        </div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">下载中心</h2>
                <ul>
                    <li class="on"><a href="${base}/front/download.do">下载中心</a></li>
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