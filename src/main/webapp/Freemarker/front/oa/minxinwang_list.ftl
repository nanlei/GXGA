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
    		
    	    <h2 class="sal_t">民心网诉求请示单</h2>
    	    <#if (minxinwangList.list?size>0)>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">受理编号</th>
    				<th scope="col">投诉标题</th>
    				<th scope="col">责任部门</th>
    				<th scope="col">投诉时间</th>
    				<th scope="col">拟办时间</th>
    				<th scope="col">状态</th>
    			</tr>
    			<#list minxinwangList.list as minxinwang>
    			<tr>
    				<td width="80" class="ta_fr">${minxinwang.transactionNo}</td>
    				<td width="350" bgcolor="#FBFBFB">${minxinwang.transactionTitle}</td>
    				<td width="130" bgcolor="#FBFBFB">${minxinwang.config_department}</td>
    				<td width="90" bgcolor="#FBFBFB">${minxinwang.config_time?date('yyyy-MM-dd')}</td>
    				<td width="90" bgcolor="#FBFBFB">${minxinwang.config_disposeTime?date('yyyy-MM-dd')}</td>
    				<td width="80" bgcolor="#FBFBFB"><#switch minxinwang.transactionStatus><#case 'NEW'>未处理<#break/><#case 'WAI'>处理中<#break/><#case 'FIN'>办结<#break/><#case 'OVT'>超时<#break/></#switch></td>
    			</tr>
    			</#list>
    		</table>
    		<#else>
    		<center>暂无数据</center>
    		</#if>
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