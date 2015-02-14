package cn.gov.dl.ga.gxga.process.admin.employee;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmployeeService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class EmployeeUpdateProcess extends Process {
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String departmentId = params.get("departmentId");
		String employeeOrder = params.get("employeeOrder");
		String employeeName = params.get("employeeName");
		String employeeGender = params.get("employeeGender");
		String employeeIdNo = params.get("employeeIdNo");
		String employeePoliceNo = params.get("employeePoliceNo");
		String employeeBirth = params.get("employeeBirth");
		String employeePolitical = params.get("employeePolitical");// 政治面貌
		String employeeNationality = params.get("employeeNationality");// 民族
		String employeeIdentity = params.get("employeeIdentity");// 人员身份
		String employeeAddress = params.get("employeeAddress");
		String employeeFamily = params.get("employeeFamily");

		String employeeEducation = params.get("employeeEducation");
		String employeeSchool = params.get("employeeSchool");
		String employeeDegree = params.get("employeeDegree");
		String employeeMajor = params.get("employeeMajor");
		String employeeHighEducation = params.get("employeeHighEducation");
		String employeeHighSchool = params.get("employeeHighSchool");
		String employeeHighDegree = params.get("employeeHighDegree");
		String employeeHighMajor = params.get("employeeHighMajor");
		String employeeJobTitle = params.get("employeeJobTitle");
		String employeeOtherCertificate = params
				.get("employeeOtherCertificate");

		String employeePosition = params.get("employeePosition");
		String employeePositionLevel = params.get("employeePositionLevel");
		String employeeStartTime = params.get("employeeStartTime");
		String employeeJob = params.get("employeeJob");
		String employeeWorkStartTime = params.get("employeeWorkStartTime");
		String employeePoliceStartTime = params.get("employeePoliceStartTime");
		String employeePoliceSpecialty = params.get("employeePoliceSpecialty");
		String employeeOtherSpecialty = params.get("employeeOtherSpecialty");
		String employeeRewards = params.get("employeeRewards");

		String employeeId = params.get("employeeId");

		Object[] parameters = new Object[] { departmentId, employeeOrder,
				employeeName, employeeGender, employeeIdNo, employeePoliceNo,
				employeeBirth, employeePolitical, employeeNationality,
				employeeIdentity, employeeAddress, employeeFamily,
				employeeEducation, employeeSchool, employeeDegree,
				employeeMajor, employeeHighEducation, employeeHighSchool,
				employeeHighDegree, employeeHighMajor, employeeJobTitle,
				employeeOtherCertificate, employeePosition,
				employeePositionLevel, employeeStartTime, employeeJob,
				employeeWorkStartTime, employeePoliceStartTime,
				employeePoliceSpecialty, employeeOtherSpecialty,
				employeeRewards, employeeId };

		employeeService.updateEmployee(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}
}
