import java.util.*;

public class Mexicola {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        // max 100 task
        Task[] tasks = new Task[100]; // Array of Task objects now
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
                System.out.println(line + "\n Here are the tasks in your list:");
                // Only loop up to the current number of tasks added
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i].toString());
                }
                System.out.println(line);
            }
            else if (userInput.startsWith("mark ")) {
                // Extracts the number and converts to 0-based index
                int idx = Integer.parseInt(userInput.substring(5)) - 1;
                tasks[idx].markAsDone();
                System.out.println(line + "\n Nice! I've marked this task as done:\n   " + tasks[idx] + "\n" + line);
            } else if (userInput.startsWith("unmark ")) {
                int idx = Integer.parseInt(userInput.substring(7)) - 1;
                tasks[idx].unmark();
                System.out.println(line + "\n OK, I've marked this task as not done yet:\n   " + tasks[idx] + "\n" + line);
            }
            else {
                // Check if there is still space in the array
                if (taskCount < 100) {
                    tasks[taskCount] = new Task(userInput);
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