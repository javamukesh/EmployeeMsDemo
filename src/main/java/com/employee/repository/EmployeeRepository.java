package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.model.State;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT E FROM EMPLOYEE E WHERE " + " (E.firstName = :FIRSTNAME OR :FIRSTNAME IS NULL) "
			+ "AND (E.lastName = :LASTNAME OR :LASTNAME IS NULL) "
			+ "AND (E.contactNumber = :CONTACTNUMBER OR :CONTACTNUMBER IS NULL) "
			+ "AND (E.state = :state OR :state IS NULL) "
			+ "AND (E.age = :AGE OR :AGE IS NULL) ")
	public List<Employee> findEmployees(@Param("FIRSTNAME") String firstName, @Param("LASTNAME") String lastName,
			@Param("AGE") Integer age, @Param("CONTACTNUMBER") Long contactNumber,@Param("state") State state);

}
