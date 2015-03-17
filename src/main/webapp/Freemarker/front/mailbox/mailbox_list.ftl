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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/mailbox.do">局长信箱</a> > <span>留言列表</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>局长信箱</span>
    		</h2>
    		<h2 class="sal_t">留言列表</h2>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">标题</th>
    				<th scope="col">留言时间</th>
    				<th scope="col">状态</th>
    			</tr>
    			<#if mailList??>
    			<#list mailList.list as mail>
    			<tr>
    				<td width="530" class="ta_fr">${mail.mailSubject}</td>
    				<td width="180" bgcolor="#FBFBFB">${mail.createByTime}</td>
    				<td width="100">${mail.sts}</td>
    				<#--<td width="130" bgcolor="#FBFBFB"><a href="${base}/front/mailbox.do?command=info&mailId=${mail.mailId}">查看</a></td>-->
    			</tr>
    			</#list>
    			</#if>
    		</table>
    		<@p.paging mailList />
    	</div>
    	<!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">局长信箱</h2>
				<ul>
					<li class="on"><a href="${base}/front/mailbox.do">局长信箱</a></li>
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
</body>
</html>
</@p.page>