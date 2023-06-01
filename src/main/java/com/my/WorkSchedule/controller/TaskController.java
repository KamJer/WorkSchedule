package com.my.WorkSchedule.controller;

import com.my.WorkSchedule.entity.Contact;
import com.my.WorkSchedule.entity.Task;
import com.my.WorkSchedule.service.ContactService;
import com.my.WorkSchedule.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService, ContactService contactService) {
        this.taskService = taskService;
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.info("GET /tasks - Getting all tasks");
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        logger.info("GET /tasks/{} - Getting task by ID: {}", id);
        return taskService.getTaskById(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> {
                    logger.warn("GET /tasks/{} - Task not found for ID: {}", id, id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        logger.info("POST /tasks - Adding new task", task.toString());
        Task savedTask = taskService.addTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        logger.info("PUT /tasks/{} - Updating task with ID: {}", task.getId());
        Task updatedTask = taskService.updateTask(task);
//        checking if passed task is a new record or old one for updating (if its new it does not yet have id)
//        if it is a new task create new one and return proper code
        if (task.getId() != 0) {
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("DELETE /tasks/{} - Deleting task with ID: {}", id);
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Task>> getTasksByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.info("GET /tasks/date/{} - Getting tasks with date {}", date);
        List<Task> tasks = taskService.getTasksBetweenDates(date, date.plusDays(1));
        if (tasks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/contact/{contactId}")
    public ResponseEntity<Task> addContactToTask(@PathVariable("taskId") long taskId, @PathVariable("contactId") long contactId) {
        logger.info("PUT /tasks//{}/contact/{} - Updating task (adding contact to task) with task id: {} and contact id: {}", taskId, contactId, taskId, contactId);
        Optional<Task> taskToUpdate = taskService.getTaskById(taskId);
        Optional<Contact> contactToAdd = contactService.getContactById(contactId);
//        checking if passed ids are valid, if not respond with proper http code
        if (taskToUpdate.isEmpty() || contactToAdd.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskToUpdate.get().getContact().add(contactToAdd.get());
        Task updatedTask = taskService.updateTask(taskToUpdate.get());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }


}
