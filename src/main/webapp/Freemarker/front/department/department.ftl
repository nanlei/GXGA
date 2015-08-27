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
            <h2 class="st"><a name="${category.dcId}" href="${base}/front/department.do?command=sublist&dcId=${category.dcId}&departmentCode=${departmentCode}" class="sde_more">更多+</a><span>${category.dcName}</span></h2>
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
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">站点导航</h2>
                <dl class="slider">
                	<dt <#if departmentCode=='BGS'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=BGS">办公室</a></dt>
                    <#if departmentCode=='BGS'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='ZGJJC'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a></dt>
                    <#if departmentCode=='ZGJJC'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='ZHZX'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a></dt>
                    <#if departmentCode=='ZHZX'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='XFK'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=XFK">信访科</a></dt>
                    <#if departmentCode=='XFK'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                    <dt><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a></dt>
                	<dt <#if departmentCode=='ZADD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a></dt>
                    <#if departmentCode=='ZADD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='XTJDD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a></dt>
                    <#if departmentCode=='XTJDD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='GBDD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a></dt>
                    <#if departmentCode=='GBDD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='SYZDD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a></dt>
                    <#if departmentCode=='SYZDD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='WADD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a></dt>
                    <#if departmentCode=='WADD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='FZDD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a></dt>
                    <#if departmentCode=='FZDD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='BFDD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=BFDD">边防大队</a></dt>
                    <#if departmentCode=='BFDD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                	<dt <#if departmentCode=='LSTJD'>class="slider_open"</#if>><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a></dt>
                    <#if departmentCode=='LSTJD'>
                    <dd>
                    	<#list categoryList as category>
                        <a href="#${category.dcId}">${category.dcName}</a>
                    	</#list>
                    </dd>
                    </#if>
                </dl>
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
<script>
$(function () {
	//初始化变量
	var flag = 0;
	var $dt = $('.slider dt');
	var $dd = $('.slider dd');
	//初始化状态
	$dd.eq(0).show();
	 $dt.eq(0).css('cursor', 'default');
 });
</script>
<script src="${base}/js/scrolltopcontrol.js"></script>
</body>
</@p.page>