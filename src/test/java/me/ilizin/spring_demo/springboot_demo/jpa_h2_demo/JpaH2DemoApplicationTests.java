package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao.TaskDAOImpl;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test") // Prevent CommandLineRunner beans from executing during junit testing
@SpringBootTest
class JpaH2DemoApplicationTests {

	@Autowired
	TaskDAOImpl taskDAO;

	@Test
	void testTaskDAOImpl() {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.withNano(0); //remove milliseconds to avoid mismatch

		Task task = new Task("Gym", "Go to gym", localDateTime);
		taskDAO.save(task);
		assertThat(taskDAO.findById(1))
				.isEqualTo(new Task(1, "Gym", "Go to gym", localDateTime));

		assertThat(taskDAO.findAll())
				.isEqualTo(new ArrayList<>(Collections.singleton(new Task(1, "Gym", "Go to gym", localDateTime))));
	}

}
