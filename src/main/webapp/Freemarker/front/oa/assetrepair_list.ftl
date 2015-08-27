<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
	<div class="topbg">
		<!--top end-->
		<@p.banner/>
		<!--banner end-->
		<@p.nav on="oa"/>
    </div>
    <!--topbg end-->
    <div class="mc">
    	<div class="location">
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/oa.do">网上办公</a> > <span>设备维修</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>设备维修</span>
    		</h2>
    		<h2 class="sal_t">设备维修</h2>
    		<#if (assetRepairList.list?size>0)>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">维修代码</th>
    				<th scope="col">设备名称</th>
    				<th scope="col">设备所在地</th>
    				<th scope="col">部门</th>
    				<th scope="col">报修时间</th>
    				<th scope="col">状态</th>
    			</tr>
    			<#list assetRepairList.list as assetRepair>
    			<tr>
    				<td width="150" class="ta_fr">${assetRepair.arCode}</td>
    				<td width="200">${assetRepair.assetName}</td>
    				<td width="200">${assetRepair.assetLocation}</td>
    				<td width="100">${assetRepair.departmentName}</td>
    				<td width="130">${assetRepair.createByTime}</td>
    				<td width="70">${assetRepair.sts}</td>
    			</tr>
    			</#list>
    		</table>
    	    <#else>
    		<center>暂无数据</center>
    		</#if>
    		<@p.paging assetRepairList />
    	</div>
    	<!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">网上办公</h2>
				<ul>
					<li><a href="${base}/front/oa.do">会议列表</a></li>
					<li class="on"><a href="${base}/front/oa.do?command=assetrepair">设备维修</a></li>
					<li><a href="${base}/front/oa.do?command=overtimemeal">加班用餐</a></li>
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