package com.hibret.soap;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {
	Employee findById(long id);
    List<Employee> findByFirstNameAndLastName(String fname, String lname);
    List<Employee> findByFirstNameContainingIgnoreCase(String name);
} 