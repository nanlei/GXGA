<#assign base="${springMacroRequestContext.getContextPath()}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>大连市公安局高新园区分局</title>
	<link href="${base}/js/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css">
	<link href="${base}/js/miniui/themes/icons.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="${base}/js/jquery-1.11.2.min.js"></script>
	<script language="javascript" src="${base}/js/miniui/miniui.js"></script>
	<script language="javascript">
	<!-- 
		if (top.location != self.location) {top.location=self.location;}
	-->
	</script>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
</head>
<body oncontextmenu="return false;" onselectstart="return false;">
	<div id="loginWindow" class="mini-window" title="大连市公安局高新园区分局 - 后台管理登录" style="width:340px;height:160px;" showModal="true" showCloseButton="false">
    	<div id="loginForm" style="padding:15px;padding-top:10px;">
        	<table>
            	<tr>
            		<td style="width:60px;">
            			<label for="userName$text">用户名：</label>
            		</td>
            		<td>
            			<input id="userName" name="userName" class="mini-textbox" requiredErrorText="用户名不能为空" required="true" style="width:150px;"/>&nbsp;&nbsp;
            			<a href="#"></a>
                	</td>    
            	</tr>
            	<tr>
                	<td style="width:60px;">
                		<label for="password$text">密&nbsp;&nbsp;&nbsp;码：</label>
                	</td>
                	<td>
                    	<input id="password" name="password" class="mini-password" requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>&nbsp;&nbsp;
                	</td>
            	</tr>
            	<tr>
                	<td></td>
                	<td style="padding-top:10px;">
                    	<a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                    	<a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
                	</td>
            	</tr>
        	</table>
        </div>
	</div>
</body>
<script type="text/javascript" charset="utf-8">
	//Disable miniui expired alerts
	var WinAlerts = window.alert;
	window.alert = function (e) {
		return;
	};

	mini.parse();
	
	var loginWindow = mini.get("loginWindow");
	loginWindow.show();
	
	function onLoginClick(e) {
		var form = new mini.Form("#loginWindow");
		
		form.validate();
		
		if (form.isValid() == false) 
			return;
		
		loginWindow.hide();
		mini.loading("登录验证中，请稍等...", "提示");
		
		var data = form.getData();
		var json = mini.encode(data);
		$.ajax({
			url: "${base}/admin/logon.do",
			type: "post",
			data: {object: json},
			dataType: "json",
			success: function (data) {
				try{
					if(data.status=="true"){
						mini.loading("登录成功，即将转入首页", "提示");
						setTimeout("location.replace('${base}/admin/home.do')",1500);
					}else if(data.status=="invalid"){
						mini.loading("用户名或密码错误，请重试", "提示");
						setTimeout("location.replace('${base}/admin/login.do')",1500);
					}else if(data.status=="locked"){
						mini.loading("用户被锁定，无法登录", "提示");
						setTimeout("location.replace('${base}/admin/login.do')",1500);					
					}else if(data.status=="null"){
						mini.loading("登录失败，请重试", "提示");
						setTimeout("location.replace('${base}/admin/login.do')",1500);
					}
				}catch(e){
					mini.loading("系统出现错误，请重试或与系统管理员联系", "提示");
					setTimeout("location.replace('${base}/admin/login.do')",1500);
				}
			}//End of success
		});//End of Ajax
	}//End of onLoginClick
	
	function onResetClick(e) {
		var form = new mini.Form("#loginWindow");
		form.clear();
	}
</script>
</html>