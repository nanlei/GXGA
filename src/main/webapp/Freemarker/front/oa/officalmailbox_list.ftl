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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/oa.do">网上办公</a> > <span>局长信箱</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>局长信箱</span>
    		</h2>
    		
    	    <h2 class="sal_t">局长信箱通知单</h2>
    	    <#if (officalMailBoxList.list?size>0)>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">信箱编号</th>
    				<th scope="col">标题</th>
    				<th scope="col">责任部门</th>
    				<th scope="col">接收时间</th>
    				<th scope="col">状态</th>
    			</tr>
    			<#list officalMailBoxList.list as officalMailBox>
    			<tr>
    				<td width="80" class="ta_fr">${officalMailBox.transactionNo}</td>
    				<td width="350" bgcolor="#FBFBFB">${officalMailBox.transactionTitle}</td>
    				<td width="130" bgcolor="#FBFBFB">${officalMailBox.config_unit}</td>
    				<td width="100" bgcolor="#FBFBFB">${officalMailBox.config_date}</td>
    				<td width="80" bgcolor="#FBFBFB"><#switch officalMailBox.transactionStatus><#case 'NEW'>未处理<#break/><#case 'WAI'>处理中<#break/><#case 'FIN'>办结<#break/><#case 'OVT'>超时<#break/></#switch></td>
    			</tr>
    			</#list>
    		</table>
    		<#else>
    		<center>暂无数据</center>
    		</#if>
    		<@p.paging officalMailBoxList />
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