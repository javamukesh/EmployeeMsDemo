package com.employee.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.employee.model.Employee;


public interface EmployeeService {
	public Long createEmployee(Employee e) throws ServiceException;
	public Employee updateEmployee(Employee e) throws ServiceException;
	public List<Employee> findEmployee(Employee e);
	public void deleteEmployee(Long id);
}
