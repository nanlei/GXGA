<#-- 解析通用链接地址 -->
<#function parseLink link>
<#switch link>
	<#case "@back"><#return "javascript:history.go(-1)"><#break>
	<#case "@close"><#return "javascript:window.close()"><#break>
<#default>
	<#return link>
</#switch>
</#function>

<#-- 截短文本 -->
<#function cutText text maxLen suffix="">
<#if (maxLen < 0)><#return text></#if>
<#if (text?string?length > maxLen)>
	<#return action.substring(text, maxLen*2, suffix)>
<#else>
	<#return text>
</#if>
</#function>

<#-- 引入CSS -->
<#function importCSS src>
	<#return "<style type=\"text/css\">@import url('${springMacroRequestContext.getContextPath()}${src}');</style>">
</#function>

<#-- 引入JS -->
<#function importJS src>
	<#return "<script type=\"text/javascript\" src=\"${springMacroRequestContext.getContextPath()}${src}\"></script>">
</#function>

<#-- 将TextArea中输入的内容转换成合适的显示形式 -->
<#function text2html text>
	<#if text?has_content>
	<#return text?html?replace("\n","<br>")>
	<#else>
	<#return "">
	</#if>
</#function>