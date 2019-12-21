package com.example.demowebservice1jlg6;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/empservice")
public class FirstRESTService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping(path = "/hello")
	public String hello() {
		return "hello shubham";
	}

	@GetMapping(path = "/emp")
	public Employee getEmp() {
		return new Employee("mphit", "malhotra");
	}

	@GetMapping(path = "/emps")
	public List<Employee> getAllEmps() {
		return employeeRepository.findAll();
	}

	@PostMapping(path = "/createemp")
	public void createEmp(@RequestBody Employee employee) {
		employeeRepository.save(employee);
	}

	@GetMapping(path = "/empss/{id}")
	public Employee getEmpById(@PathVariable("id") Long id) {
		if (employeeRepository.findById(id).isPresent()) {
			return employeeRepository.findById(id).get();
		} else {
			throw new UserNotFoundException("No user found");
		}

	}

	@GetMapping(path = "/empsss")
	public Employee getEmpByIdWithReqParam(@RequestParam("id") Long id) {
		return employeeRepository.findById(id).get();
	}

	@PostMapping(path = "/v2/createemp")
	public ResponseEntity<Object> createEmpV2(@RequestBody Employee employee) {

		Employee employee2 = employeeRepository.save(employee);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("{id}").buildAndExpand(employee2.getId())
				.toUri();

		return ResponseEntity.created(uri).build();

	}
}
