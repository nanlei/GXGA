<@admin.page title="分类文章管理">
<@admin.conArea title="部门综合>>分类文章管理>>查询" id="form1">
<@admin.con id="datacon1">
<#if loginUser.departmentId==0>
	<tr>
		<td>部门:</td>
		<td><input class="mini-treeselect" id="departmentId" required="false" name="departmentId" value="1" onValueChanged="onDepartmentChanged" style="width:150px;" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="false" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
		<td>分类：</td>
		<td><input class="mini-combobox" id="dcId" required="false" name="dcId" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/departmentcategory.do?command=getcategorybydepartment" showNullItem="true" allowInput="false" /></td>
	</tr>
<#else>
<input class="mini-hidden" name="departmentId" value="${(loginUser.departmentId)?default('')}" />
	<tr>
		<td>分类：</td>
		<td><input class="mini-combobox" id="dcId" required="false" name="dcId" style="width:150px;" textField="text" valueField="id" dataField="data" url="${base}/admin/departmentcategory.do?command=getcategorybydepartment&departmentId=${loginUser.departmentId}" showNullItem="true" allowInput="false" /></td>
	</tr>
</#if>
<@admin.searchArea colspan="7">
<@admin.searchLeftArea>
	<@admin.actBtn name="查询" actionName="/admin/branchdepartment.do?command=search" event="Search" icon="icon-search"/>
</@admin.searchLeftArea>
<@admin.searchRightArea>
	<@admin.actBtn name="新建" actionName="/admin/branchdepartment.do?command=createpre" event="Add" icon="icon-add"/>
	<@admin.actBtn name="修改" actionName="/admin/branchdepartment.do?command=updatedispatcher" event="Edit" icon="icon-edit"/>
	<@admin.actBtn name="删除" actionName="/admin/branchdepartment.do?command=delete" event="Delete" icon="icon-remove"/>
</@admin.searchRightArea>
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.dataArea id="form2">     
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;padding:0;margin:0;" allowResize="true"
    url="${base}/admin/branchdepartment.do?command=search"  idField="articleId" multiSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn" headerAlign="center">序号</div>
        <div field="departmentName" width="120" headerAlign="center" align="center" allowSort="true">部门</div>
        <div field="articleTitle" width="120" headerAlign="center" align="center" allowSort="true">标题</div>
        <div field="articleOrder" width="60" headerAlign="center" align="center" allowSort="true">排序</div>
        <div field="articleStatus" width="60" headerAlign="center" align="center" allowSort="false">状态</div>
        <div field="createByName" width="70" headerAlign="center" align="center" allowSort="false">更新人</div>
        <div field="createByTime" width="100" headerAlign="center" align="center" allowSort="false" dateFormat="yyyy-MM-dd HH:mm:ss">更新时间</div>
        <div field="createByIP" width="70" headerAlign="center" align="center" allowSort="false">IP</div>
        <div field="pageView" width="60" headerAlign="center" align="center" allowSort="false">浏览次数</div>
    </div>
</div>
</@admin.dataArea>
<@admin.script>
    
    mini.parse();

    var grid = mini.get("datagrid1");
    Search();
	grid.sortBy("articleOrder", "asc");
	
	<#if loginUser.departmentId==0>
	function onDepartmentChanged(){
		var departmentId=mini.get("departmentId");
		var id=departmentId.getValue();
		
		var dcId=mini.get("dcId");
		
		dcId.setValue("");
		
		var url="${base}/admin/departmentcategory.do?command=getcategorybydepartment&departmentId="+id;
		
		dcId.setUrl(url);
    	dcId.select(0);
	}
	</#if>
	
    function Search() {
    
    	var form = new mini.Form("#datacon1");
    	
    	var data = form.getData();
        var json = mini.encode(data);
        
        grid.load({ condition: json });
        
        grid.gotoPage(grid.pageIndex,grid.pageSize);
        
    }
    
    function Add() {
        mini.open({
            url: "${base}/admin/branchdepartment.do?command=createpre",
            title: "分类文章新建", width: 900, height: 580,
            ondestroy: function (action) {
                Search();
            }
        });
    }
    
    function Edit() {
        var rows = grid.getSelecteds();
        
       if (rows.length==1) {
            mini.open({
                url: "${base}/admin/branchdepartment.do?command=updatedispatcher&articleId="+rows[0].articleId,
                title: "编辑分类文章:"+rows[0].articleTitle, width: 900, height: 580,
                ondestroy: function (action) {                    
                    Search();
                }
            });
            
        } else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请只选中一条数据",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        }
        
    }
    
    function Delete() {
        var rows = grid.getSelecteds();
        
        if (rows.length > 0) {
    		mini.showMessageBox({
    			title: "提示", 
    			message: "确定删除吗？",
    			buttons: ["ok","no"],
    			iconCls: "mini-messagebox-info", 
    			callback: function(action){
    				if(action=="ok"){
    					doDelete(rows);
    				}
   		 		}
   		 	});
        } else {
        	mini.showMessageBox({
        		title:"提示",
        		message:"请至少选择一条记录",
        		buttons:["ok"],
        		iconCls:"mini-messagebox-info"
        	});
        }
    }
    
    function doDelete(rows){
    	var idsStr = [];
    	
    	for (var i = 0, l = rows.length; i < l; i++) {
    		var r = rows[i];
    		idsStr.push(r.articleId);
    	}
    	
    	var ids = idsStr.join(',');
    	
    	grid.loading("操作中，请稍后......");
    	
    	$.ajax({
    		url: "${base}/admin/branchdepartment.do?command=delete",
    		data: { articleIds: ids },
    		success: function (data) {
    			try{
    				if(data.status=="true"){
    					grid.unmask();
    					Search();
    					mini.alert("文章删除成功");
    				}
    			}catch(e){
    				grid.unmask();
    				mini.alert(data);
    			}
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    			grid.unmask();
    			mini.alert(jqXHR.responseText);
    		}
    	});
    }    
</@admin.script>
</@admin.page>