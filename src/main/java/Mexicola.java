import java.util.Scanner;

/**
 * Represents the main entry point for the Mexicola chatbot.
 */
public class Mexicola {
    private static final int MAX_TASKS = 100;
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        System.out.println(LINE);
        System.out.println(" Hello! I'm Mexicola");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            if (userInput.equalsIgnoreCase("list")) {
                System.out.println(LINE);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i].toString());
                }
                System.out.println(LINE);
            } else if (userInput.startsWith("mark ")) {
                int idx = Integer.parseInt(userInput.substring(5)) - 1;
                tasks[idx].markAsDone();
                System.out.println(LINE);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[idx]);
                System.out.println(LINE);
            } else if (userInput.startsWith("unmark ")) {
                int idx = Integer.parseInt(userInput.substring(7)) - 1;
                tasks[idx].unmark();
                System.out.println(LINE);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[idx]);
                System.out.println(LINE);
            } else {
                if (taskCount < MAX_TASKS) {
                    tasks[taskCount] = new Task(userInput);
                    taskCount++;
                    System.out.println(LINE);
                    System.out.println(" added: " + userInput);
                    System.out.println(LINE);
                } else {
                    System.out.println(LINE);
                    System.out.println(" Sorry, your task list is full!");
                    System.out.println(LINE);
                }
            }
        }
        sc.close();
    }
}