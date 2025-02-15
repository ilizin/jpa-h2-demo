package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao.TaskDAO;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class JpaH2DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(JpaH2DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaH2DemoApplication.class, args);
	}

	@Bean
	Scanner scanner() {
		return new Scanner(System.in);
	}

	// Executed after the spring beans have been loaded
	/* When using @Bean for a method such as commandLineRunner, Spring will check the arguments to the method.
	   Spring will resolve the method arguments by injecting the appropriate Spring Bean. In this case,
	   there is no need to use @Autowired. Spring will inject the beans automatically behind the scenes. */
	@Bean
	public CommandLineRunner commandLineRunner(TaskDAO taskDAO, JpaH2DemoMenu jpaH2DemoMenu) {
		return runner -> {
			int selectedOption;
			do {
				selectedOption = jpaH2DemoMenu.showInitialMenu();
				try {
					switch (selectedOption) {
						case 1:
							createTask(jpaH2DemoMenu, taskDAO);
							break;
						case 5:
							readTask(jpaH2DemoMenu, taskDAO);
							break;
						case 6:
							readTaskByWord(jpaH2DemoMenu, taskDAO);
							break;
						case 7:
							listAllTasks(jpaH2DemoMenu, taskDAO);
							break;
						case 8: break;
						default:
							System.out.print("The option selected is wrong, choose an option between 1-8");
					}
				} catch	(InputMismatchException ex) {
					logger.warn("The input is wrong", ex);
					System.out.print("The option selected is wrong, choose an option between 1-8");
				} catch (Exception ex) {
					logger.warn("A serious error happened, we close the application", ex);
					selectedOption = 8;
				}
			} while (selectedOption != 8) ;
			jpaH2DemoMenu.close();
		};
	}

	private void readTaskByWord(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		String word = jpaH2DemoMenu.readWord();
		logger.debug("Reading all tasks by word '{}'", word);
		List<Task> tasks = taskDAO.findByWord(word);
		logger.debug("Tasks read are '{}'", tasks);
		jpaH2DemoMenu.printAllTasks(tasks);
	}

	private void listAllTasks(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		jpaH2DemoMenu.listAllTasks();
		logger.debug("Reading all tasks");
		List<Task> tasks = taskDAO.findAll();
		logger.debug("Tasks read are '{}'", tasks);
		jpaH2DemoMenu.printAllTasks(tasks);
	}

	private void readTask(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		int id = jpaH2DemoMenu.readId();
		logger.debug("Reading task by id '{}'", id);
		Task task = taskDAO.findById(id);
		logger.debug("Task read is '{}'", task);
		jpaH2DemoMenu.printTask(task, id);
	}

	private void createTask(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		Task task = jpaH2DemoMenu.readTask();
		logger.debug("Saving a task object");
		taskDAO.save(task);
		logger.debug("Saved task. Generated id: '{}'", task.getId());
	}
}
