<#assign base="${springMacroRequestContext.getContextPath()}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>大连市公安局高新园区分局</title>
	<link href="${base}/js/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css">
	<link href="${base}/js/miniui/themes/icons.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="${base}/js/jquery-1.11.2.min.js"></script>
	<script language="javascript" src="${base}/js/miniui/miniui.js"></script>
	<script language="javascript" src="${base}/js/admin/admin.js"></script>
	<script language="javascript">
	<!-- 
		if (top.location != self.location) {top.location=self.location;}
	-->
	</script>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<style type="text/css">
	html, body{
    padding:0;
    border:0;
    margin:0;
    width:100%;
    height:100%;
    overflow:hidden;
    font-family:Verdana;
    font-size:12px;    
    line-height:22px;
}
.header{
    background:url(${base}/images/admin/header.gif) repeat-x 0 -1px;
}
 body .header .topNav
    {
        position:absolute;right:8px;top:10px;        
        font-size:12px;
        line-height:25px;
    }
    .header .topNav a
    {
        text-decoration:none;
        color:#222;
        font-weight:normal;
        font-size:12px;
        line-height:25px;
        margin-left:3px;
        margin-right:3px;
    }
    .header .topNav a:hover
    {
        text-decoration:underline;
        color:Blue;
    }   

	</style>
</head>
<body>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	<div class="header" region="north" height="60" showSplit="false" showHeader="false" showSplitIcon="true">
		<h1 style="margin:0;padding:15px;cursor:default;font-family:'Trebuchet MS',Arial,sans-serif;float:left;">
			<a style="cursor:pointer;" onclick="onPageOpenDefault()">大连市公安局高新园区分局</a>
        </h1>
        <div class="topNav">
		        欢迎您&nbsp;${loginUser.realName}&nbsp;(&nbsp;${loginUser.roleName}&nbsp;)<a href="logout.do" style="padding:0 10px;text-decoration:none;">注销</a>
		</div>
		 <div style="position:absolute;right:12px;bottom:0px;font-size:12px;line-height:20px;font-weight:normal;">
            <span style="color:Red;font-family:Tahoma">更换皮肤：</span>
            <select id="selectSkin" onchange="onSkinChange(this.value)" style="width:100px;" >
                <option value="">默认</option>
                <option value="blue">经典蓝</option>
                <option value="gray">经典灰</option>
                <option value="olive2003">橄榄绿</option>
                <option value="blue2003">深蓝</option>
                <option value="blue2010">灰蓝</option>
				<option value="bootstrap">清爽</option>
            </select>
        </div>
    </div>
	<div region="south" showSplit="false" showHeader="false" height="30" showSplitIcon="true">
        <div style="line-height:28px;text-align:center;cursor:default">版权所有 © 大连市公安局高新园区分局</div>
    </div>
    <div showHeader="false" region="west" width="180" maxWidth="250" minWidth="100" style="border:0;" showSplitIcon="true">
        <!--Outlook Menu-->
		<div class="mini-outlookmenu" url="menu.do" activeIndex="0" onItemSelect="onItemSelect" idField="id" parentField="pid" textField="text" >
        </div>
    </div>
    <div title="center" region="center" bodyStyle="overflow:hidden;">
        <iframe id="mainframe" frameborder="0" name="main" style="width:100%;height:100%;" border="0"></iframe>
    </div>
</div>
<script>
	//Disable miniui expired alerts
	var WinAlerts = window.alert;
	window.alert = function (e) {
		return;
	};
	
	mini.parse();
 
    //init iframe src
    var iframe = document.getElementById("mainframe");
    iframe.src = "main.do";
        	
    function onItemSelect(e) {
        var item = e.item;
        iframe.src = item.url;
    }
    
    function onPageOpenDefault(){

    }
</script>
</body>
</html>