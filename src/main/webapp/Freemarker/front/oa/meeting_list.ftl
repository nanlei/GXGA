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
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/oa.do">网上办公</a> > <span>会议列表</span>
    	</div>
    	<div class="left">
    		<h2 class="st">
    			<span>会议列表</span>
    		</h2>
    		<h2 class="sal_t">会议列表</h2>
    		<#if (meetingList.list?size>0)>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">会议主题</th>
    				<th scope="col">会议室</th>
    				<th scope="col">部门</th>
    				<th scope="col">开始时间</th>
    				<th scope="col">结束时间</th>
    			</tr>
    			<#list meetingList.list as meeting>
    			<tr>
    				<td width="430" class="ta_fr">${meeting.meetingSubject}</td>
    				<td width="80" bgcolor="#FBFBFB">${meeting.meetingRoom}</td>
    				<td width="100">${meeting.departmentName}</td>
    				<td width="150" bgcolor="#FBFBFB">${meeting.meetingStartTime}</td>
    				<td width="150" bgcolor="#FBFBFB">${meeting.meetingEndTime}</td>
    			</tr>
    			</#list>
    		</table>
    		<#else>
    		<center>暂无数据</center>
    		</#if>
    		<@p.paging meetingList />
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