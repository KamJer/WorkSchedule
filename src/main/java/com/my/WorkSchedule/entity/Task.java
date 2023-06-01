package com.my.WorkSchedule.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TASK_EMPLOYEE",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private List<Employee> employees;

    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime time;
    @Column(name = "CAR_ID")
    private int carId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CONTACT_TASK",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID")
    )
    private List<Contact> contact;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_SUPERVISING_ID")
    private Employee employeeSupervising;

    public Task() {
    }

    public Task(long id, List<Employee> employees, String description, LocalDateTime time, int carId, List<Contact> contact, Employee employeeSupervising) {
        this.id = id;
        this.employees = employees;
        this.description = description;
        this.time = time;
        this.carId = carId;
        this.contact = contact;
        this.employeeSupervising = employeeSupervising;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<>();
        }
        return contact;
    }

    public Employee getEmployeeSupervising() {
        return employeeSupervising;
    }

    public void setEmployeeSupervising(Employee employeeSupervising) {
        this.employeeSupervising = employeeSupervising;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }
}