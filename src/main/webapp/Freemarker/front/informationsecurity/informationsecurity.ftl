<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
	<div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="informationsecurity"/>
    </div>
    <!--topbg end-->
    <div class="mc">
		<@p.articleLocation article=informationSecurity/>
    	<@p.articlePanel article=informationSecurity/>
        <!--left end-->
        <div class="s_right">
            <h2>
                <img src="${base}/images/srnavt.jpg" />
            </h2>
            <@p.articleTypeNav article=informationSecurity/>
            <h2 class="mb_20" >
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