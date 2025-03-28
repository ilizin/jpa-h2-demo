package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskDAOImplTest {

    @Autowired
    TaskDAOImpl taskDAO;

    @Test
    public void taskDAOImplTest() {
        Task task = new Task("Gym", "Go to gym", LocalDateTime.now());
        taskDAO.save(task);
        taskDAO.findById(1);
        assertThat(taskDAO.findById(1)).isNotNull();
    }
}
