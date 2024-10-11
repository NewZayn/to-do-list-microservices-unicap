package com.example.demo.controller;
import com.example.demo.dto.TaskDTO;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("api/{userId}/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id, @PathVariable Long userId) {
        Task task = taskService.getTaskById(id, userId);
        TaskDTO taskDTO = new TaskDTO(task);
        return ResponseEntity.ok().body(taskDTO);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task, @PathVariable Long userId) {
        task.setUserID(userId);
        Task newtask = taskService.fromDTO(task);
        taskService.saveTask(newtask);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDTO task, @PathVariable Long id, @PathVariable Long userId) {
        task.setUserID(userId);
        Task updatedTask = taskService.updateTask(task, id);
        return ResponseEntity.ok().body(updatedTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTaskRepository(@PathVariable long userId) {
        List<Task> tasks = taskService.findByClient(userId);
        List<TaskDTO> dtoTasks = tasks.stream().map(TaskDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoTasks);
    }


    @PutMapping("done/{id}")
    public ResponseEntity<TaskDTO> completedTask(@RequestBody TaskDTO task, @PathVariable Long userId) {
        task.setUserID(userId);
        Task newTask =  taskService.updateTask(task, userId);
        return ResponseEntity.ok().body(new TaskDTO(newTask));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @PathVariable Long userId) {
        Task task = taskService.getTaskById(id, userId);
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteTask(task);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Task>> getTasksByCategoryAndClient(@PathVariable Long categoryId, @PathVariable Long userId) {
        List<Task> tasks = taskService.findByCategory(categoryId, userId);
        return ResponseEntity.ok().body(tasks);
    }
}
