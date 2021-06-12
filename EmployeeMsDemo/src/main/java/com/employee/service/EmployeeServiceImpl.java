package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired(required = true)
	private EmployeeRepository repo;

	@Override
	public Long createEmployee(final Employee e) throws ServiceException {
		final Employee save = repo.save(e);
		return save.getId();
	}

	@Override
	public Employee updateEmployee(final Employee e) throws ServiceException {
		final Optional<Employee> optEmp = repo.findById(e.getId());
		if (optEmp.isPresent()) {
			return repo.save(e);
		} else {
			throw new ServiceException("Employee not found where emp id " + e.getId());
		}

	}

	@Override
	public List<Employee> findEmployee(final Employee e) {
		return repo.findEmployees(e.getFirstName(), e.getLastName(), e.getAge(), e.getContactNumber());
	}

	@Override
	public void deleteEmployee(Long id) {
		Optional<Employee> employee = repo.findById(id);
		if (employee.isPresent()) {
			repo.delete(employee.get());
		} else {
			log.error("Employee does not found for the given id " + id);
			throw new ServiceException("Employee does not found for the given id " + id);
		}

	}

}
