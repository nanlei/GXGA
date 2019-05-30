<@admin.page title="人员管理">
<@admin.conArea title="人力资源>>人员管理>>修改" id="form1">
<input class="mini-hidden" name="employeeId" value="${(employee.employeeId)?default('')}"/>
<fieldset id="fieldset1" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset1');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">基本信息</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<@admin.con id="datacon1">
	<tr>
		<td><label>用户名:</label></td>
		<td>${employee.userName?default('')}</td>
		<td><label>当前IP：</label></td>
		<td>${ftlUtil.getIP()}</td>
	</tr>
	<tr>
		<td><label>姓名:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeName" style="width:130px;" value="${employee.employeeName?default('')}"/></td>
		<td><label>性别:</label></td>
		<td><input class="mini-combobox" required="false" name="employeeGender" style="width:130px;" value="${employee.employeeGender?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=GENDER" showNullItem="true" allowInput="true"/></td>
		<td><label>身份证号:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeIdNo" style="width:130px;" value="${employee.employeeIdNo?default('')}"/></td>
		<td><label>警号:</label></td>
		<td><input class="mini-textbox" required="false" name="employeePoliceNo" style="width:130px;" value="${employee.employeePoliceNo?default('')}"/></td>
	</tr>
	<tr>
		<td><label>生日:</label></td>
		<td><input class="mini-datepicker" required="false" name="employeeBirth" style="width:130px;" value="${employee.employeeBirth?default('1970-01-01')}" format="yyyy-MM-dd" showTime="true"/></td>
		<td><label>政治面貌:</label></td>
		<td><input class="mini-combobox" required="false" name="employeePolitical" style="width:130px;" value="${employee.employeePolitical?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POLITICAL" showNullItem="true" allowInput="true"/></td>
		<td><label>民族:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeNationality" style="width:130px;" value="${employee.employeeNationality?default('')}"/></td>
		<td><label>人员身份:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeIdentity" style="width:130px;" value="${employee.employeeIdentity?default('')}"/></td>
	</tr>
	<tr>
		<td>家庭住址：</td>
		<td colspan="7"><input class="mini-textbox" required="false" name="employeeAddress" style="width:564px;" value="${employee.employeeAddress?default('')}"/></td>
	</tr>
	<tr>
		<td>家庭成员：</td>
		<td colspan="7"><input class="mini-textbox" required="false" name="employeeFamily" style="width:564px;" value="${employee.employeeFamily?default('')}"/></td>	
	</tr>
</@admin.con>
</ul>
</fieldset>
<fieldset id="fieldset2" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset2');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">学历教育</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<@admin.con id="datacon2">
	<tr>
		<td><label>全日制学历:</label></td>
		<td><input class="mini-combobox" required="false" name="employeeEducation" style="width:130px;" value="${employee.employeeEducation?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=EDUCATION" showNullItem="true" allowInput="true"/></td>
		<td><label>毕业院校:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeSchool" style="width:130px;" value="${employee.employeeSchool?default('')}" /></td>
		<td><label>学位:</label></td>
		<td><input class="mini-combobox" required="false" name="employeeDegree" style="width:130px;" value="${employee.employeeDegree?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=DEGREE" showNullItem="true" allowInput="true"/></td>
		<td><label>专业:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeMajor" style="width:130px;" value="${employee.employeeMajor?default('')}"/></td>
	</tr>
	<tr>
		<td><label>最高学历:</label></td>
		<td><input class="mini-combobox" required="false" name="employeeHighEducation" style="width:130px;" value="${employee.employeeHighEducation?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=EDUCATION" showNullItem="true" allowInput="true"/></td>
		<td><label>毕业院校:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeHighSchool" style="width:130px;" value="${employee.employeeHighSchool?default('')}" /></td>
		<td><label>学位:</label></td>
		<td><input class="mini-combobox" required="false" name="employeeHighDegree" style="width:130px;" value="${employee.employeeHighDegree?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=DEGREE" showNullItem="true" allowInput="true"/></td>
		<td><label>专业:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeHighMajor" style="width:130px;" value="${employee.employeeHighMajor?default('')}"/></td>
	</tr>
	<tr>
		<td><label>警务专业技术职称:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeJobTitle" style="width:130px;" value="${employee.employeeJobTitle?default('')}" /></td>
		<td><label>其它职业资格证书:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeOtherCertificate" style="width:130px;" value="${employee.employeeOtherCertificate?default('')}" /></td>
		
	</tr>
</@admin.con>
</ul>
</fieldset>
<fieldset id="fieldset3" style="border:solid 1px #aaa;margin:0;"> 
<legend onclick="FieldsetClick('fieldset3');"><img src="${base}/images/admin/arrow_u.gif" title="隐藏" border="0">工作情况</legend> 
<ul style="padding:6px 0 0 6px;margin:0px;">
<@admin.con id="datacon3">
	<tr>
		<td><label>部门:</label></td>
		<td><input class="mini-treeselect" required="false" name="departmentId" style="width:130px;" value="${employee.departmentId?default('')}" textField="text" valueField="id" parentField="upperId" dataField="data" url="${base}/admin/department.do?command=getupperdepartment" showNullItem="true" allowInput="true" popupWidth="150px" showFolderCheckBox="true" showClose="true" oncloseclick="onCloseClick" showTreeIcon="false"/></td>
		<td><label>职务:</label></td>
		<td><input class="mini-combobox" required="false" name="employeePosition" style="width:130px;" value="${employee.employeePosition?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITION" showNullItem="true" allowInput="true"/></td>
		<td><label>职级:</label></td>
		<td><input class="mini-combobox" required="false" name="employeePositionLevel" style="width:130px;" value="${employee.employeePositionLevel?default('')}" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=POSITIONLEVEL" showNullItem="true" allowInput="true"/></td>
	</tr>
	<tr>
		<td><label>任现职时间:</label></td>
		<td><input class="mini-datepicker" required="false" name="employeeStartTime" style="width:130px;" value="${employee.employeeStartTime?default('1970-01-01')}" format="yyyy-MM-dd" showTime="true" /></td>
		<td><label>工作岗位:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeJob" style="width:130px;" value="${employee.employeeJob?default('')}" /></td>
		<td><label>参加工作时间:</label></td>
		<td><input class="mini-datepicker" required="false" name="employeeWorkStartTime" style="width:130px;" value="${employee.employeeWorkStartTime?default('1970-01-01')}" format="yyyy-MM-dd" showTime="true" /></td>
		<td><label>参加公安工作时间:</label></td>
		<td><input class="mini-datepicker" required="false" name="employeePoliceStartTime" style="width:130px;" value="${employee.employeePoliceStartTime?default('1970-01-01')}" format="yyyy-MM-dd" showTime="true" /></td>
	</tr>
	<tr>
		<td><label>公安业务特长:</label></td>
		<td><input class="mini-textbox" required="false" name="employeePoliceSpecialty" styledth:130px;" value="${employee.employeePoliceSpecialty?default('')}"/></td>
		<td><label>其它特长:</label></td>
		<td><input class="mini-textbox" required="false" name="employeeOtherSpecialty" style="width:130px;" value="${employee.employeeOtherSpecialty?default('')}"/></td>
	</tr>
	<tr>
		<td>奖惩情况：</td>
		<td colspan="7"><input class="mini-textbox" required="false" name="employeeRewards" style="width:564px;" value="${employee.employeeRewards?default('')}"/></td>
	</tr>
	<tr>
		<td>部门管理员：</td>
		<td colspan="7"><input class="mini-combobox" required="true" name="isDeptAdmin" textField="text" valueField="id" dataField="data" url="${base}/admin/const.do?constant=YN" showNullItem="true" allowInput="true" style="width:150px;"/>(非系统权限，业务使用)</td>
	</tr>
</@admin.con>
</ul>
</fieldset>
<br>
<@admin.con id="datacon4">
<@admin.searchArea colspan="8">
<@admin.searchRightArea>	
	<@admin.actBtn name="保存" actionName="/admin/employee.do?command=update" event="Save"/>
</@admin.searchRightArea> 
</@admin.searchArea>
</@admin.con>
</@admin.conArea>
<@admin.conArea title="页面提示" id="form2">
本页面为职位修改页面，请按照上述内容填写修改后的相关信息<br>
职位类别和职位类型可以在常量管理中设置，注意常量类型保持统一即可<br>
填写完成后，点击【保存】按钮即可
</@admin.conArea>
<br>
<@admin.script>
	mini.parse();
	
	var form = new mini.Form("form1");
	
	function Save() {
	
		var data = form.getData();
		
		form.validate();
		
		if (form.isValid() == false) {
			return;
		}
		
		var json = mini.encode(data);

		form.loading("操作中，请稍后......");
		
		$.ajax({
			url: "${base}/admin/employee.do?command=update",
			data: { object: json },
			cache: false,
			success: function (data) {
				try{
					if(data.status=="true"){
						form.unmask();
						mini.showMessageBox({
    	 					title: "提示",    
    						message: "人员修改成功",
    						buttons: ["ok"],    
    						iconCls: "mini-messagebox-info",   
    						callback: function(action){
    							if(action=="ok"||action=="close"){
    								closeWindow();
    							}
   		 					}
    					});
					}
				}catch(e){
					form.unmask();
					mini.alert(data);
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				form.unmask();
				mini.alert(jqXHR.responseText);
			}
		});
	}
	
	function closeWindow(){
    	window.CloseOwnerWindow();
    }
    
    function onCloseClick(e) {
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    }
</@admin.script>
</@admin.page>