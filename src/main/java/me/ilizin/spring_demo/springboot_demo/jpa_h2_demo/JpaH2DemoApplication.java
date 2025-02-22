package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.dao.TaskDAO;
import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			OptionsMenu selectedOption;
			do {
				selectedOption = jpaH2DemoMenu.showInitialMenu();
				if (selectedOption != null) {
					try {
						runOption(taskDAO, jpaH2DemoMenu, selectedOption);
					} catch (Exception ex) {
						logger.warn("A serious error happened, we close the application", ex);
						selectedOption = OptionsMenu.EXIT;
					}
				} else {
					System.out.println("The option selected is wrong, choose an option between 1 and 8");
				}
				jpaH2DemoMenu.pressToContinue();
			} while (selectedOption != OptionsMenu.EXIT) ;
			jpaH2DemoMenu.close();
		};
	}

	private void runOption(TaskDAO taskDAO, JpaH2DemoMenu jpaH2DemoMenu, OptionsMenu selectedOption) {
		switch (selectedOption) {
			case CREATE_TASK:
				createTask(jpaH2DemoMenu, taskDAO);
				break;
			case UPDATE_TASK:
				updateTask(jpaH2DemoMenu, taskDAO);
				break;
			case DELETE_TASK:
				deleteTask(jpaH2DemoMenu, taskDAO);
				break;
			case READ_TASK_BY_ID:
				readTaskById(jpaH2DemoMenu, taskDAO);
				break;
			case READ_TASK_BY_WORD:
				readTaskByWord(jpaH2DemoMenu, taskDAO);
				break;
			case LIST_ALL_TASKS:
				listAllTasks(jpaH2DemoMenu, taskDAO);
				break;
			case DELETE_ALL_TASKS:
				deleteAllTasks(jpaH2DemoMenu, taskDAO);
				break;
			case EXIT: break;
			default:
				System.out.println("The option selected is wrong, choose an option between 1 and 8");
		}
	}

	private void deleteAllTasks(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		int numberDeletedTaks = taskDAO.deleteAll();
		jpaH2DemoMenu.printDeletedTasks(numberDeletedTaks);
	}

	private void deleteTask(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		Task task = readTaskById(jpaH2DemoMenu, taskDAO);
		if(task == null) {
			return;
		}
		taskDAO.delete(task);
	}

	private void updateTask(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		Task task = readTaskById(jpaH2DemoMenu, taskDAO);
		if(task == null) {
			return;
		}
		int id = task.getId();
		task = jpaH2DemoMenu.readTask();
		if (task == null) {
			return;
		}
		task.setId(id);
		taskDAO.update(task);
		jpaH2DemoMenu.printTask(task, task.getId());
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

	private Task readTaskById(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		String id = jpaH2DemoMenu.readId();
		try {
			int idNum = Integer.parseInt(id);
			logger.debug("Reading task by id '{}'", id);
			Task task = taskDAO.findById(idNum);
			logger.debug("Task read is '{}'", task);
			jpaH2DemoMenu.printTask(task, idNum);
			return task;
		} catch (NumberFormatException ex) {
			System.out.println("The id is wrong, it must be a number");
			return null;
		}
	}

	private void createTask(JpaH2DemoMenu jpaH2DemoMenu, TaskDAO taskDAO) {
		Task task = jpaH2DemoMenu.readTask();
		if (task == null) {
			return;
		}
		logger.debug("Saving a task object");
		taskDAO.save(task);
		logger.debug("Saved task. Generated id: '{}'", task.getId());
	}
}
