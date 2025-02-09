package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao.StudentDAO;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaH2DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(JpaH2DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaH2DemoApplication.class, args);
	}

	// Executed after the spring beans have been loaded
	/* When using @Bean for a method such as commandLineRunner, Spring will check the arguments to the method.
	   Spring will resolve the method arguments by injecting the appropriate Spring Bean. In this case,
	   there is no need to use @Autowired. Spring will inject the beans automatically behind the scenes. */
	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			createStudent(studentDAO);
		};
	}

	private void createStudent(StudentDAO studentDAO) {
		logger.debug("Creating a new student object");
		Student student = new Student("Paul", "Doe", "paul@gmx.com");
		logger.debug("Saving a student object");
		studentDAO.save(student);
		logger.debug("Saved student. Generated id: '{}'", student.getId());
	}
}
