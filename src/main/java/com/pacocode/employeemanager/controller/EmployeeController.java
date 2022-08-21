package com.pacocode.employeemanager.controller;

import com.pacocode.employeemanager.exception.UserNotFoundException;
import com.pacocode.employeemanager.model.Employee;
import com.pacocode.employeemanager.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees () {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.findEmployeeById(id));
        if(!employee.isPresent()) {
            //throw new UserNotFoundException("Employee was not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employee.get());
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee (@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee (@PathVariable("id") Long id,
            @RequestBody Employee employee) {
        Optional<Employee> optional = Optional.ofNullable(employeeService.findEmployeeById(id));

        if(!optional.isPresent()) {
            throw new UserNotFoundException("Employee not Found");
        }

        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee (@PathVariable("id") Long id) {
        Optional<Employee> optional = Optional.ofNullable(employeeService.findEmployeeById(id));

        if(!optional.isPresent()) {
            throw new UserNotFoundException("Employee Not Found");
        }

        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
