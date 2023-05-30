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
        // Tworzenie obiektu kontaktu
        Contact contact = new Contact();
        contact.setPhoneNumber("111111111");
        contact.setCompanyName("Google");
        contact.setContactName("Twardostoj Dziąsło");
        contactRepository.save(contact);

        // Tworzenie obiektu zadania
        Task task = new Task();
        task.setDescription("Testowe zadanie");
        task.setTime(LocalDateTime.now());
        task.setCarId(1);
        taskRepository.save(task);

        // Dodawanie kontaktu do zadania
        task.getContact().add(contact);
        entityManager.flush();

        // Pobieranie zadania z bazy danych
        Task savedTask = taskRepository.findById(task.getId()).orElse(null);

        // Sprawdzanie czy kontakt znajduje się w tabeli CONTACT_TASK
        boolean isContactInContactTask = entityManager.getEntityManager()
                .createQuery("SELECT ct FROM ContactTask ct WHERE ct.contact = :contact")
                .setParameter("contact", contact)
                .getResultList().size() > 0;
        assertThat(isContactInContactTask).isTrue();

        // Sprawdzanie czy kontakt znajduje się na liście kontaktów w obiekcie zadania
        assertThat(savedTask.getContact()).contains(contact);
    }

    @Test
    public void testAddEmployeeToTask() {
        // Tworzenie obiektu pracownika
        Employee employee = new Employee();
        employee.setName("Jan Nowak");
        employeeRepository.save(employee);

        // Tworzenie obiektu zadania
        Task task = new Task();
        task.setDescription("Testowe zadanie");
        task.setTime(LocalDateTime.now());
        task.setCarId(1);
        taskRepository.save(task);

        // Dodawanie pracownika do zadania
        task.getEmployees().add(employee);
        entityManager.flush();

        // Pobieranie zadania z bazy danych
        Task savedTask = taskRepository.findById(task.getId()).orElse(null);

        // Sprawdzanie czy pracownik znajduje się w tabeli TASK_EMPLOYEE
        boolean isEmployeeInTaskEmployee = entityManager.getEntityManager()
                .createQuery("SELECT te FROM TaskEmployee te WHERE te.employee = :employee")
                .setParameter("employee", employee)
                .getResultList().size() > 0;
        assertThat(isEmployeeInTaskEmployee).isTrue();

        // Sprawdzanie czy pracownik znajduje się na liście pracowników w obiekcie zadania
        assertThat(savedTask.getEmployees()).contains(employee);
    }
}

