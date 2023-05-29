package com.my.WorkSchedule;

import com.my.WorkSchedule.controller.TaskController;
import com.my.WorkSchedule.entity.Task;
import com.my.WorkSchedule.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskControllerTest {
	@Mock
	private TaskService taskService;

	private TaskController taskController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		taskController = new TaskController(taskService);
	}

	@Test
	void getAllTasks_ShouldReturnListOfTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task());
		tasks.add(new Task());

		when(taskService.getAllTasks()).thenReturn(tasks);

		ResponseEntity<List<Task>> response = taskController.getAllTasks();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(tasks, response.getBody());
		verify(taskService, times(1)).getAllTasks();
	}

	@Test
	void getTaskById_ExistingId_ShouldReturnTask() {
		long taskId = 1L;
		Task task = new Task();
		task.setId(taskId);

		when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

		ResponseEntity<Task> response = taskController.getTaskById(taskId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(task, response.getBody());
		verify(taskService, times(1)).getTaskById(taskId);
	}

	@Test
	void getTaskById_NonExistingId_ShouldReturnNotFound() {
		long taskId = 1L;

		when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

		ResponseEntity<Task> response = taskController.getTaskById(taskId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
		verify(taskService, times(1)).getTaskById(taskId);
	}

	@Test
	void addTask_ValidTask_ShouldReturnCreated() {
		Task task = new Task();

		when(taskService.addTask(any(Task.class))).thenReturn(task);

		ResponseEntity<Task> response = taskController.addTask(task);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(task, response.getBody());
		verify(taskService, times(1)).addTask(any(Task.class));
	}

	@Test
	void updateTask_ValidTask_ShouldReturnUpdatedTask() {
		long taskId = 1L;
		Task task = new Task();
		task.setId(taskId);

		when(taskService.updateTask(any(Task.class))).thenReturn(task);

		ResponseEntity<Task> response = taskController.updateTask(task);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(task, response.getBody());
		verify(taskService, times(1)).updateTask(any(Task.class));
	}

	@Test
	void deleteTask_ExistingId_ShouldReturnNoContent() {
		long taskId = 1L;

		ResponseEntity<Void> response = taskController.deleteTask(taskId);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(taskService, times(1)).deleteTask(taskId);
	}
}
