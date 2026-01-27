import java.util.*;

public class Mexicola {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        // max 100 task
        String[] tasks = new String[100];
        int taskCount = 0; // Keeps track of how many tasks are actually in the array

        System.out.println(line);
        System.out.println(" Hello! I'm Mexicola");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println(line);
                // Only loop up to the current number of tasks added
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
            } else {
                // Check if there is still space in the array
                if (taskCount < 100) {
                    tasks[taskCount] = userInput;
                    taskCount++;
                    System.out.println(line);
                    System.out.println(" added: " + userInput);
                    System.out.println(line);
                } else {
                    System.out.println(line);
                    System.out.println(" Sorry, your task list is full!");
                    System.out.println(line);
                }
            }
        }
    }
}