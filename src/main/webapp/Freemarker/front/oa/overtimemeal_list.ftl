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
    			<span>加班用餐</span>
    		</h2>
    		<h2 class="sal_t">加班用餐</h2>
    		<#if (overtimeMealList.list?size>0)>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">部门</th>
    				<th scope="col">加班日期</th>
    				<th scope="col">状态</th>
    			</tr>
    			<#list overtimeMealList.list as overtimeMeal>
    			<tr>
    				<td width="250" class="ta_fr">${overtimeMeal.departmentName}</td>
    				<td width="250">${overtimeMeal.overtimeDate}</td>
    				<td width="250">${overtimeMeal.sts}</td>
    			</tr>
    			</#list>
    		</table>
        	<#else>
    		<center>暂无数据</center>
    		</#if>
    		<@p.paging overtimeMealList />
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