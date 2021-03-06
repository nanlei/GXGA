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
            <a href="${base}/index.do">首页</a>  > 专项工作 > <a href="${base}/front/job.do?jobId=${job.jobId}">${job.jobTitle}</a> >
            <span>${job.jobTitle}</span>
        </div>
		<div class="left">
			<h2 class="st">
				<span>${job.jobTitle}</span>
			</h2>
			<div class="sac">
				<h2 class="spt">${article.articleTitle}</h2>
				<h2 class="time">发布时间：${article.createByTime} &nbsp;&nbsp;
		                       浏览量：${article.pageView?default('0')}
				</h2>
				<div style="font-size:16px;font-family:仿宋">${article.articleContent}</div>
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
                <h2 class="srnt">专项工作</h2>
                <ul>
                	<#list jobList as j>
                	<li <#if job.jobId==j.jobId>class="on"</#if>><a href="${base}/front/job.do?jobId=${j.jobId}">${j.jobTitle}</a></li>
                	</#list>
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