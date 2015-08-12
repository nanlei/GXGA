<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
	<div class="topbg">
		<!--top end-->
		<@p.banner/>
		<!--banner end-->
		<@p.nav on="oa"/>
    <!--topbg end-->
    <div class="mc">
    	<div class="location">
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/oa.do">网上办公</a> > <span>民心网</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>民心网</span>
    		</h2>
    		
    	    <ul class="newslist">
    	        <#list minxinwangList.list as minxinwang>
    	        <li><span class="date2">${minxinwang.createByTime}</span><a href="${base}/front/oa.do?command=minxinwangdetail&transactionId=${minxinwang.transactionId}">${minxinwang.transactionNo} - ${minxinwang.transactionTitle}</a></li>
    	        </#list>
	        </ul>
    		<@p.paging minxinwangList />
    	</div>
    	<!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<@p.oaPanel/>
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
</html>
</@p.page>