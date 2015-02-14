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
            <a href="Default.htm">首页</a>  > 部门综合 >
            <span>
            	<#if departmentCode=='BGS'>办公室
            	<#elseif departmentCode=='ZGJJC'>政工纪检处
            	<#elseif departmentCode=='ZHZX'>指挥中心
            	<#elseif departmentCode=='SFK'>信访科
            	<#elseif departmentCode=='XZDD'>刑侦大队
            	<#elseif departmentCode=='ZADD'>治安大队
            	<#elseif departmentCode=='XTJDD'>巡特警大队
            	<#elseif departmentCode=='GBDD'>国保大队
            	<#elseif departmentCode=='SYZDD'>食药侦大队
            	<#elseif departmentCode=='WADD'>网安大队
            	<#elseif departmentCode=='FZDD'>法制大队
            	<#elseif departmentCode=='BFDD'>边防大队
            	<#elseif departmentCode=='LSTJD'>蓝鲨突击队
            	</#if>
            </span>
        </div>
        <div class="left">
        	<#list categoryList as category>
            <h2 class="st"><a href="${base}/front/department.do?command=sublist&dcId=${category.dcId}&departmentCode=${departmentCode}" class="sde_more">更多+</a><span>${category.dcName}</span></h2>
            <ul class="newslist newslist2">
            <#assign subList=ftlUtil.getArticleListByDcId('${category.dcId}') />
        		<#list subList as sub>
        		<li><span class="date2">【${sub.createByTime}】</span><a href="${base}/front/department.do?command=article&articleId=${sub.articleId}&departmentCode=${departmentCode}">${sub.articleTitle}</a></li>
        		</#list>
        	</ul>            
        	</#list>
        </div>
        <!--left end-->
        <div class="s_right">
            <h2>
                <img src="${base}/images/srnavt.jpg" />
            </h2>
            <div class="rnav">
                <h2 class="srnt">站点导航</h2>
                <ul>
                    <li <#if departmentCode=='BGS'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=BGS">办公室</a></li>
                    <li <#if departmentCode=='ZGJJC'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a></li>
                    <li <#if departmentCode=='ZHZX'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a></li>
                    <li <#if departmentCode=='XFK'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XFK">信访科</a></li>
                    <li <#if departmentCode=='XZDD'>class="on"</#if>><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a></li>
                    <li <#if departmentCode=='ZADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a></li>
                    <li <#if departmentCode=='XTJDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a></li>
                    <li <#if departmentCode=='GBDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a></li>
                    <li <#if departmentCode=='SYZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a></li>
                    <li <#if departmentCode=='WADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a></li>
                    <li <#if departmentCode=='FZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a></li>
                    <li <#if departmentCode=='BFDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=BFDD">边防大队</a></li>
                    <li <#if departmentCode=='LSTJD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a></li>
                </ul>
            </div>
            <h2 class="mb_20">
                <img src="${base}/images/srnavb.jpg" />
            </h2>
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