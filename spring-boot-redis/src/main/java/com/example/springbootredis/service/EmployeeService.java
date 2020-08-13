package com.example.springbootredis.service;

import com.example.springbootredis.model.Employee;

public interface EmployeeService {

	public Employee save(Employee emp);

	public Employee findById(Long id);
}
