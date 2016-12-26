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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/mailbox.do">回音壁</a> > <span>留言内容</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>回音壁</span>
    		</h2>
    		<h2 class="sal_t">留言内容</h2>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr"></th>
    				<th scope="col"></th>
    			</tr>
    			<#if mail??>
    			<tr>
    				<td width="180" class="ta_fr">标题</td>
    				<td width="530" bgcolor="#FBFBFB">${mail.mailSubject}</td>
    			</tr>
    			<tr>
    				<td class="ta_fr">内容</td>
    				<td>${mail.mailContent}</td>
    			</tr>
    			<tr>
    				<td class="ta_fr">发布人</td>
    				<td>${mail.createByName}</td>
    			</tr>    			
    			<tr>
    				<td class="ta_fr">发布时间</td>
    				<td><#if mail.createByTime??>${mail.createByTime?string('yyyy-MM-dd HH:mm:ss')}<#else></#if></td>
    			</tr>
    			<tr>
    				<td class="ta_fr">回复内容</td>
    				<td>${mail.mailComment?default('')}</td>
    			</tr>
    			<tr>
    				<td class="ta_fr">回复人</td>
    				<td>${mail.commentByName?default('')}</td>
    			</tr>    			
    			<tr>
    				<td class="ta_fr">回复时间</td>
    				<td><#if mail.commentByTime??>${mail.commentByTime?string('yyyy-MM-dd HH:mm:ss')}<#else></#if></td>
    			</tr>
    			<tr>
    				<td class="ta_fr">回复期限</td>
    				<td><#if mail.dueDate??>${mail.dueDate?string('yyyy-MM-dd')}<#else></#if></td>
    			</tr>
    			<tr>
    				<td class="ta_fr">状态</td>
    				<td><#if mail.sts=='NEW'>新建<#elseif mail.sts=='WAI'>处理中<#elseif mail.sts=='RUN'>已处理<#elseif mail.sts=='EVL'>已评价</#if></td>
    			</tr>
    			<tr>
    				<td class="ta_fr">评价</td>
    				<td><#if mail.rank?default('')=='L1'>★
    				<#elseif mail.rank?default('')=='L2'>★★
    				<#elseif mail.rank?default('')=='L3'>★★★
    				<#elseif mail.rank?default('')=='L4'>★★★★
    				<#elseif mail.rank?default('')=='L5'>★★★★★
    				</#if>
    				</td>
    			</tr>
    			</#if>
    		</table>
    	</div>
    	<!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">局长信箱</h2>
				<ul>
					<li class="on"><a href="${base}/front/mailbox.do">回音壁</a></li>
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
    <script src="${base}/js/scrolltopcontrol.js"></script>
</body>
</html>
</@p.page>