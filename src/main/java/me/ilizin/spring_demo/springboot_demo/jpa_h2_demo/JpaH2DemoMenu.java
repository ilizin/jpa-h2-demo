package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class JpaH2DemoMenu {

    private final Scanner scanner;

    public JpaH2DemoMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public OptionsMenu showInitialMenu() {
        String selection;
        System.out.println("\nChoose from these choices");
        System.out.println("-------------------------");
        System.out.println("1 - Create a task");
        System.out.println("2 - Update a task");
        System.out.println("3 - Delete a task");
        System.out.println("4 - Read a task by id");
        System.out.println("5 - Read a task by word");
        System.out.println("6 - List all tasks");
        System.out.println("7 - Delete all tasks");
        System.out.println("8 - Exit the demo");
        System.out.println();
        System.out.print("Option ==> ");
        selection = scanner.next();
        return OptionsMenu.get(selection);
    }

    public Task readTask() {
        System.out.println("\nCreate a task");
        System.out.println("-------------------------");
        System.out.print("Task title ==> ");
        String taskTitle = scanner.next();
        System.out.print("Task description ==> ");
        String taskDescription = scanner.next();
        System.out.print("Task due date (dd-MM-yyyy) ==> ");
        String dueDate = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate dateTime = LocalDate.parse(dueDate.trim(), formatter);
            return new Task(taskTitle, taskDescription, dateTime.atStartOfDay());
        } catch (DateTimeParseException ex) {
            System.out.println("Error entering the date, please use the correct format (dd-MM-yyyy)");
            return null;
        }
    }

    public String readId() {
        System.out.println("\nRead a task by id");
        System.out.println("-------------------------");
        System.out.print("Task id ==> ");
        return scanner.next();
    }

    public String readWord() {
        System.out.println("\nRead a task by word");
        System.out.println("-------------------------");
        System.out.print("Word ==> ");
        return scanner.next();
    }

    public void listAllTasks() {
        System.out.println("\nList all tasks");
        System.out.println("-------------------------");
    }

    public void printTask(Task task, int id) {
        if (task == null) {
            System.out.println("No task with id '" + id + "' found");
        } else {
            System.out.println("Task title: " + task.getTitle());
            System.out.println("Task description: " + task.getDescription());
            System.out.println("Task due data: " + task.getDueDate());
        }
    }

    public void printAllTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
        }
        for (Task task: tasks) {
            System.out.println("Task id: " + task.getId());
            System.out.println("Task title: " + task.getTitle());
            System.out.println("Task description: " + task.getDescription());
            System.out.println("Task due date: " + task.getDueDate());
            System.out.println();
        }
    }

    public void printDeletedTasks(int numberDeletedTaks) {
        System.out.println("\nDelete all tasks");
        System.out.println("-------------------------");
        if (numberDeletedTaks == 0) {
            System.out.println("No tasks to delete");
        }
        System.out.println("Deleted tasks number: " + numberDeletedTaks);
    }

    public void close() {
        scanner.close();
    }

    public void pressToContinue() {
        System.out.print("\nPress to continue");
        //TODO Review this, why twice?
        scanner.nextLine();
        scanner.nextLine();
    }
}
