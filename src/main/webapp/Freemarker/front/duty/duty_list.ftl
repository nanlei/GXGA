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
    		<a href="${base}/index.do" name="anchor">首页</a> > 分局值班
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>分局值班</span>
    		</h2>
    		<h2 class="sal_t">高新园区公安分局值班表 (${sessionYearMonth?date('yyyyMM')?string('yyyy-MM')})</h2>
    		<div style="margin-top:-20px;text-align:center;"><a href="#" onclick="javascript:dutyMonthChange('-1','${sessionYearMonth}')">上月</a> <a href="#" onclick="javascript:dutyMonthChange('1','${sessionYearMonth}')">下月</a></div>
    		<div class="sal_top">
    			<h2 class="sal_l"><!--大厅值班：3715 地下门卫：3714--></h2>
    			<h2 class="sal_r"><!--指挥中心值班：3770 3771 3772 3773 传真：84790464--></h2>
    		</div>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr" width="180">日期</th>
    				<th scope="col" width="180">局领导</th>
    				<th scope="col" width="180">值班长</th>
    				<th scope="col" width="180">值班民警</th>
    			</tr>
    			<#list dutyList as duty>
    			<tr>
    				<td width="180" class="ta_fr">${duty.dutyDate}</td>
    				<td width="180" bgcolor="#FBFBFB">${duty.dutyManager}</td>
    				<td width="180">${duty.dutyLeader}</td>
    				<td width="180" bgcolor="#FBFBFB">${duty.dutyPolice}</td>
    			</tr>
    			</#list>
    		</table>
    		<h2 class="sal_t">分局部门/派出所值班表</h2>
    		<table border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">部门</th>
    				<th scope="col">更新时间</th>
    				<th scope="col">值班表</th>
    			</tr>
    			<#list dutyPlanList as dutyPlan>
    			<tr>
    				<td width="240" class="ta_fr">${dutyPlan.departmentName}</td>
    				<td width="240">${dutyPlan.createByTime}</td>
    				<td width="240" bgcolor="#FBFBFB"><a href="${base}${dutyPlan.dpUrl}">下载</a></td>
    			</tr>
    			</#list>
    		</table>
    	</div>
        <!--left end-->
        <div class="s_right">
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
    <script>
    function dutyMonthChange(offset,ts){
    	location.href="duty.do?offset="+offset+"&ts="+ts+"#anchor";
    }
    </script>
</body>
</@p.page>