package com.my.WorkSchedule;

import com.my.WorkSchedule.entity.Contact;
import com.my.WorkSchedule.entity.Employee;
import com.my.WorkSchedule.entity.Task;
import com.my.WorkSchedule.repository.ContactRepository;
import com.my.WorkSchedule.repository.EmployeeRepository;
import com.my.WorkSchedule.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testAddContactToTask() {
        Contact contact = new Contact();
        contact.setPhoneNumber("111111111");
        contact.setCompanyName("Google");
        contact.setContactName("Twardostoj Dziąsło");
        contactRepository.save(contact);

        Task task = new Task();
        task.setDescription("Testowe zadanie 3");
        task.setTime(LocalDateTime.now());
        task.setCarId(1);
        taskRepository.save(task);

        task.getContact().add(contact);
        entityManager.flush();

        Task savedTask = taskRepository.findById(task.getId()).orElse(null);

        assertThat(contactRepository.findById(contact.getId()).equals(contact));
        assertThat(savedTask.getContact()).contains(contact);
    }

    @Test
    public void testAddEmployeeToTask() {
        Employee employee = new Employee();
        employee.setName("Jan Kowalski");
        employeeRepository.save(employee);

        Task task = new Task();
        task.setDescription("Testowe zadanie 2");
        task.setTime(LocalDateTime.now());
        task.setCarId(1);
        taskRepository.save(task);

        task.getEmployees().add(employee);
        entityManager.flush();

        Task savedTask = taskRepository.findById(task.getId()).orElse(null);

        assertThat(employeeRepository.findById(employee.getId()).equals(employee));
        assertThat(savedTask.getEmployees()).contains(employee);
    }
}

