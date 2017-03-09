package com.tilindra.demo.dao.jpa;

import org.springframework.data.repository.CrudRepository;
import com.tilindra.demo.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	Employee findEmployeeByCity(String city);
}
