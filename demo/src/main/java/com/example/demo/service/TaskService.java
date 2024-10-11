package com.example.demo.service;
import com.example.demo.Repository.TaskRepository;
import com.example.demo.Repository.TaskStateRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.TaskDTO;
import com.example.demo.model.Task;
import com.example.demo.model.TaskState;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskCategoryService taskCategoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskStateRepository taskState;

    @Autowired
    TaskStateService taskStateService;



    public Task getTaskById(Long id, Long userId) {
        return taskRepository.findTask(id, userId);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public List<Task> findByClient(Long user) {
        taskStateService.updateTaskState();
        return taskRepository.findByClient(user);
    }

    public List<Task> findByCategory(Long id, Long clientID) {
        return taskRepository.findByCategory(id, (long) 1, clientID);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(TaskDTO task, Long id) {
        taskStateService.updateTaskState();
        Task task1 = fromDTO(task);
        updateData(task1, findTaskById(id));
        return taskRepository.save(task1);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found"));
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public Task fromDTO(TaskDTO taskDTO) {
        TaskState taskState = new TaskState();
        taskState.setId(1L);
        return new Task(
                taskDTO.getId(),
                userRepository.findByID(taskDTO.getUserID()),
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getPriority(),
                taskState,
                taskCategoryService.repository.findByID(taskDTO.getCategory()),
                taskDTO.getDonedate(),
                new Date(),
                new Date()
        );
    }


    private void updateData(Task newObj, Task obj) {
        newObj.setTitle((obj.getTitle() == null) ? newObj.getTitle() : obj.getTitle());
        newObj.setDescription((obj.getDescription() == null) ? newObj.getDescription() : obj.getDescription());
        newObj.setPriority((obj.getPriority() == 0) ? newObj.getPriority() : obj.getPriority());
        newObj.setClient((obj.getClient() == null) ? newObj.getClient() : obj.getClient());
        newObj.setState((obj.getState() == null) ? newObj.getState() : obj.getState());
        newObj.setCategory((obj.getCategory() == null) ? newObj.getCategory() : obj.getCategory());
        newObj.setDonedate((obj.getDonedate() == null) ? newObj.getDonedate() : obj.getDonedate());
        newObj.setCreatedAt((obj.getCreatedAt() == null) ? newObj.getCreatedAt() : obj.getCreatedAt());
        newObj.setUpdatedAt(new Date());
    }

}

