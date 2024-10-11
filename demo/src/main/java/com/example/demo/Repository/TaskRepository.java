package com.example.demo.Repository;


import com.example.demo.model.Task;
import com.example.demo.model.TaskCategory;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.category.id =:id and t.state.id =:state and t.client.id =:clientID")
    public List<Task> findByCategory(@Param("id") Long id,
                                     @Param("state") Long state,
                                     @Param("clientID") Long clientID);

    @Query("select t from Task  t where t.category.id= :id")
    public List<Task> findByCategoryId(@Param("id") Long id);

    @Query("SELECT t from Task t WHERE t.id= :id AND t.client=:clientID")
    public Task findTask(@Param("id") Long id, @Param("clientID") Long clientID);

    @Query("SELECT t FROM Task  t where t.state= :state")
    public List<Task> findByState(@Param("state") Long state);

    @Query("SELECT t FROM Task t WHERE t.client.id = :userID")
    public List<Task> findByClient(@Param("userID") Long userID);

}
