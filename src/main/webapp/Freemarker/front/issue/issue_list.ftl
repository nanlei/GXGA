<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
        <!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="issue"/>
	</div>
    <!--topbg end-->
    <div class="mc">
        <@p.articleListLocation articleType=articleType />
		<@p.articleListPanel articleType=articleType articleList=issueList/>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <@p.articleListTypeNav articleType=articleType/>
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