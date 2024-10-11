package com.example.demo.service;

import com.example.demo.Repository.TaskRepository;
import com.example.demo.Repository.TaskStateRepository;
import com.example.demo.model.Task;
import com.example.demo.model.TaskState;
import com.example.demo.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TaskStateService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskStateRepository taskStateRepository;

    public void updateTaskState() {
        List<Task> Tasks = taskRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Task task : Tasks) {
            LocalDate validatyDate = convertToLocalDate(task.getDonedate());
            if (validatyDate.isBefore(today) && !task.getState().getTaskStatus().equals(TaskStatus.LATE.name())) {
                TaskState lateStatus = taskStateRepository.findByStatus(TaskStatus.LATE.name());
                if (lateStatus != null) {
                    task.setState(lateStatus);
                }
            }
            taskRepository.save(task);
        }
    }
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
