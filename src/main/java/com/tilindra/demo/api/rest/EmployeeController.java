package com.tilindra.demo.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tilindra.demo.domain.Employee;
import com.tilindra.demo.exception.DataFormatException;
import com.tilindra.demo.messaging.Producer;
import com.tilindra.demo.service.EmployeeService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.service.ResponseMessage;

@RestController
@RequestMapping(value = "/demo")
public class EmployeeController extends AbstractRestHandler {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private Producer producer;

	@ApiOperation(notes = "Create an Employee in json format", value = "createEmployee", response = ResponseMessage.class)
	@RequestMapping(value = "/createEmployee", method = RequestMethod.POST, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createEmployee(@RequestBody Employee employee, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("Creating an Employee {}"+ employee);
		Employee createdEmployee = this.employeeService.createEmployee(employee);
		producer.sendToRabbitmq(createdEmployee, "Employee has been published to RabbitMQ");
		response.setHeader("Location", request.getRequestURL().append("/").append(createdEmployee.getId()).toString());
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Employee getEmployee(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Employee employee = this.employeeService.getEmployee(id);
		checkResourceFound(employee);
		return employee;
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.employeeService.getEmployee(id));
		if (id != employee.getId())
			throw new DataFormatException("ID doesn't match!");
		this.employeeService.updateEmployee(employee);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.employeeService.getEmployee(id));
		this.employeeService.deleteEmployee(id);
	}
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public void getAllEmployees(HttpServletRequest request, HttpServletResponse response) {
		this.employeeService.getAllEmployees();
	}

}
