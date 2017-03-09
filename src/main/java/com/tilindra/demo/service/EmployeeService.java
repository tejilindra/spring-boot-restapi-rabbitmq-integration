package com.tilindra.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tilindra.demo.dao.jpa.EmployeeRepository;
import com.tilindra.demo.domain.Employee;

@Service
public class EmployeeService {

	 private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

	    @Autowired
	    private EmployeeRepository employeeRepository;

	    public EmployeeService() {
	    }

	    public Employee createEmployee(Employee employee) {
	        log.info("creating Employee: {}" + employee);
	    	return employeeRepository.save(employee);
	    }

	    public Employee getEmployee(long id) {
	    	log.info("get Employee for id {}" + id);
	        return employeeRepository.findOne(id);
	    }

	    public void updateEmployee(Employee employee) {
	    	log.info("Update Employee: {}" + employee);
	    	employeeRepository.save(employee);
	    }

	    public void deleteEmployee(Long id) {
	    	log.info("Delete Employee id {}" + id);
	    	employeeRepository.delete(id);
	    }
	    
	    public Iterable<Employee> getAllEmployees() {
	    	log.info("Get All Employees");
	        return employeeRepository.findAll();
	    }

}
