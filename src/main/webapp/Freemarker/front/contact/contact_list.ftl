<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
	<div class="topbg">
		<!--top end-->
		<@p.banner/>
		<!--banner end-->
		<@p.nav on="contact"/>
    </div>
    <!--topbg end-->
    <div class="mc">
    	<div class="location">
    		<a href="${base}/index.do">首页</a> > <a href="${base}/front/contact.do">通讯录</a> > <span>${departmentName?default('')}</span>
    	</div>
    	<div class="left sal_c">
    		<h2 class="st">
    			<span>通讯录</span>
    		</h2>
    		<h2 class="sal_t">高新园区公安分局机关电话号码表</h2>
    		<div class="sal_top">
    			<h2 class="sal_l"><!--大厅值班：3715 地下门卫：3714--></h2>
    			<h2 class="sal_r"><!--指挥中心值班：3770 3771 3772 3773 传真：84790464--></h2>
    		</div>
    		<table  border="0" cellspacing="0" cellpadding="0" class="sal_table">
    			<tr>
    				<th scope="col" class="ta_fr">部门</th>
    				<th scope="col">科室</th>
    				<th scope="col">姓名</th>
    				<th scope="col">职务</th>
    				<th scope="col">内线</th>
    				<th scope="col">外线</th>
    				<th scope="col">手机</th>
    				<th scope="col">虚拟号</th>
    				<th scope="col">联通手机</th>
    				<th scope="col">虚拟号</th>
    				<th scope="col">房间号</th>
    				<th scope="col">宅电</th>
    			</tr>
    			<#if contactList??>
    			<#list contactList as contact>
    			<tr>
    				<td width="85" class="ta_fr">${contact.departmentName}</td>
    				<td width="80" bgcolor="#FBFBFB">${contact.subDepartment}</td>
    				<td width="75">${contact.name}</td>
    				<td width="80" bgcolor="#FBFBFB">${contact.positionName}</td>
    				<td width="54"><#if contact.internalNo?has_content>${contact.internalNo}<#else>&nbsp;</#if></td>
    				<td width="87" bgcolor="#FBFBFB"><#if contact.externalNo?has_content>${contact.externalNo}<#else>&nbsp;</#if></td>
    				<td width="100"><#if contact.mobile?has_content>${contact.mobile}<#else>&nbsp;</#if></td>
    				<td width="73" bgcolor="#FBFBFB"><#if contact.virtualNo?has_content>${contact.virtualNo}<#else>&nbsp;</#if></td>
    				<td width="100"><#if contact.unicomNo?has_content>${contact.unicomNo}<#else>&nbsp;</#if></td>
    				<td width="65" bgcolor="#FBFBFB"><#if contact.unicomVirtualNo?has_content>${contact.unicomVirtualNo}<#else>&nbsp;</#if></td>
    				<td width="60"><#if contact.officeNo?has_content>${contact.officeNo}<#else>&nbsp;</#if></td>
    				<td width="95" bgcolor="#FBFBFB"><#if contact.homePhone?has_content>${contact.homePhone}<#else>&nbsp;</#if></td>
    			</tr>
    			</#list>
    			</#if>
    		</table>
    	</div>
    	<!--left end-->
		<@p.topic />
    </div>
    <!--beginning of bottom -->
    <@p.bottom/>
    <!--end of bottom -->
</body>
</html>
</@p.page>