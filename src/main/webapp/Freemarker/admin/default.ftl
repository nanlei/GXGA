<@admin.page title="默认页面">
<@admin.script>
	mini.parse();
	
	mini.showMessageBox({
		title: "提示",
		message: "${message}",
		buttons: ["ok"],
		iconCls: "mini-messagebox-info",
		callback: function(action){
			if(action=="ok"||action=="close"){
				closeWindow();
			}
		}
	});
	
	function closeWindow(){
    	window.CloseOwnerWindow();
    }
</@admin.script>
</@admin.page>