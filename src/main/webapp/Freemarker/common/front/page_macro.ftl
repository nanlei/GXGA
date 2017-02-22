<#include "../admin/common_function.ftl">

<#assign base="${springMacroRequestContext.getContextPath()}" />

<#-- PAGE -->
<#macro page>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#nested>
</html>
</#macro>

<#-- HEAD -->
<#macro head>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
	<title>大连市公安局高新园区分局</title>
	<script language="javascript">
	<!-- 
		if (top.location != self.location) {top.location=self.location;}
	-->
	</script>
	<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
	<!--banner-->
	<script src="${base}/js/jquery-1.11.2.min.js" type="text/javascript"></script>
	<script src="${base}/js/pic_circle.js" type="text/javascript"></script>
	<!--[if IE 6]>
	<script src="${base}/js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
	<script type="text/javascript">
		DD_belatedPNG.fix('*');
	</script>
	<![endif]-->
	<script lang="javascript">
        var windowWidth = $(window).width();
        var contentWidth = 1000 + 148 * 2;
        var reversePartWidth = (contentWidth - windowWidth) / 2;
        var overPartWidth = windowWidth > contentWidth ? windowWidth - contentWidth : 0;
	function calculateAndResetScrolls()
	{
		windowWidth=$(window).width();
		contentWidth = 1000 + 148 * 2;
		reversePartWidth = -(contentWidth - windowWidth) / 2;
		overPartWidth = (windowWidth > contentWidth ? windowWidth - contentWidth : 0)/2;
		var scrollBarOffset = (contentWidth - windowWidth) / 2;
	    //var rightScrollOffset = $('#left_scroll').offset().left + 1000 +78;
		var rightScrollOffset = overPartWidth + 1000 + 148;
		if(overPartWidth>2)
		{
			$('#left_scroll').css('left',overPartWidth+'px');
			$('#right_scroll').css('left', rightScrollOffset + 'px');
		}
		else
		{
		    //alert('hi');
		    $('#left_scroll').css('left', '0px');
		    
		    //$('#right_scroll').css('right', reversePartWidth + 'px');
		    $('#right_scroll').css('left',  rightScrollOffset+'px');
			$(window).scrollLeft(scrollBarOffset);
			//alert(reversePartWidth);
		}
	}

	$(document).ready(calculateAndResetScrolls)
	$(window).resize(calculateAndResetScrolls);
	</script>
	<!--mothystarbanner-->
   <script type="text/javascript" src="${base}/js/banner.js"></script>
</head>
</#macro>

<#-- BANNER -->
<#macro banner>
<div class="banner">
	<div class="top">
		<div class="logo">
			<a href="${base}/index.do"><img src="${base}/images/logo.png" alt="大连市公安局高新园区分局" /></a>
		</div>
	</div>
	<div id="abgne_fade_pic">
		<a href="" class="ad"><img src="${base}/images/b.jpg" /></a>
		<a href="" class="ad"><img src="${base}/images/b.jpg" /></a>
		<a href="" class="ad"><img src="${base}/images/b.jpg" /></a>
	</div>
</div>
</#macro>

<#-- NAV -->
<#macro nav on="index">
<div class="nav">
	<ul>
		<li <#if on="index">class="on"</#if>><a href="${base}/index.do">首页</a></li>
		<li <#if on="branchoverview">class="on"</#if>><a href="${base}/front/branchoverview.do">分局概况</a></li>
		<li <#if on="law">class="on"</#if>><a href="${base}/front/law.do">法律纵览</a></li>
		<li <#if on="oa">class="on"</#if>><a href="${base}/front/oa.do">网上办公</a></li>
		<li <#if on="talent">class="on"</#if>><a href="${base}/front/talent.do">人才市场</a></li>
		<li <#if on="issue">class="on"</#if>><a href="${base}/front/issue.do">昨日要情</a></li>
		<li <#if on="download">class="on"</#if>><a href="${base}/front/download.do">下载中心</a></li>
		<li <#if on="contact">class="on"</#if>><a href="${base}/front/contact.do">通讯录</a></li>
	</ul>
</div>
</#macro>

<#-- ARTICLE LIST START -->
<#-- Location for article list -->
<#macro articleListLocation articleType="">
<div class="location">
	<a href="${base}/index.do">首页</a> > 
	<span>
	<#if articleType='A'>
	<#elseif articleType='SUPERIORFILE'>上级文件
	<#elseif articleType='BRANCHFILE'>分局文件
	<#elseif articleType='IMAGENEWS'>图片新闻
	<#elseif articleType='NOTICE'>通知通报
	<#elseif articleType='ISSUEWORD'>昨日要情
	<#elseif articleType='WORKREPORT'>工作动态
	<#elseif articleType='POLITICALREPORT'>政工简报
	<#elseif articleType='POLITICALNOTICE'>政工纪检
	<#elseif articleType='LEGAL'>公安法制
	<#elseif articleType='DISCIPLINE'>纪检监察
	<#elseif articleType='POLICECASE'>每日警情
	<#elseif articleType='POLICECULTURE'>警营文化
	<#elseif articleType='EVALUATION'>督导考核
	<#elseif articleType='EXPERIENCE'>经验交流
	<#elseif articleType='LAW'>法律纵览
	<#elseif articleType='INFORMATIONSECURITY'>信息安全
	<#else>
	</#if>
	</span>
</div>
</#macro>

<#-- Panel for article list -->
<#macro articleListPanel articleType="" articleList=[]>
<div class="left">
	<h2 class="st">
		<span>
		<#if articleType='A'>
		<#elseif articleType='SUPERIORFILE'>上级文件
		<#elseif articleType='BRANCHFILE'>分局文件
		<#elseif articleType='IMAGENEWS'>图片新闻
		<#elseif articleType='NOTICE'>通知通报
		<#elseif articleType='ISSUEWORD'>昨日要情
		<#elseif articleType='WORKREPORT'>工作动态
		<#elseif articleType='POLITICALREPORT'>政工简报
		<#elseif articleType='POLITICALNOTICE'>政工纪检
		<#elseif articleType='LEGAL'>公安法制
		<#elseif articleType='DISCIPLINE'>纪检监察
		<#elseif articleType='POLICECASE'>每日警情
		<#elseif articleType='POLICECULTURE'>警营文化
		<#elseif articleType='EVALUATION'>督导考核
		<#elseif articleType='EXPERIENCE'>经验交流
		<#elseif articleType='LAW'>法律纵览
		<#elseif articleType='INFORMATIONSECURITY'>信息安全
		<#else>
		</#if>
		</span>
	</h2>
	<#if (articleList.list?size>0)>
	<ul class="newslist">
		<#list articleList.list as article>
		<#if article.articleType='LAW'>
		<li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/law.do?command=info&lawId=${article.articleId}">${article.articleTitle}</a></li>
		<#elseif article.articleType='INFORMATIONSECURITY'>
		<li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/informationsecurity.do?command=info&informationSecurityId=${article.articleId}">${article.articleTitle}</a></li>
		<#elseif article.articleType='ISSUEWORD'>
		<li><span class="date2"></span><a href="${base}${article.filePath}">昨日要情${article.issueDate}</a></li>
		<#elseif article.articleType='POLICECASE'>
			<#if article.type=='ARTICLE'>
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
            <#elseif article.type='WORD'>
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}${article.filePath}">${article.articleTitle}</a></li>
            </#if>
        <#elseif article.articleType="BRANCHFILE">
        	<#if article.articleBizType="NOR">
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
        	<#elseif article.articleBizType="RED">
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/articleRedHead.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
        	</#if>
        <#elseif article.articleType="NOTICE">
        	<#if article.articleBizType="NOR">
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
        	<#elseif article.articleBizType="RED">
            <li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/articleRedHead.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
        	</#if>
		<#else>
		<li><span class="date2">【<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if>】</span><a href="${base}/front/article.do?articleId=${article.articleId}">${article.articleTitle}</a></li>
		</#if>
		</#list>
	</ul>
	<@p.paging articleList />
	</#if>
</div>
</#macro>

<#-- Type nav for article list -->
<#macro articleListTypeNav articleType="">
<div class="rnav">
	<h2 class="srnt">
	<#if articleType='A'>
	<#elseif articleType='SUPERIORFILE'>上级文件
	<#elseif articleType='BRANCHFILE'>分局文件
	<#elseif articleType='IMAGENEWS'>图片新闻
	<#elseif articleType='NOTICE'>通知通报
	<#elseif articleType='ISSUEWORD'>昨日要情
	<#elseif articleType='WORKREPORT'>工作动态
	<#elseif articleType='POLITICALREPORT'>政工简报
	<#elseif articleType='POLITICALNOTICE'>政工纪检
	<#elseif articleType='LEGAL'>公安法制
	<#elseif articleType='DISCIPLINE'>纪检监察
	<#elseif articleType='POLICECASE'>每日警情
	<#elseif articleType='POLICECULTURE'>警营文化
	<#elseif articleType='EVALUATION'>督导考核
	<#elseif articleType='EXPERIENCE'>经验交流
	<#elseif articleType='LAW'>法律纵览
	<#elseif articleType='INFORMATIONSECURITY'>信息安全
	<#else>
	</#if>
	</h2>
	<ul>
		<li <#if articleType='LAW'>class="on"</#if>><a href="${base}/front/law.do">法律纵览</a></li>
		<li <#if articleType='INFORMATIONSECURITY'>class="on"</#if>><a href="${base}/front/informationsecurity.do">信息安全</a></li>
		<li <#if articleType='SUPERIORFILE'>class="on"</#if>><a href="${base}/front/articleList.do?type=SUPERIORFILE">上级文件</a></li>
		<li <#if articleType='IMAGENEWS'>class="on"</#if>><a href="${base}/front/articleList.do?type=IMAGENEWS">图片新闻</a></li>
		<li <#if articleType='BRANCHFILE'>class="on"</#if>><a href="${base}/front/articleList.do?type=BRANCHFILE">分局文件</a></li>
		<li <#if articleType='NOTICE'>class="on"</#if>><a href="${base}/front/articleList.do?type=NOTICE">通知通报</a></li>
		<li <#if articleType='ISSUEWORD'>class="on"</#if>><a href="${base}/front/articleList.do?type=ISSUEWORD">昨日要情</a></li>
		<li <#if articleType='WORKREPORT'>class="on"</#if>><a href="${base}/front/articleList.do?type=WORKREPORT">工作动态</a></li>
		<li <#if articleType='POLITICALREPORT'>class="on"</#if>><a href="${base}/front/articleList.do?type=POLITICALREPORT">政工简报</a></li>
		<li <#if articleType='POLITICALNOTICE'>class="on"</#if>><a href="${base}/front/articleList.do?type=POLITICALNOTICE">政工纪检</a></li>
		<li <#if articleType='LEGAL'>class="on"</#if>><a href="${base}/front/articleList.do?type=LEGAL">公安法制</a></li>
		<li <#if articleType='DISCIPLINE'>class="on"</#if>><a href="${base}/front/articleList.do?type=DISCIPLINE">纪检监察</a></li>
		<li <#if articleType='POLICECASE'>class="on"</#if>><a href="${base}/front/articleList.do?type=POLICECASE">每日警情</a></li>
		<li <#if articleType='POLICECULTURE'>class="on"</#if>><a href="${base}/front/articleList.do?type=POLICECULTURE">警营文化</a></li>
		<li <#if articleType='EVALUATION'>class="on"</#if>><a href="${base}/front/articleList.do?type=EVALUATION">督导考核</a></li>
		<li <#if articleType='EXPERIENCE'>class="on"</#if>><a href="${base}/front/articleList.do?type=EXPERIENCE">经验交流</a></li>
	</ul>
</div>
</#macro>
<#-- ARTICLE LIST END -->

<#-- ARTICLE  START -->
<#-- Location for article -->
<#macro articleLocation article="">
<div class="location">
	<a href="${base}/index.do">首页</a> > 
	<#if article.articleType='A'>
	<#elseif article.articleType='LAW'>
	<a href="${base}/front/law.do">法律纵览</a>
	<#elseif article.articleType='INFORMATIONSECURITY'>
	<a href="${base}/front/law.do">信息安全</a>
	<#elseif article.articleType='SUPERIORFILE'>
	<a href="${base}/front/articleList.do?type=${article.articleType}">上级文件</a>
	<#elseif article.articleType='BRANCHFILE'>
	<a href="${base}/front/articleList.do?type=${article.articleType}">分局文件</a>
	<#elseif article.articleType='IMAGENEWS'>
	<a href="${base}/front/articleList.do?type=${article.articleType}">图片新闻</a>
    <#elseif article.articleType='NOTICE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">通知通报</a>
    <#elseif article.articleType='WORKREPORT'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">工作动态</a>
    <#elseif article.articleType='POLITICALREPORT'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">政工简报</a>
    <#elseif article.articleType='POLITICALNOTICE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">政工纪检</a>
    <#elseif article.articleType='LEGAL'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">公安法制</a>
    <#elseif article.articleType='DISCIPLINE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">纪检监察</a>
    <#elseif article.articleType='POLICECASE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">每日警情</a>
    <#elseif article.articleType='POLICECULTURE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">警营文化</a>
    <#elseif article.articleType='EVALUATION'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">督导考核</a>
    <#elseif article.articleType='EXPERIENCE'>
    <a href="${base}/front/articleList.do?type=${article.articleType}">经验交流</a>
    <#else>
    </#if>
    > <span>${article.articleTitle}</span>
</div>
</#macro>

<#--Panel for article -->
<#macro articlePanel article="">
<div class="left">
	<h2 class="st">
		<span>
			<#if article.articleType='A'>
			<#elseif article.articleType='LAW'>法律纵览
			<#elseif article.articleType='INFORMATIONSECURITY'>信息安全
			<#elseif article.articleType='SUPERIORFILE'>上级文件
			<#elseif article.articleType='BRANCHFILE'>分局文件
			<#elseif article.articleType='IMAGENEWS'>图片新闻
			<#elseif article.articleType='NOTICE'>通知通报
			<#elseif article.articleType='WORKREPORT'>工作动态
			<#elseif article.articleType='POLITICALREPORT'>政工简报
			<#elseif article.articleType='POLITICALNOTICE'>政工纪检
			<#elseif article.articleType='LEGAL'>公安法制
			<#elseif article.articleType='DISCIPLINE'>纪检监察
			<#elseif article.articleType='POLICECASE'>每日警情
			<#elseif article.articleType='POLICECULTURE'>警营文化
			<#elseif article.articleType='EVALUATION'>督导考核
			<#elseif article.articleType='EXPERIENCE'>经验交流
			<#elseif article.articleType='MONTHLYSTAR'>每月之星
			<#else>
			</#if>
		</span>
	</h2>
	<#if article.articleType='IMAGENEWS'><#-- IMAGE -->
		<#assign imageList=ftlUtil.getImageByArticleId('${article.articleId}')/>
	<div class="sac">
		<h2 class="spt">${article.articleTitle}</h2>
		<h2 class="time">发布时间：<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if></h2>
		<#if imageList?size!=0>
		<div class="sac">
			<#list imageList as image>
			<p style="text-align:center"><img title="${image.imageName}" src="${base}${image.imageUrl}"></p><br/><br/>
			</#list>
		</div>
		</#if>
	</div>
	<div class="sac">
	<div style="font-family:仿宋">${article.articleContent}</div>
	<#else><#-- ATTACHMENT -->
	<div class="sac">
		<h2 class="spt">${article.articleTitle}</h2>
		<h2 class="time">发布时间：<#if article.articleDate??>${article.articleDate}<#else>${article.createByTime}</#if></h2>
		<div style="font-family:仿宋">${article.articleContent}</div>
	</div>
	<div class="page">
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
	</#if>
	</div>
</div>
</#macro>

<#-- Type nave for article -->
<#macro articleTypeNav article="">
<div class="rnav">
	<h2 class="srnt">
	<#if article.articleType='A'>
	<#elseif article.articleType='LAW'>法律纵览
	<#elseif article.articleType='INFORMATIONSECURITY'>信息安全
	<#elseif article.articleType='SUPERIORFILE'>上级文件
	<#elseif article.articleType='BRANCHFILE'>分局文件
	<#elseif article.articleType='IMAGENEWS'>图片新闻
	<#elseif article.articleType='NOTICE'>通知通报
	<#elseif article.articleType='WORKREPORT'>工作动态
	<#elseif article.articleType='POLITICALREPORT'>政工简报
	<#elseif article.articleType='POLITICALNOTICE'>政工纪检
	<#elseif article.articleType='LEGAL'>公安法制
	<#elseif article.articleType='DISCIPLINE'>纪检监察
	<#elseif article.articleType='POLICECASE'>每日警情
	<#elseif article.articleType='POLICECULTURE'>警营文化
	<#elseif article.articleType='EVALUATION'>督导考核
	<#elseif article.articleType='EXPERIENCE'>经验交流
	<#else>
	</#if>
	</h2>
	<ul>
		<#if article.articleType='A'>
		<#elseif article.articleType='LAW'>
		<li class="on"><a href="${base}/front/law.do">法律纵览</a></li>
		<#elseif article.articleType='INFORMATIONSECURITY'>
		<li class="on"><a href="${base}/front/informationsecurity.do">信息安全</a></li>
		<#elseif article.articleType='SUPERIORFILE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">上级文件</a></li>
		<#elseif article.articleType='BRANCHFILE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">分局文件</a></li>
		<#elseif article.articleType='IMAGENEWS'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">图片新闻</a></li>
		<#elseif article.articleType='NOTICE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">通知通报</a></li>
		<#elseif article.articleType='WORKREPORT'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">工作动态</a></li>
		<#elseif article.articleType='POLITICALREPORT'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">政工简报</a></li>
		<#elseif article.articleType='POLITICALNOTICE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">政工纪检</a></li>
		<#elseif article.articleType='LEGAL'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">公安法制</a></li>
		<#elseif article.articleType='DISCIPLINE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">纪检监察</a></li>
		<#elseif article.articleType='POLICECASE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">每日警情</a></li>
		<#elseif article.articleType='POLICECULTURE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">警营文化</a></li>
		<#elseif article.articleType='EVALUATION'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">督导考核</a></li>
		<#elseif article.articleType='EXPERIENCE'>
		<li class="on"><a href="${base}/front/articleList.do?type=${article.articleType}">经验交流</a></li>
		<#else>
		</#if>
	</ul>
</div>
</#macro>

<#macro oaPanel>
<#assign command=RequestParameters.command?default('') />
<div class="rnav">
<h2 class="srnt">网上办公</h2>
	<ul>
	    <li <#if command=''>class="on"</#if>><a href="${base}/front/oa.do">会议列表</a></li>
	    <li <#if command?contains('assetrepair')>class="on"</#if>><a href="${base}/front/oa.do?command=assetrepair">设备维修</a></li>
	    <li <#if command?contains('overtimemeal')>class="on"</#if>><a href="${base}/front/oa.do?command=overtimemeal">加班用餐</a></li>
	    <li <#if command?contains('minxinwang')>class="on"</#if>><a href="${base}/front/oa.do?command=minxinwang"">民心网</a></li>
        <li <#if command?contains('minyiwang')>class="on"</#if>><a href="${base}/front/oa.do?command=minyiwang"">民意网</a></li>
        <li <#if command?contains('pubcomm')>class="on"</#if>><a href="${base}/front/oa.do?command=pubcomm"">公众交流平台</a></li>
        <li <#if command?contains('officalmailbox')>class="on"</#if>><a href="${base}/front/oa.do?command=officalmailbox"">局长信箱</a></li>
	</ul>
</div>
</#macro>

<#macro duty>
<div class="m_duty s_od mb_20">
	<h2 class="mt1"><span class="f_r">详情</span>值班安排</h2>
	<h2 class="m_txt1"><span class="f_r"><a href="${base}/front/duty.do">查看详情</a></span>${ftlUtil.getToday()}</h2>
	<div class="mod_c">
		<#assign dutyList=ftlUtil.getDutyForIndex()/>
		<#list dutyList as duty>
		<h2 class="mod_t">${duty.dutyDate}</h2>
		<ul>
			<li><span>局领导</span>${duty.dutyManager}</li>
			<li><span>值班长</span>${duty.dutyLeader}</li>
			<li><span>值班民警</span>${duty.dutyPolice}</li>
		</ul>
		</#list>
	</div>
</div>
</#macro>

<#macro rank>
<div class="m_contribute s_od mb_20 ">
	<h2 class="mt3">投稿统计</h2>
	<ul>
		<li class="li2"><span class="s1">名次</span><span class="s2">单位</span><span class="s1">数量</span></li>
	</ul>
	<marquee id="b" onmouseover="this.stop()" style=" width: 200px; height: 174px; background: #E9F4E1;" onmouseout="this.start()" scrollamount="2" direction="up">
		<ul>
			<#assign rankList=ftlUtil.getRankList()/>
			<#list rankList as rank>
			<li><span class="s1">${rank_index+1}</span><span class="s2">${rank.departmentName}</span><span class="s1">${rank.rankValue}</span></li>
			</#list>
		</ul>
	</marquee>
</div>
</#macro>

<#macro login>
<div class="lo">
	<h2>姓名：<input id="userName" type="text" name="userName" class="txtbox2" /></h2>
	<h2>密码：<input id="password" type="password" name="password" class="txtbox2" /></h2>
	<input id="loginButton" type="button" value="" class="btn2" />
</div>
</#macro>

<#macro imageLink>
<div class="s_od oa"><a href="http://10.80.1.225:9080/oa/oa/index.jsp"><img src="${base}/images/oa.jpg" /></a></div>
<div class="s_od em"><a href="${base}/front/mailbox.do"><img src="${base}/images/e.jpg" /></a></div>
</#macro>

<#macro frontJS>
<script type="text/javascript">
$(function(){
	<#list 1..9 as item>
	$('#Select${item}').change(function(){
		var url=$(this).children('option:selected').val();
		if(url!="0"){
			window.open(url);
		}
	});
	</#list>
	
	$('#loginButton').click(function(){
		var userName=$('#userName').val();
		var password=$('#password').val();
		var json='{\"userName\":"'+userName+'",\"password\":"'+password+'"}';
		if(userName==''){
			alert('请输入用户名');
			return;
		}
		if(password==''){
			alert('请输入密码');
			return;
		}
		$.ajax({
			url: "${base}/admin/logon.do",
			type: "post",
			data: {object:json},
			dataType: "json",
			success: function (data) {
				try{
					if(data.status=="true"){
						location.replace('${base}/admin/home.do');
					}else if(data.status=="invalid"){
						location.replace('${base}/admin/login.do');
					}else if(data.status=="locked"){
						location.replace('${base}/admin/login.do');					
					}
				}catch(e){
					alert("系统出现错误，请重试或与系统管理员联系");
				}
			}//End of success
		});//End of Ajax
	});
});
</script>
</#macro>

<#macro topic>
<#assign jobList=ftlUtil.getJobList() />
<h2 class="mt6"><a href="">专项工作</a></h2>
<div class="slideGroup">
	<div class="parBd">
		<div class="slideBox">
			<a class="sPrev" href="javascript:void(0)"></a>
			<ul>
				<#list jobList as job>
				<li><a href="/front/job.do?jobId=${job.jobId}"><h2>${job.jobTitle}</h2><img src="${base}${job.jobImageUrl}" /></a></li>
				</#list>
			</ul>
			<a class="sNext" href="javascript:void(0)"></a>
		</div>
	</div>
</div>
<script src="${base}/js/jquery.pack.js"></script>
<script src="${base}/js/jquery.SuperSlide.js"></script>
<script type="text/javascript">
/*
 SuperSlide组合注意：
1、内外层mainCell、targetCell、prevCell、nextCell等对象不能相同，除非特殊应用；
2、注意书写顺序，通常先写内层js调用，再写外层js调用
*/
/* 内层图片滚动切换 */
jQuery(".slideGroup .slideBox").slide({
	mainCell: "ul",
	vis: 4,
	prevCell: ".sPrev",
	nextCell: ".sNext",
	effect: "leftLoop"
});
</script>
</#macro>

<#macro bottom>
<div class="b_box">
	<div class="bottom">
		<h2 class="f_nav">
			<a href="${base}/index.do">首页</a>|
			<a href="${base}/front/branchoverview.do">分局概况</a>|
			<a href="${base}/front/law.do">法律纵览</a>|
			<a href="${base}/front/oa.do">网上办公</a>|
			<a href="${base}/front/talent.do">人才市场</a>|
			<a href="${base}/front/informationsecurity.do">信息安全</a>|
			<a href="${base}/front/download.do">下载中心</a>|
			<a href="${base}/front/contact.do">通讯录</a>|
			<a href="${base}/admin/login.do">网站管理</a>
		</h2>
		<p class="btxt">版权所有：大连市公安局高新园区分局</p>
	</div>
</div>
</#macro>

<#-- Paging -->
<#-- 处理分页参数 -->
<#function getPageUrl pageNum pageSize>
<#local pageUrl=base+ftlUtil.getFullUrlWithoutPageInfo()>
<#if pageUrl?ends_with("?")>
<#return pageUrl + "pageSize=" + pageSize + "&pageIndex=" + pageNum>
<#else>
<#return pageUrl + "&pageSize=" + pageSize + "&pageIndex=" + pageNum>
</#if>
</#function>

<#-- 全部或分页显示 -->
<#function getPageUrlResize size>
<#local pageUrl=base+ftlUtil.getFullUrlWithoutPageInfo()>
<#if pageUrl?ends_with("?")>
<#return pageUrl + "pageIndex=1&pageSize=" + size>
<#else>
<#return pageUrl + "&pageIndex=1&pageSize=" + size>
</#if>
</#function>

<#-- 分页信息 -->
<#macro paging pagingList>
<#local pageCount=pagingList.pageCount>
<#local rowCount=pagingList.rowCount>
<#local pageNum=pagingList.pageNum>
<#local pageSize=pagingList.pageSize>
<#if rowCount == 0>
	<#if useFlag?exists>
		<div class="digg"><span>没有相关记录</span></div>
	<#else>
		<#assign useFlag = 1>
	</#if>
<#else>
	<div class="digg">
	<#if (pageNum !=1)>
		<#if (pageNum != pageCount )>
			<span>当前显示 ${((pageNum -1) * pageSize +1)}-${pageNum * pageSize} 条 / 共 ${rowCount} 条</span>
		<#else>
			<#if ((pageNum -1) * pageSize +1)=(rowCount)>
				<span>当前显示 ${rowCount} 条 / 共 ${rowCount} 条</span>
			<#else>
				<span>当前显示 ${((pageNum -1) * pageSize +1)}-${rowCount} 条 / 共 ${rowCount} 条</span>
			</#if>
		</#if>
	<#else>
		<#if (pageNum != pageCount )>
			<span>当前显示 1-${pageSize} 条 / 共 ${rowCount} 条</span>
		<#else>
			<span>当前显示 1-${rowCount} 条 / 共 ${rowCount} 条</span>
		</#if>
	</#if>
	<#if (pageCount <= 5)>
		<#local startPage = 1>
		<#local endPage = pageCount>
	<#elseif (pageNum + 5 > pageCount)>
		<#local startPage = pageCount - 10>
		<#local endPage = pageCount>
	<#elseif (pageNum - 5 < 1)>
		<#local startPage = 1>
		<#local endPage = 5>
	<#else>
		<#local startPage = pageNum - 5>
		<#local endPage = pageNum + 5>
	</#if>
	<#if (pageCount > 1)>
	  <#if (pageNum != 1)>
	     <a href="${getPageUrl(1,pageSize)}" title="第一页">首页</a>
	     <a href="${getPageUrl(pageNum-1,pageSize)}" title="上页">上一页</a>
	  </#if>
	  <#if (startPage<0)>
	  	<#local startPage = 1>
	  <#elseif ((endPage-startPage)>=10)>
	  	<#local startPage = startPage+3>
	  </#if>
	  <#list startPage..endPage as x>
	     <#if x=pageNum>
	        <span class="current"><a href="#">${x}</a></span>
	     <#else>
	        <a href="${getPageUrl(x,pageSize)}">${x}</a>
	     </#if>
	  </#list>
	  <#if (pageCount != pageNum)>
	     <a href="${getPageUrl(pageNum+1,pageSize)}" title="下页">下一页</a>
	     <a href="${getPageUrl(pageCount + 1,pageSize)}" title="最后一页">尾页</a>
	   </#if> 
	</#if>
	</div>
</#if>
</#macro>

<#macro birthday birthdayList>
<#if (birthdayList?size>0)>
<div id="right_scroll">
        <div class="right_scroll_bg">
        </div>
        <div class="right_scroll_context">
            <div class="right_scroll_contextitle">
                <a class="right_scroll_contextitle_close" href="javascript:void(0);" title="右侧关闭">
                    ×
                </a>
                
            </div>
            <div class="right_scroll_contextxt">
                <div class="content_title">
                    <#assign dayOfWeek=ftlUtil.getDayOfWeek()/>
	                <#assign dow=''/>
	                <#if dayOfWeek=1><#assign dow='星期日'/>
	                <#elseif dayOfWeek=2><#assign dow='星期一'/>
	                <#elseif dayOfWeek=3><#assign dow='星期二'/>
	                <#elseif dayOfWeek=4><#assign dow='星期三'/>
	                <#elseif dayOfWeek=5><#assign dow='星期四'/>
	                <#elseif dayOfWeek=6><#assign dow='星期五'/>
	                <#elseif dayOfWeek=7><#assign dow='星期六'/>
	                </#if>
	                <span>${ftlUtil.getToday()} ${dow}</span>
                    <h2 class="f_date">${ftlUtil.getToday()?split('-')[2]}</h2>
                    <h2 class="f_d2"><#--闰九月廿八--></h2>
                </div>
                <div class="content_list">
                    <h2 class="fr1">祝：</h2>
                    <script type="text/javascript">
                        //js无缝滚动代码
                        function marquee(i, direction) {
                            var obj = document.getElementById("marquee" + i);
                            var obj1 = document.getElementById("marquee" + i + "_1");
                            var obj2 = document.getElementById("marquee" + i + "_2");
                            if (direction == "up") {
                                if (obj2.offsetTop - obj.scrollTop <= 0) {
                                    obj.scrollTop -= (obj1.offsetHeight + 20);
                                } else {
                                    var tmp = obj.scrollTop;
                                    obj.scrollTop++;
                                    if (obj.scrollTop == tmp) {
                                        obj.scrollTop = 1;
                                    }
                                }
                            } else {
                                if (obj2.offsetWidth - obj.scrollLeft <= 0) {
                                    obj.scrollLeft -= obj1.offsetWidth;
                                } else {
                                    obj.scrollLeft++;
                                }
                            }
                        }

                        function marqueeStart(i, direction) {
                            var obj = document.getElementById("marquee" + i);
                            var obj1 = document.getElementById("marquee" + i + "_1");
                            var obj2 = document.getElementById("marquee" + i + "_2");

                            obj2.innerHTML = obj1.innerHTML;
                            var marqueeVar = window.setInterval("marquee(" + i + ", '" + direction + "')", 20);
                            obj.onmouseover = function () {
                                window.clearInterval(marqueeVar);
                            }
                            obj.onmouseout = function () {
                                marqueeVar = window.setInterval("marquee(" + i + ", '" + direction + "')", 20);
                            }
                        }
                    </script>
                    <div id="marquee2" class="marqueetop">
                        <ul id="marquee2_1">
							<#list birthdayList as birthday>
                            <li>
                            	<h2><span>${birthday.employeeName}</span>${birthday.departmentName}</h2>
                            </li>
                        	</#list>
                        </ul>
                        <ul id="marquee2_2"></ul>
                    </div>
                    <#if (birthdayList?size>3)>
                    <script type="text/javascript">marqueeStart(2, "up");</script>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var b_v = navigator.appVersion;
        var IE6 = b_v.search(/MSIE 6/i) != -1;
        function objscroll(divname) {
            var objs = document.getElementById(divname),
                divname = $("#" + divname);
            var o = {};
            o.obj = divname;
            o.objH = divname.height();
            o.objs = objs;

            $(function () {
                if ($("body").width() >= 1) { $("#right_scroll").show(); }
                $(window).resize(function () {
                    if ($("body").width() <= 1) {
                        $("#right_scroll").hide();
                    } else if ($("body").width() >= 1 && $(".right_scroll_bg").text() == 1) {
                        $("#right_scroll").hide();
                    } else {
                        $("#right_scroll").show();
                    }
                });
            });
            $(window).scroll(function () {
                var bodyH = $(document).scrollTop(),
                     headH = $(".banner").height();
                menuH = $(".nav").height();
                o.objs.style.top = (headH + menuH) + "px";
                //var bodyW = $("#left_scroll").position().left + 148 + 1000 + 4 - $(document).scrollLeft();
                if (IE6) {
                    if (bodyH >= headH + menuH) {
                        o.objs.style.top = bodyH + "px";
                    }
                } else if (bodyH >= headH + menuH) {
                    //divname.css("position", "fixed");
                    //divname.css("left", bodyW + "px");
                    //o.objs.style.top = 0;
                    divname.css("top", $(window).scrollTop() + "px");
                } else {
                    //divname.css("position", "absolute");
                    //divname.css("left", "1152px");
                    //divname.css("right", "0px");
                }
            });
        }
        objscroll("right_scroll");

        $(".right_scroll_contextitle_close").click(function () {
            $("#right_scroll").hide().find(".right_scroll_bg").text("1");
        });
    </script>
</#if>
</#macro>

<#macro emergencyNotice emergencyNoticeList>
<#if (emergencyNoticeList?size>0)>
	<div id="left_scroll">
        <div class="left_scroll_bg">
        </div>
        <div class="left_scroll_context">
            <div class="left_scroll_contextitle">
                <a class="left_scroll_contextitle_close" href="javascript:void(0);" title="左侧关闭">
                    ×
                </a>  
            </div>
            <div class="left_scroll_contextxt">
                <script src="js/jquery.imgscroll.min.js"></script>
                <script type="text/javascript">
                    $(function () {
                        imgScroll.rolling({
                            name: 'g1',
                            width: '128px',
                            height: '87px',
                            direction: 'top',
                            speed: 10,
                            addcss: true
                        });
                    })
                </script>
                <div class="content_list<#if (emergencyNoticeList?size>4)>g1</#if>">
                    <ul>
                    	<#list emergencyNoticeList as emergencyNotice>
                        <li><a href="${base}${emergencyNotice.noticeAttachmentUrl}"><img src="${base}${emergencyNotice.noticeImageUrl}" title="${emergencyNotice.noticeTitle}"/></a></li>
                    	</#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
        var b_v = navigator.appVersion;
        var IE6 = b_v.search(/MSIE 6/i) != -1;
        function objscroll(divname) {
            var objs = document.getElementById(divname),
                divname = $("#" + divname);
            var o = {};
            o.obj = divname;
            o.objH = divname.height();
            o.objs = objs;

            $(function () {
                if ($("body").width() >= 1) { $("#left_scroll").show(); }
                $(window).resize(function () {
                    if ($("body").width() <= 1) {
                        $("#left_scroll").hide();
                    } else if ($("body").width() >= 1 && $(".left_scroll_bg").text() == 1) {
                        $("#left_scroll").hide();
                    } else {
                        $("#left_scroll").show();
                    }
                });
            });
            $(window).scroll(function () {
                var bodyH = $(document).scrollTop(),
                     headH = $(".banner").height();
                menuH = $(".nav").height();
                o.objs.style.top = (headH + menuH) + "px";
                //alert($(window).scrollTop());
                //var bodyW = $(document).scrollLeft();
                if (IE6) {
                    if (bodyH >= headH + menuH) {
                        o.objs.style.top = bodyH + "px";
                    }
                } else if (bodyH >= headH + menuH) {
                    //divname.css("left", (-bodyW) + "px");
                    divname.css("top", $(window).scrollTop() + "px");
                } else {
                    //divname.css("left", "0px");
                }
            });
        }
        objscroll("left_scroll");

        $(".left_scroll_contextitle_close").click(function () {
            $("#left_scroll").hide().find(".left_scroll_bg").text("1");
        });
    </script>
    <script src="${base}/js/jquery-1.11.2.min.js"></script>
</#if>
</#macro>

<#-- Monthly Star -->
<#macro monthlystar>
<div class="Monthlystar">
	<h2><a href=""><img src="images/ms_title.png" /></a></h2>
	<div id="focus">
		<ul>
			<#list monthlyStarImages as msi>
				<li><a href="${base}/front/monthlystar.do?command=info&id=${monthlyStar.articleId}" target="_blank"><img src="${msi.imageUrl}" alt="${msi.imageName}" /><span>${msi.imageName}</span></a></li>
			</#list>
		</ul>
	</div>
	<div class="ms_txt"><a href="${base}/front/monthlystar.do?command=info&id=${monthlyStar.articleId}">${monthlyStar.articleTitle}</a></div>
</div>
</#macro>

<#macro monthlystar1_bak>
<div class="Monthlystar">
	<h2><a href="${base}/front/monthlystar.do"><img src="${base}/images/ms_title.png" /></a></h2>
	<#if monthlyStar??>
	<p><a href="${base}/front/monthlystar.do?command=info&id=${monthlyStar.articleId}"><img src="${base}${monthlyStar.imageUrl}" width="259" height="217"/></p>
	<div><a href="${base}/front/monthlystar.do?command=info&id=${monthlyStar.articleId}">${monthlyStar.articleTitle}</a></div>
	<#else>
	</#if>
</div>
</#macro>

<#-- Index Mailbox -->
<#macro indexMailbox>
<div class="i_em">
	<a href="${base}/front/mailbox.do"><img src="images/em.png" /></a>
</div>
</#macro>

<#-- zdlist -->
<#macro zdList>
<h2 class="mt5">站点导航</h2>
<ul class="zdlist">
	<li><a href="${base}/front/department.do?departmentCode=BGS">办公室</a></li>
	<li><a href="${base}/front/department.do?departmentCode=ZGJJC">政工纪检处</a></li>
	<li><a href="${base}/front/department.do?departmentCode=ZHZX">指挥中心</a></li>
	<li><a href="${base}/front/department.do?departmentCode=XFK">信访科</a></li>
	<li><a href="http://10.80.48.78:8080/police/login_view.action">刑侦大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=ZADD">治安大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=XTJDD">巡特警大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=GBDD">国保大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=SYZDD">食药侦大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=WADD">网安大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=FZDD">法制大队</a></li>
	<li><a href="${base}/front/department.do?departmentCode=CRJDD">出入境大队</a></li>
</ul>
</#macro>

<#-- dhLink -->
<#macro dhLink qgdhList=[] sndhList=[] sjdhList=[] sjzsbmdhList=[] qxfjdhList=[]>
<div class="bg_g">
	<select id="Select5" class="se_box2">
		<option value="0">全国导航</option>
		<#list qgdhList as qgdh>
		<option value="${qgdh.linkUrl}">${qgdh.linkDescription}</option>
		</#list>
	</select>
	<select id="Select6" class="se_box2">
		<option value="0">省内导航</option>
		<#list sndhList as sndh>
		<option value="${sndh.linkUrl}">${sndh.linkDescription}</option>
		</#list>
	</select>
	<select id="Select7" class="se_box2">
		<option value="0">市局导航</option>
		<#list sjdhList as sjdh>
		<option value="${sjdh.linkUrl}">${sjdh.linkDescription}</option>
		</#list>
	</select>
	<select id="Select8" class="se_box2">
		<option value="0">市局直属部门导航</option>
		<#list sjzsbmdhList as sjzsbmdh>
		<option value="${sjzsbmdh.linkUrl}">${sjzsbmdh.linkDescription}</option>
		</#list>
	</select>
	<select id="Select9" class="se_box2">
		<option value="0">区县分局导航</option>
		<#list qxfjdhList as qxfjdh>
		<option value="${qxfjdh.linkUrl}">${qxfjdh.linkDescription}</option>
		</#list>
	</select>
</div>
</#macro>

<#-- asLink -->
<#macro asLink qgkList=[] stkList=[] sjkList=[]>
<div class="bg_g">
    <h2 class="mt5 mb_10">应用系统</h2>
	<select id="Select1" class="se_box2">
		<option value="0">全国库</option>
		<#list qgkList as qgk>
		<option value="${qgk.linkUrl}">${qgk.linkDescription}</option>
		</#list>
	</select>
	<select id="Select2" class="se_box2">
		<option value="0">省厅库</option>
		<#list stkList as stk>
		<option value="${stk.linkUrl}">${stk.linkDescription}</option>
		</#list>
	</select>
	<select id="Select3" class="se_box2">
		<option value="0">市局库</option>
		<#list sjkList as sjk>
		<option value="${sjk.linkUrl}">${sjk.linkDescription}</option>
		</#list>
	</select>
	<select id="Select4" class="se_box2">
		<option value="0">分局库</option>
		<#list fjkList as fjk>
		<option value="${fjk.linkUrl}">${fjk.linkDescription}</option>
		</#list>
	</select>
</div>
</#macro>

<#macro showNewGif date>
<#if daysOffset(date?string)<=3><img src="${base}/images/new.gif" /></#if>
</#macro>