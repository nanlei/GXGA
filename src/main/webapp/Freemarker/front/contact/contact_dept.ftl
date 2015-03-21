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
			<a href="${base}/index.do">首页</a> > <span>通讯录</span>
		</div>
		<div class="left">
			<h2 class="st"><span>通讯录</span></h2>
			<ul class="sal_list">
				<#list departmentList as department>
				<li><a href="${base}/front/contact.do?command=list&departmentCode=${department.departmentCode}">${department.departmentName}</a></li>
				</#list>
			</ul>
		</div>
		<!--left end-->
		<div class="s_right">
			<h2><img src="${base}/images/srnavt.jpg" /></h2>
			<div class="rnav">
				<h2 class="srnt">通讯录</h2>
				<ul>
					<li class="on"><a href="${base}/front/contact.do">通讯录</a></li>
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
</@p.page>