<#include "config.ftl">
<#include "common_function.ftl">

<#global base="${springMacroRequestContext.getContextPath()}" />

<#-- 页面统一基础模板  -->
<#macro page title=macro_config.default_title tile="" js=[] css=[]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${macro_config.common_title} - ${title?html}</title>
	<#list macro_config.css_path as css>
	<link href="${base}${css}" rel="stylesheet" type="text/css">
	</#list>
	<#if css?has_content>
	<#list css as cssfile><#-- 自定义CSS加载 -->
		<link href="${base}${cssfile}" rel="stylesheet" type="text/css">
	</#list>
	</#if>
	<#list macro_config.js_path as js>
	<script language="javascript" src="${base}${js}"></script>
	</#list>
	<#if js?has_content>
	<#list js as jsfile><#-- 自定义JS加载 -->
		<script language="javascript" src="${base}${jsfile}"></script>
	</#list>
	</#if>
	<meta http-equiv="Content-type" content="text/html; charset=${macro_config.charset}">
	<meta http-equiv="pragma" content="no-cache" />
 <style type="text/css">
    .myrow
    {
        background:#fceee2;
    }
    </style>
</head>
<body <#if ftlUtil.getSystemMode()='DEV'><#else>oncontextmenu="return false"</#if> style="margin:0;padding:0;border:0;width:100%;height:100%;overflow:visible;">
<#nested>
</body>
</html>
</#macro>

<#-- 页面自动分块  -->
<#macro splitter1 size="0">
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%">
		<#if size?index_of("px")!=-1>
		<div size="${size}" showCollapseButton="true">
		<#else>
	    <div size="${size}%" showCollapseButton="true">
		</#if>
	    	<#nested>
		</div>
</#macro>
<#macro splitter2>
		<div showCollapseButton="true">
		     <#nested>   
		</div>
	</div>
</#macro>

<#-- js脚本 -->
<#macro script>
<script>
	//Disable miniui expired alerts
	var WinAlerts = window.alert;
	window.alert = function (e) {
		return;
	};

	document.onkeydown = function(){
		if((window.event.altKey) && ((window.event.keyCode==37) || (window.event.keyCode==39))){
			mini.alert("禁止的操作");
			window.event.returnValue=false;
			window.event.preventDefault();
		}//屏蔽  Alt+  方向键  ←/Alt+  方向键  →
		
		if((window.event.keyCode==8)){
			var elem = event.srcElement;  
            var name = elem.nodeName;  
            if(name=='INPUT' || name=='TEXTAREA'){  
            }else{
            	mini.alert("禁止的操作");
				window.event.keyCode=0;
				window.event.returnValue=false;
				window.event.preventDefault();
            }
		}
		
		if((window.event.keyCode==116) || (window.event.ctrlKey && window.event.keyCode==82)){
			mini.alert("禁止的操作");
			window.event.keyCode=0;
			window.event.returnValue=false;
			window.event.preventDefault();
         }//屏蔽F5刷新键/Ctrl+R
         
         if(window.event.keyCode==122){
         	mini.alert("禁止的操作");
         	window.event.keyCode=0;
         	window.event.returnValue=false;
         	window.event.preventDefault();
         }//屏蔽F11
         
         if(window.event.ctrlKey && window.event.keyCode==78){
         	mini.alert("禁止的操作");
         	window.event.returnValue=false;
         	window.event.preventDefault();
         }//屏蔽  Ctrl+n
         
         if(window.event.shiftKey && window.event.keyCode==121){
         	mini.alert("禁止的操作");
         	window.event.returnValue=false;
         	window.event.preventDefault();
         }//屏蔽 Shift+F10
         
         if(window.event.srcElement.tagName=="A" && window.event.shiftKey){
         	mini.alert("禁止的操作");
         	window.event.returnValue=false;
         	window.event.preventDefault();
         }//屏蔽 shift加鼠标左键新开一网页  

		if((window.event.altKey) && (window.event.keyCode==115)){
			mini.alert("禁止的操作");
			window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px");
			window.event.preventDefault();
			return false;
		}//屏蔽Alt+F4
	}
</script>
<script type="text/javascript">
<#nested>
</script>
</#macro>

<#-- 查询条件区域 -->
<#macro conArea title="" id="" fileUpload="false" action="">
<form id="${id}" method="post" style="padding:5px 5px 0 5px;margin:0;" <#if fileUpload="true">action="${action}" enctype="multipart/form-data"</#if>>
	<fieldset id="fieldset${id}" style="border:solid 1px #aaa;margin:0;"> 
	  <legend onclick="FieldsetClick('fieldset${id}');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">&nbsp;${title}</legend> 
		<ul style="padding:6px 0 0 6px;margin:0px;">
			<#nested>
		</ul>
	</fieldset>
</form>	
</#macro>

<#-- 查询条件 -->
<#macro con id="">
<div id="${id}">
<table style="width:100%;">
	<#nested>
</table>
</div>
</#macro>

<#-- 查询按钮区域 -->
<#macro searchArea colspan="">
<tr>
<td colspan="${colspan}">
	<#nested>
</td>
</tr>
</#macro>

<#-- 查询左侧按钮区域 -->
<#macro searchLeftArea>
	<div style="float:left;">
		<#nested>
	</div>
</#macro>

<#-- 查询右侧按钮区域 -->
<#macro searchRightArea>
	<div style="float:right;">
		<#nested>
	</div>
</#macro>

<#-- 数据区域 -->
<#macro dataArea id="">
<form id="${id}" class="mini-fit" method="post" style="padding:5px;margin:0;">
	<#nested>
</form>	
</#macro>

<#-- 按钮 -->
<#macro actBtn name="" icon="" actionName="" event="" parameter="" width="60">
<#if actionName='#'>
<a class="mini-button" iconCls="${icon}" <#if icon?has_content>plain="true"<#else>style="width:${width}px;margin-right:20px;"</#if> onclick="${event}(<#if parameter?has_content>'${parameter}'</#if>)">${name}</a>
<#else>
<#if ftlUtil.hasPermission('${actionName}','${loginUser.roleId}')>
<a class="mini-button" iconCls="${icon}" <#if icon?has_content>plain="true"<#else>style="width:${width}px;margin-right:20px;"</#if> onclick="${event}(<#if parameter?has_content>'${parameter}'</#if>)">${name}</a>
</#if>
</#if>
</#macro>

<#--权限判定-->
<#macro hasPermission actionName="">
<#if ftlUtil.hasPermission('${actionName}','${loginUser.roleId}')>
<#nested>
</#if>
</#macro>

<#-- CKEditor编辑器 -->
<#-- Toolbar:Full/Basic -->
<#macro ckeditor id="" name="" value="" toolbar="">
<#assign tb="Full"/>
<#if toolbar?has_content>
	<#assign tb=toolbar>
</#if>
<textarea id="${id}" name="${name}">${value}</textarea>
<script language="javascript" src="${base}/components/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	var editor = CKEDITOR.replace('${id}',{toolbar:'${tb}',
		filebrowserBrowseUrl : '${base}/components/ckfinder/ckfinder.html',
    	filebrowserImageBrowseUrl : '${base}/components/ckfinder/ckfinder.html?type=Images',
    	filebrowserFlashBrowseUrl : '${base}/components/ckfinder/ckfinder.html?type=Flash',
		filebrowserUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&Type=File',  
		filebrowserImageUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&Type=Image',  
		filebrowserFlashUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&Type=Flash',
		on: {
                instanceReady: function( ev ) {
                    this.dataProcessor.writer.setRules( 'p', {
                        indent: false,
                        breakBeforeOpen: false,   //<p>之前不加换行
                        breakAfterOpen: false,    //<p>之后不加换行
                        breakBeforeClose: false,  //</p>之前不加换行
                        breakAfterClose: false    //</p>之后不加换行
                    });
                }
            }//end of on
        });
    CKFinder.setupCKEditor(editor, '${base}/components/ckfinder');
</script>
</#macro>