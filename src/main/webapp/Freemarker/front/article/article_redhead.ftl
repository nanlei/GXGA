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
        <#if article.articleType="BRANCHFILE">
        <div class="location">  
            <a href="Default.htm">首页</a> > <a href="${base}/front/articleList.do?type=BRANCHFILE">分局文件</a> > <span>${article.articleTitle}</span>
        </div>
        <div class="left sal_c">
            <h2 class="st"><span>分局文件</span></h2>
            <h2 class="sno_t"><img src="${base}/images/redhead_branchfile.png" /></h2>
            <div class="sno_no">${branchFileNo}</div>
            <div class="sno_txtbox2">
                <h2 class="sno_txtbox_t">${article.articleTitle}</h2>
                <div class="sno_txt">${article.articleContent}</div>      
            </div>
            <div class="sno_bottom2">
                <span>${bottomEnding}</span>
                <span>${bottomDate}</span>
            </div>
            <br/><br/>
            <div class="page" style="width:836px;margin:0 auto;">
				<#assign attachmentList=ftlUtil.getAttachmentByArticleId('${article.articleId}')/>
				<#if attachmentList?size==0>当前没有附件<br/>
				<#else>附件列表：<br/>
					<#list attachmentList as attachment>
					<a href="${base}/front/attachment.do?command=download&attachmentId=${attachment.attachmentId}">${attachment.attachmentName}</a><br/>
					</#list>
				</#if>
				<#if article.articleType='NOTICE'||article.articleType='SUPERIORFILE'>
					<#assign signList=ftlUtil.getSignByArticleId('${article.articleId}')/>
					<#if signList?size==0>当前没有签收<br/>
					<#else>签收列表：<br/>
						<#list signList as sign>
						${sign.departmentName} 已签收 <br/>
						</#list>
					</#if>		
				</#if>
			</div>
        </div>
        <#elseif article.articleType="NOTICE">
        <div class="location">
        	<a href="Default.htm">首页</a> > <a href="${base}/front/articleList.do?type=NOTICE">通知通报</a> > <span>${article.articleTitle}</span>
        </div>       
        <div class="left sal_c">
            <h2 class="st"><span>通知通报</span></h2>
            <h2 class="sno_t"><img src="${base}/images/redhead_notice.png" /></h2>
            <table border="0" cellspacing="0" cellpadding="0" class="sno_table1">
            	<tr>
            		<td align="left">${topUnit}</td>
            		<td align="center">${noticeNo}</td>
            		<td align="right">${topDate}</td>
            	</tr>
            </table>
            <div class="sno_txtbox">
            	<h2 class="sno_txtbox_t">${article.articleTitle}</h2>
            	<div class="sno_txt">${article.articleContent}</div>
            	<div class="sno_txtbottom">
            		大连市公安局高新园区分局<br/>
            		${bottomDate}
            	</div>
            </div>
            <div class="sno_bottom">
                <div class="sno_bor_bo sno_bor_r"><b>主办单位</b><span>${noticeHost}</span></div>
                <div class="sno_bor_bo"><b>联系人</b><span>${noticeContact}</span></div>
                <div class="sno_bor_r"><b>联系电话</b><span>${noticePhone}</span></div>
                <div><b>发布时间</b><span>${bottomDate}</span></div>
            </div>
            <br/><br/>
            <div class="page" style="width:836px;margin: 60px; auto">
				<#assign attachmentList=ftlUtil.getAttachmentByArticleId('${article.articleId}')/>
				<#if attachmentList?size==0>当前没有附件<br/>
				<#else>附件列表：<br/>
					<#list attachmentList as attachment>
					<a href="${base}/front/attachment.do?command=download&attachmentId=${attachment.attachmentId}">${attachment.attachmentName}</a><br/>
					</#list>
				</#if>
				<#if article.articleType='NOTICE'||article.articleType='SUPERIORFILE'>
					<#assign signList=ftlUtil.getSignByArticleId('${article.articleId}')/>
					<#if signList?size==0>当前没有签收<br/>
					<#else>签收列表：<br/>
						<#list signList as sign>
						${sign.departmentName} 已签收 <br/>
						</#list>
					</#if>		
				</#if>
			</div>
        </div>     
        </#if>
        <!--left end-->
        <@p.topic/>
    </div>
    <!--beginning of bottom -->
    <@p.bottom/>
    <!--end of bottom -->
    <script src="${base}/js/scrolltopcontrol.js"></script>
</body>
</@p.page>