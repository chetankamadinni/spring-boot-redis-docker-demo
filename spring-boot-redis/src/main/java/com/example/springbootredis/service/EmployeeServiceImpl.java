package com.example.springbootredis.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.springbootredis.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String EMPLOYEE = "Employee";

	private RedisTemplate<String, Object> redisTemplate;

	private HashOperations<String, Long, Employee> hashOperations;

	@Autowired
	public EmployeeServiceImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	public void init() {
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public Employee save(Employee emp) {
		hashOperations.put(EMPLOYEE, emp.getId(), emp);
		return emp;
	}

	@Override
	public Employee findById(Long id) {
		return (Employee) hashOperations.get(EMPLOYEE, id);
	}

}
