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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/monthlystar.do">每月之星</a> > <span> ${monthlyStar.articleTitle}</span>
    	</div>
    	<@p.articlePanel article=monthlyStar/>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">每月之星</h2>
				<ul>
					<li class="on"><a href="${base}/front/monthlystar.do">每月之星</a></li>
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
    <script src="${base}/js/scrolltopcontrol.js"></script>
</body>
</@p.page>