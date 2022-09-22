package com.hibret.soap;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> saveEmployees(List<Employee> employees);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    List<Employee> getEmployeeByFirstName(String name);
    Employee updateEmployee(Employee updatedEmployee);
    void deleteEmployee(long id);
	
}