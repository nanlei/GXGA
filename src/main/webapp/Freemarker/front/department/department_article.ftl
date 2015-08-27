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
            <a href="${base}/index.do">首页</a>  > 部门综合 >
            	<#if dc.dcCode=='BGS'><a href="${base}/front/department.do?departmentCode=BGS">办公室</a>
            	<#elseif dc.dcCode=='ZGJJC'><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a>
            	<#elseif dc.dcCode=='ZHZX'><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a>
            	<#elseif dc.dcCode=='SFK'><a href="${base}/front/department.do?departmentCode=XFK">信访科</a>
            	<#elseif dc.dcCode=='XZDD'><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a>
            	<#elseif dc.dcCode=='ZADD'><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a>
            	<#elseif dc.dcCode=='XTJDD'><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a>
            	<#elseif dc.dcCode=='GBDD'><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a>
            	<#elseif dc.dcCode=='SYZDD'><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a>
            	<#elseif dc.dcCode=='WADD'><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a>
            	<#elseif dc.dcCode=='FZDD'><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a>
            	<#elseif dc.dcCode=='BFDD'><a href="${base}/front/department.do?departmentCode=BFDD">边防大队</a>
            	<#elseif dc.dcCode=='LSTJD'><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a>
            	</#if> >
            <span>${dc.dcName}</span>
        </div>
		<div class="left">
			<h2 class="st">
				<span>${dc.dcName}</span>
			</h2>
			<div class="sac">
				<h2 class="spt">${article.articleTitle}</h2>
				<h2 class="time">发布时间：${article.createByTime}</h2>
				<#if video??>
					<script type='text/javascript' src='${base}/js/jwplayer/jwplayer.js'></script>
					<div id="mediaspace"></div>
					<script>
					jwplayer('mediaspace').setup({
						'autostart': true,
						'flashplayer': '${base}/js/jwplayer/jwplayer.flash.swf',
									  'file': '${base}${video.videoUrl}',
									  'controlbar': 'bottom',
									  'image': '${base}/images/logo.png',
									  'width': '680',
									  'height': '450'
									});
</script>
				</#if>
				<br/><br/>
				<p style="font-family:仿宋">${article.articleContent}</p>
			</div>
			<div class="page">
				<#assign attachmentList=ftlUtil.getAttachmentByArticleId('${article.articleId}')/>
				<#if attachmentList?size==0>当前没有附件
				<#else>附件列表：<br/>
					<#list attachmentList as attachment>
					<a href="${base}/front/attachment.do?command=download&attachmentId=${attachment.attachmentId}">${attachment.attachmentName}</a><br/>
					</#list>
				</#if>
			</div>
		</div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">站点导航</h2>
                <ul>
                    <li <#if dc.dcCode=='BGS'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=BGS">办公室</a></li>
                    <li <#if dc.dcCode=='ZGJJC'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a></li>
                    <li <#if dc.dcCode=='ZHZX'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a></li>
                    <li <#if dc.dcCode=='XFK'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XFK">信访科</a></li>
                    <li <#if dc.dcCode=='XZDD'>class="on"</#if>><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a></li>
                    <li <#if dc.dcCode=='ZADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a></li>
                    <li <#if dc.dcCode=='XTJDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a></li>
                    <li <#if dc.dcCode=='GBDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a></li>
                    <li <#if dc.dcCode=='SYZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a></li>
                    <li <#if dc.dcCode=='WADD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a></li>
                    <li <#if dc.dcCode=='FZDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a></li>
                    <li <#if dc.dcCode=='BFDD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=BFDD">边防大队</a></li>
                    <li <#if dc.dcCode=='LSTJD'>class="on"</#if>><a href="${base}/front/department.do?departmentCode=LSTJD">蓝鲨突击队</a></li>
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