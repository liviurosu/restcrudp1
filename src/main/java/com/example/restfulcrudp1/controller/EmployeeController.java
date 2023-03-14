package com.example.restfulcrudp1.controller;

import com.example.restfulcrudp1.exception.ResourceNotFoundException;
import com.example.restfulcrudp1.model.Employee;
import com.example.restfulcrudp1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //will create a get all employee api
    @GetMapping("/employees")
    public List<Employee> getAllEmmloyees(){
        return employeeRepository.findAll();
    }
    //create employee
    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //get employeee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value ="id") long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employeee not found for this id:" + employeeId));
        return ResponseEntity.ok().body(employee);

    }
    //udate employeee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value ="id") long employeeId, @RequestBody Employee employeeDetails ) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employeee not found for this id:" + employeeId));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    //delete employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value ="id") long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employeee not found for this id:" + employeeId));
        employeeRepository.deleteById(employeeId);
        return ResponseEntity.ok().build();
    }

}
