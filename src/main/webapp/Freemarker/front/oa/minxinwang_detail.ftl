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
    		<h2 class="sal_t">${transaction.transactionTitle}</h2>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr"></th>
    				<th scope="col"></th>
    				<th scope="col"></th>
    				<th scope="col"></th>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">受理编号:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${transaction.transactionNo}</td>
    			</tr>
    			<tr>
    			    <td width="150" class="ta_fr">投诉人姓名:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_name}</td>
    				<td width="150" bgcolor="#FBFBFB">投诉人电话:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_phone}</td>
    			</tr>
    		    <tr>
    			    <td width="150" class="ta_fr">投诉人地址:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_address}</td>
    				<td width="150" bgcolor="#FBFBFB">投诉人Mail:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_email}</td>
    			</tr>
    			<tr>
    			    <td width="150" class="ta_fr">投诉时间:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_time?date('yyyy-MM-dd')}</td>
    				<td width="150" bgcolor="#FBFBFB">拟办时间:</td>
    				<td width="300" bgcolor="#FBFBFB">${config.config_disposeTime?date('yyyy-MM-dd')}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">投诉标题:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${transaction.transactionTitle}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">投诉内容:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3" style="text-align:left">${config.config_content}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">责任部门:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${config.config_department}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">拟办意见:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${config.config_comment}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">分管领导:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${config.config_leader}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">领导意见:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3">${config.config_leaderComment}</td>
    			</tr>
    			<tr>
    				<td width="150" class="ta_fr">状态:</td>
    				<td width="300" bgcolor="#FBFBFB" colspan="3"><#switch transaction.transactionStatus><#case 'NEW'>未处理<#break/><#case 'WAI'>处理中<#break/><#case 'FIN'>办结<#break/><#case 'OVT'>超时<#break/></#switch></td>
    			</tr>
    		</table>
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