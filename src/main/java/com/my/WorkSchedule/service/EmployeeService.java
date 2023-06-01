package com.my.WorkSchedule.service;

import com.my.WorkSchedule.entity.Employee;
import com.my.WorkSchedule.repository.EmployeeRepository;
import com.my.WorkSchedule.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees from the employee repository.
     *
     * @return List of employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by their ID from the employee repository.
     *
     * @param id ID of the employee
     * @return Optional containing the employee if found, empty otherwise
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Adds a new employee to the employee repository.
     *
     * @param employee Employee to be added
     * @return The added employee
     */
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Updates an existing employee in the employee repository.
     *
     * @param employee Employee to be updated
     * @return The updated employee
     */
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Deletes an employee by their ID from the employee repository.
     * Also deletes any associated task employees.
     *
     * @param id ID of the employee to be deleted
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteTaskEmployeeByEmployeeId(id);
        employeeRepository.deleteById(id);
    }
}
