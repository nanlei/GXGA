<#assign base="${springMacroRequestContext.getContextPath()}" />
<#assign talentTypeName=ftlUtil.getConstantNameByTypeAndValue('TALENT','${articleCode}')/>
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="talent"/>
    </div>
    <!--topbg end-->
    <div class="mc">
        <div class="location">
            <a href="${base}/index.do">首页</a>  > <a href="${base}/front/talent.do">人才市场</a> > 
            <a href="${base}/front/talent.do?command=sublist&category=${articleCode}">${talentTypeName}</a> >
            <span>${talent.articleTitle}</span>
        </div>
		<div class="left">
			<h2 class="st">
				<span>${talentTypeName}</span>
			</h2>
			<div class="sac">
				<h2 class="spt">${talent.articleTitle}</h2>
				<h2 class="time">发布时间：${talent.createByTime}</h2>
				${talent.articleContent}
			</div>
			<div class="page">
				<#assign attachmentList=ftlUtil.getAttachmentByArticleId('${talent.articleId}')/>
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
            <h2>
                <img src="${base}/images/srnavt.jpg" />
            </h2>
            <div class="rnav">
                <h2 class="srnt">人才市场</h2>
                <ul>
                    <#list categoryList as category>
                    <li <#if category.articleCode=articleCode>class="on"</#if>><a href="${base}/front/talent.do?command=sublist&category=${category.articleCode}">${category.articleCodeName}</a></li>
                    </#list>
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