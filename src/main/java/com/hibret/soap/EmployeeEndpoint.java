package com.hibret.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import soapwebservice.hibret.com.soap_web_service.AddEmployeeRequest;
import soapwebservice.hibret.com.soap_web_service.AddEmployeeResponse;
import soapwebservice.hibret.com.soap_web_service.DeleteEmployeeRequest;
import soapwebservice.hibret.com.soap_web_service.DeleteEmployeeResponse;
import soapwebservice.hibret.com.soap_web_service.EmployeeInfo;
import soapwebservice.hibret.com.soap_web_service.GetAllEmployeesResponse;
import soapwebservice.hibret.com.soap_web_service.GetEmployeeByFirstNameRequest;
import soapwebservice.hibret.com.soap_web_service.GetEmployeeByFirstNameResponse;
import soapwebservice.hibret.com.soap_web_service.GetEmployeeByIdRequest;
import soapwebservice.hibret.com.soap_web_service.GetEmployeeByIdResponse;
import soapwebservice.hibret.com.soap_web_service.ServiceStatus;
import soapwebservice.hibret.com.soap_web_service.UpdateEmployeeRequest;
import soapwebservice.hibret.com.soap_web_service.UpdateEmployeeResponse;

@Endpoint
public class EmployeeEndpoint {
	private static final String NAMESPACE_URI = "com.hibret.SOAPWebService/soap-web-service";
	@Autowired
	private EmployeeService EmployeeService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeByIdRequest")
	@ResponsePayload
	public GetEmployeeByIdResponse getEmployee(@RequestPayload GetEmployeeByIdRequest request) {
		GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
		EmployeeInfo EmployeeInfo = new EmployeeInfo();
		BeanUtils.copyProperties(EmployeeService.getEmployeeById(request.getId()), EmployeeInfo);
		response.setEmployeeInfo(EmployeeInfo);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeByFirstNameRequest")
	@ResponsePayload
	public GetEmployeeByFirstNameResponse getEmployeeByName(@RequestPayload GetEmployeeByFirstNameRequest request) {
		GetEmployeeByFirstNameResponse response = new GetEmployeeByFirstNameResponse();
		List<EmployeeInfo> EmployeeInfoList = new ArrayList<>();
		List<Employee> EmployeeList = EmployeeService.getEmployeeByFirstName(request.getFirstName());
		for (int i = 0; i < EmployeeList.size(); i++) {
		     EmployeeInfo ob = new EmployeeInfo();
		     BeanUtils.copyProperties(EmployeeList.get(i), ob);
		     EmployeeInfoList.add(ob);    
		}
		response.getEmployeeInfo().addAll(EmployeeInfoList);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmployeesRequest")
	@ResponsePayload
	public GetAllEmployeesResponse getAllEmployees() {
		GetAllEmployeesResponse response = new GetAllEmployeesResponse();
		List<EmployeeInfo> EmployeeInfoList = new ArrayList<>();
		List<Employee> EmployeeList = EmployeeService.getAllEmployees();
		for (int i = 0; i < EmployeeList.size(); i++) {
		     EmployeeInfo ob = new EmployeeInfo();
		     BeanUtils.copyProperties(EmployeeList.get(i), ob);
		     EmployeeInfoList.add(ob);    
		}
		response.getEmployeeInfo().addAll(EmployeeInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
	@ResponsePayload
	public AddEmployeeResponse addEmployee(@RequestPayload AddEmployeeRequest request) {
		AddEmployeeResponse response = new AddEmployeeResponse();		
    	        ServiceStatus serviceStatus = new ServiceStatus();		
		Employee Employee = new Employee();
		Employee.setFirstName(request.getFirstName());
		Employee.setLastName(request.getLastName());
		Employee.setEmailId(request.getEmailId());
                Employee employee = EmployeeService.saveEmployee(Employee);
		   EmployeeInfo EmployeeInfo = new EmployeeInfo();
	           BeanUtils.copyProperties(Employee, EmployeeInfo);
		   response.setEmployeeInfo(EmployeeInfo);
        	   serviceStatus.setStatusCode("SUCCESS");
        	   serviceStatus.setMessage("Employee Added Successfully");
        	   response.setServiceStatus(serviceStatus);
                
                return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmployeeRequest")
	@ResponsePayload
	public UpdateEmployeeResponse updateEmployee(@RequestPayload UpdateEmployeeRequest request) {
		Employee Employee = new Employee();
		BeanUtils.copyProperties(request.getEmployeeInfo(), Employee);
		EmployeeService.updateEmployee(Employee);
    	        ServiceStatus serviceStatus = new ServiceStatus();
    	        serviceStatus.setStatusCode("SUCCESS");
    	        serviceStatus.setMessage("Employee Updated Successfully");
    	        UpdateEmployeeResponse response = new UpdateEmployeeResponse();
    	        response.setServiceStatus(serviceStatus);
    	        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
	@ResponsePayload
	public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
		Employee Employee = EmployeeService.getEmployeeById(request.getId());
    	        ServiceStatus serviceStatus = new ServiceStatus();
		    EmployeeService.deleteEmployee(request.getId());
	    	    serviceStatus.setStatusCode("SUCCESS");
	    	    serviceStatus.setMessage("Employee Deleted Successfully");
		
    	        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
    	        response.setServiceStatus(serviceStatus);
		return response;
	}	
} 