package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

import me.ilizin.spring_demo.springboot_demo.jpa_h2_demo.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class JpaH2DemoMenu {

    private final Scanner scanner;

    public JpaH2DemoMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showInitialMenu() {
        int selection;
        System.out.println("\nChoose from these choices");
        System.out.println("-------------------------");
        System.out.println("1 - Create a task");
        System.out.println("2 - Add a task");
        System.out.println("3 - Update a task");
        System.out.println("4 - Delete a task");
        System.out.println("5 - Read a task by id");
        System.out.println("6 - Read a task by word");
        System.out.println("7 - List all tasks");
        System.out.println("8 - Exit the demo");
        System.out.print("Option ==> ");
        selection = scanner.nextInt();
        return selection;
    }

    public Task readTask() {
        System.out.println("\nCreate a task");
        System.out.println("-------------------------");
        System.out.print("Task title ==> ");
        String taskTitle = scanner.next();
        System.out.print("Task description ==> ");
        String taskDescription = scanner.next();
        return new Task(taskTitle, taskDescription, LocalDateTime.now());
    }

    public int readId() {
        System.out.println("\nRead a task by id");
        System.out.println("-------------------------");
        System.out.print("Task id ==> ");
        return scanner.nextInt();
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
            System.out.println("No task with id = " + id + " found");
        } else {
            System.out.println("Task title: " + task.getTitle());
            System.out.println("Task description: " + task.getDescription());
            System.out.println("Task due data: " + task.getDueDate());
        }
    }

    public void printAllTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to show");
        }
        for (Task task: tasks) {
            System.out.println("Task id: " + task.getId());
            System.out.println("Task title: " + task.getTitle());
            System.out.println("Task description: " + task.getDescription());
            System.out.println("Task due date: " + task.getDueDate());
            System.out.println();
        }
    }

    public void close() {
        scanner.close();
    }
}
