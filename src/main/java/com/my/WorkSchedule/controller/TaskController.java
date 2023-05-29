package com.my.WorkSchedule.controller;

import com.my.WorkSchedule.entity.Task;
import com.my.WorkSchedule.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.info("GET /tasks - Getting all tasks");
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        logger.info("GET /tasks/{} - Getting task by ID: {}", id);
        return taskService.getTaskById(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> {
                    logger.warn("GET /tasks/{} - Task not found for ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        logger.info("POST /tasks - Adding new task", task.toString());
        Task savedTask = taskService.addTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        logger.info("PUT /tasks/{} - Updating task with ID: {}", task.getId());
        Task updatedTask = taskService.updateTask(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("DELETE /tasks/{} - Deleting task with ID: {}", id);
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
