package com.employee.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.employee.controller.EmployeeApi;
import com.employee.model.CreateEmployeeRequest;
import com.employee.model.CreateEmployeeResponse;
import com.employee.model.DeleteEmployeeRequest;
import com.employee.model.GetEmployeeRequest;
import com.employee.model.GetEmployeeResponse;
import com.employee.model.UpdateEmployeeRequest;
import com.employee.model.UpdateEmployeeResponse;

@SpringBootTest
public class ApiTest {

	@Autowired
	private EmployeeApi empApi;

	@Test
	@DisplayName("Create Emp Test ")
	void createEmpTest() {
		CreateEmployeeRequest req = new CreateEmployeeRequest().firstName("Mukesh").lastName("Bagla").age(40)
				.contactNumber(123456789l);
		ResponseEntity<CreateEmployeeResponse> addEmployeePost = empApi.addEmployeePost(req);
		assertTrue(addEmployeePost != null);
		assertTrue(addEmployeePost.getBody().getMessage()
				.equals(req.getFirstName() + " " + req.getLastName() + " created"));
		empApi.deleteEmployeeDelete(new DeleteEmployeeRequest().id(addEmployeePost.getBody().getId()));
	}

	@Test
	@DisplayName("Update Emp Test ")
	void updateEmpTest() {
		CreateEmployeeRequest req = new CreateEmployeeRequest().firstName("Mukesh").lastName("Bagla").age(40)
				.contactNumber(123456789l);
		ResponseEntity<CreateEmployeeResponse> addEmployeePost = empApi.addEmployeePost(req);
		assertTrue(addEmployeePost != null);
		assertTrue(addEmployeePost.getBody().getMessage()
				.equals(req.getFirstName() + " " + req.getLastName() + " created"));
		UpdateEmployeeRequest uer = new UpdateEmployeeRequest().age(18).contactNumber(454545l)
				.id(addEmployeePost.getBody().getId());
		ResponseEntity<UpdateEmployeeResponse> updateEmployeePut = empApi.updateEmployeePut(uer);
		assertTrue(updateEmployeePut.getBody().getContactNumber().equals(uer.getContactNumber()));
		assertTrue(updateEmployeePut.getBody().getAge().equals(uer.getAge()));
		empApi.deleteEmployeeDelete(new DeleteEmployeeRequest().id(addEmployeePost.getBody().getId()));
	}

	@Test
	@DisplayName("Get Emp Test ")
	void getEmpTest() {
		GetEmployeeRequest request = new GetEmployeeRequest();
		ResponseEntity<List<GetEmployeeResponse>> employeesGet = empApi.getEmployeesGet(request);
		int currentEmpCount = employeesGet.getBody().size();
		CreateEmployeeRequest req = new CreateEmployeeRequest().firstName("Mukesh").lastName("Bagla").age(40)
				.contactNumber(123456789l);
		ResponseEntity<CreateEmployeeResponse> addEmployeePost = empApi.addEmployeePost(req);
		assertTrue(addEmployeePost != null);
		assertTrue(addEmployeePost.getBody().getMessage()
				.equals(req.getFirstName() + " " + req.getLastName() + " created"));

		assertEquals(empApi.getEmployeesGet(request).getBody().size(), currentEmpCount + 1);
	}

}
