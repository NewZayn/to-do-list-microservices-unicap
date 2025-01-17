package com.example.demo.Repository;

import com.example.demo.model.TaskState;
import com.example.demo.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskState, Long> {

    @Query("SELECT TS FROM TaskState TS WHERE TS.taskStatus =:status")
    public TaskState findByStatus(@Param("status") String status);
}
