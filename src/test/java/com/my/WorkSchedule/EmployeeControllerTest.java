package com.my.WorkSchedule;
import com.my.WorkSchedule.controller.EmployeeController;
import com.my.WorkSchedule.entity.Employee;
import com.my.WorkSchedule.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());

        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById_ExistingId_ShouldReturnEmployee() {
        long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void getEmployeeById_NonExistingId_ShouldReturnNotFound() {
        long employeeId = 1L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void addEmployee_ValidEmployee_ShouldReturnCreated() {
        Employee employee = new Employee();

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.addEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void updateEmployee_ValidEmployee_ShouldReturnUpdatedEmployee() {
        long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    void deleteEmployee_ExistingId_ShouldReturnNoContent() {
        long employeeId = 1L;

        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}

