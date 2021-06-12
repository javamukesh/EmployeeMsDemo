package com.employee;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@SpringBootApplication
@ComponentScan("com.employee")
@EnableJpaRepositories(basePackages = "com.employee.*")
public class EmployeeApplication {

	@Autowired
	private EmployeeRepository repo;

	public static void main(final String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@PostConstruct
	private void initDb() {
		final List<Employee> initList = new ArrayList<Employee>();
		initList.add(Employee.builder().age(10).firstName("Rahul").lastName("Jain").contactNumber(123456l).build());
		initList.add(Employee.builder().age(10).firstName("Bob").lastName("dosser").contactNumber(234567l).build());
		initList.add(Employee.builder().age(10).firstName("Marco").lastName("flemin").contactNumber(345678l).build());
		initList.add(Employee.builder().age(10).firstName("Rafael").lastName("dot").contactNumber(456789l).build());

		repo.saveAll(initList);
	}

}
