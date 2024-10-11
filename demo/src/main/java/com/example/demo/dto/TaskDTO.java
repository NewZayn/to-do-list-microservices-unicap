package com.example.demo.dto;

import com.example.demo.model.Task;
import com.example.demo.model.TaskCategory;
import com.example.demo.model.TaskState;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private Long UserID;
    private String title;
    private String description;
    private int priority;
    private Long category;
    private Long state;
    private Date donedate;
    private Date createddate;
    private Date updateddate;

    public TaskDTO(Task task ) {
        this.id = task.getId();
        this.UserID = task.getClient().getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.category = task.getCategory().getId();
        this.state = task.getState().getId();
        this.donedate = task.getDonedate();
        this.createddate = task.getCreatedAt();
        this.updateddate = task.getUpdatedAt();
    }
}
