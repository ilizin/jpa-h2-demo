package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;

import java.util.List;

public interface TaskDAO {
    void save(Task student);

    Task findById(Integer id);

    List<Task> findAll();

    List<Task> findByWord(String word);
}
