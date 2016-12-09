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
            <a href="${base}/index.do">首页</a> > 部门综合 >
            	<#if dcCode=='BGS'><a href="${base}/front/department.do?departmentCode=BGS">办公室</a>
            	<#elseif dcCode=='ZGJJC'><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a>
            	<#elseif dcCode=='ZHZX'><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a>
            	<#elseif dcCode=='SFK'><a href="${base}/front/department.do?departmentCode=XFK">信访科</a>
            	<#elseif dcCode=='XZDD'><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a>
            	<#elseif dcCode=='ZADD'><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a>
            	<#elseif dcCode=='XTJDD'><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a>
            	<#elseif dcCode=='GBDD'><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a>
            	<#elseif dcCode=='SYZDD'><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a>
            	<#elseif dcCode=='WADD'><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a>
            	<#elseif dcCode=='FZDD'><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a>
            	<#elseif dcCode=='BFDD'><a href="${base}/front/department.do?departmentCode=CRJDD">出入境大队</a>
            	<#elseif dcCode=='LSTJD'><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a>
            	</#if> >
            <span>${dcName}</span>
        </div>
		<div class="left">
			<h2 class="st">
				<span>${dcName}</span>
			</h2>
			<ul class="newslist">
				<#list articleList.list as article>
				<li><span class="date2">【${article.createByTime}】</span><a href="${base}/front/department.do?command=article&articleId=${article.articleId}&departmentCode=${dcCode}">${article.articleTitle}</a></li>
				</#list>
			</ul>
			<@p.paging articleList />
		</div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">站点导航</h2>
                <ul>
                    <li <#if dcCode=='BGS'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=BGS">办公室</a></li>
                    <li <#if dcCode=='ZGJJC'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a></li>
                    <li <#if dcCode=='ZHZX'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a></li>
                    <li <#if dcCode=='XFK'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XFK">信访科</a></li>
                    <li <#if dcCode=='XZDD'>class="on"</#if>><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a></li>
                    <li <#if dcCode=='ZADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a></li>
                    <li <#if dcCode=='XTJDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a></li>
                    <li <#if dcCode=='GBDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a></li>
                    <li <#if dcCode=='SYZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a></li>
                    <li <#if dcCode=='WADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a></li>
                    <li <#if dcCode=='FZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a></li>
                    <li <#if dcCode=='BFDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=CRJDD">出入境大队</a></li>
                    <li <#if dcCode=='LSTJD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a></li>
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
</@p.page>