package com.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.ApiError;
import com.employee.model.CreateEmployeeRequest;
import com.employee.model.CreateEmployeeResponse;
import com.employee.model.DeleteEmployeeRequest;
import com.employee.model.DeleteEmployeeResponse;
import com.employee.model.Employee;
import com.employee.model.GetEmployeeRequest;
import com.employee.model.GetEmployeeResponse;
import com.employee.model.State;
import com.employee.model.UpdateEmployeeRequest;
import com.employee.model.UpdateEmployeeResponse;
import com.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest")
@Slf4j
public class EmployeeApiImpl implements EmployeeApi {

	@Autowired
	private EmployeeService service;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<CreateEmployeeResponse> addEmployeePost(@Valid final CreateEmployeeRequest req) {
		log.info("Inside addEmployeePost where request is " + req);
		
		try {
			
			Long id = service.createEmployee(Employee.builder().firstName(req.getFirstName()).lastName(req.getLastName())
					.age(req.getAge()).contactNumber(req.getContactNumber()).state(State.ADDED).build());
			final CreateEmployeeResponse response = new CreateEmployeeResponse();
			response.setId(id);
			response.setState(State.ADDED);
			response.setMessage(req.getFirstName() + " " + req.getLastName() + " created");
			return ResponseEntity.ok(response);
		} catch (final ServiceException ex) {
			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
					"Unable to create employee");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<List<GetEmployeeResponse>> getEmployeesGet(@Valid final GetEmployeeRequest req) {
		List<GetEmployeeResponse> response = new ArrayList<>();

		log.info("Inside getEmployeesGet where request is " + req);
		try {
			final List<Employee> employeeList = service.findEmployee(Employee.builder().firstName(req.getFirstName())
					.lastName(req.getLastName()).age(req.getAge()).contactNumber(req.getContactNumber()).state(req.getState()).build());
			if (employeeList != null) {
				response = employeeList.parallelStream()
						.map(e -> new GetEmployeeResponse().age(e.getAge()).contactNumber(e.getContactNumber())
								.firstName(e.getFirstName()).lastName(e.getLastName()).id(e.getId()).state(e.getState()))
						.collect(Collectors.toList());
			}
			return ResponseEntity.ok(response);
		} catch (final ServiceException ex) {
			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
					"Unable to getEmployees ");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		} catch (Exception e) {
			final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(),
					"Unable to getEmployees ");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<UpdateEmployeeResponse> updateEmployeePut(
			@Valid UpdateEmployeeRequest req) {
		
		log.info("Inside updateEmployeePut where request is " + req);
		try {
			Employee e = service.updateEmployee(Employee.builder().firstName(req.getFirstName())
					.lastName(req.getLastName()).age(req.getAge()).contactNumber(req.getContactNumber()).id(req.getId()).state(req.getState()).build());
			UpdateEmployeeResponse response = new UpdateEmployeeResponse().age(e.getAge()).contactNumber(e.getContactNumber()).firstName(e.getFirstName())
					.lastName(e.getLastName()).state(e.getState());
			return ResponseEntity.ok(response);
		} catch (final ServiceException ex) {
			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
					"Unable to update employee ");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		} catch (Exception e) {
			final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(),
					"Unable to update employee ");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		}
	}

	@Override
	public ResponseEntity<DeleteEmployeeResponse> deleteEmployeeDelete(
			@Valid DeleteEmployeeRequest req) {
		log.info("Inside deleteEmployeeDelete where request is " + req);
		try {
			service.deleteEmployee(req.getId());
			DeleteEmployeeResponse response = new DeleteEmployeeResponse().message("Employee deleted where id "+req.getId());
			return ResponseEntity.ok(response);
		} catch (final ServiceException ex) {
			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
					"Unable to deleteEmployee ");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		} catch (Exception e) {
			final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(),
					"Unable to deleteEmployee");
			return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
		}
	}
	
}
