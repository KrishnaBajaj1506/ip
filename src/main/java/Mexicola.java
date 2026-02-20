import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Represents the main entry point for the Mexicola chatbot.
 */
public class Mexicola {
    private static final String FILE_PATH = "./data/mexicola.txt"; // Path for Level 7
    private static final String LINE = "____________________________________________________________";

    // Class-level state to track tasks
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The main method that runs the chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        printWelcome();
        loadTasks();
        runBot();
        printExit();
    }

    /**
     * Runs the main command loop of the bot.
     */
    private static void runBot() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }
            handleCommand(userInput);
        }
        sc.close();
    }

    private static void saveTasks() {
        try {
            File f = new File(FILE_PATH);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs(); // Create 'data' folder if missing
            }
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            printMessage("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasks() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return;

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                    case "T":
                        Todo t = new Todo(description);
                        if (isDone) t.markAsDone();
                        tasks.add(t);
                        break;
                    case "D":
                        Deadline d = new Deadline(description, parts[3]);
                        if (isDone) d.markAsDone();
                        tasks.add(d);
                        break;
                    case "E":
                        Event e = new Event(description, parts[3], parts[4]);
                        if (isDone) e.markAsDone();
                        tasks.add(e);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            printMessage("No existing data file found.");
        }
    }

    /**
     * routes the user's input to the correct helper method.
     */
    private static void handleCommand(String userInput) {
        String command = userInput.split(" ")[0].toLowerCase();

        switch (command) {
            case "list":
                handleList();
                break;
            case "mark":
                handleMark(userInput);
                saveTasks();
                break;
            case "unmark":
                handleUnmark(userInput);
                saveTasks();
                break;
            case "todo":
                handleTodo(userInput);
                saveTasks();
                break;
            case "deadline":
                handleDeadline(userInput);
                saveTasks();
                break;
            case "event":
                handleEvent(userInput);
                saveTasks();
                break;
            default:
                printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    // --- Helper Methods for Commands ---

    private static void handleList() {
        System.out.println("    " + LINE);
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println("    " + LINE);
    }

    private static void handleMark(String userInput) {
        int index = parseIndex(userInput);
        if (isValidIndex(index)) {
            tasks.get(index).markAsDone();
            printMessage("Nice! I've marked this task as done:\n       " + tasks.get(index));
        }
    }

    private static void handleUnmark(String userInput) {
        int index = parseIndex(userInput);
        if (isValidIndex(index)) {
            tasks.get(index).unmark();
            printMessage("OK, I've marked this task as not done yet:\n       " + tasks.get(index));
        }
    }

    private static void handleTodo(String userInput) {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            printMessage("OOPS!!! The description of a todo cannot be empty.");
            return;
        }
        addTask(new Todo(description));
    }

    private static void handleDeadline(String userInput) {
        int byIndex = userInput.indexOf("/by");
        if (byIndex == -1) {
            printMessage("OOPS!!! Please use '/by' to specify the deadline date.");
            return;
        }

        // Extract description (start after "deadline " and end before "/by")
        String description = userInput.substring(8, byIndex).trim();
        // Extract date (start after "/by ")
        String by = userInput.substring(byIndex + 3).trim();

        if (description.isEmpty() || by.isEmpty()) {
            printMessage("OOPS!!! The description or date cannot be empty.");
            return;
        }
        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String userInput) {
        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            printMessage("OOPS!!! Please use '/from' and '/to' to specify the event time.");
            return;
        }

        // Extract description (start after "event " and end before "/from")
        String description = userInput.substring(5, fromIndex).trim();
        // Extract from time (start after "/from " and end before "/to")
        String from = userInput.substring(fromIndex + 5, toIndex).trim();
        // Extract to time (start after "/to ")
        String to = userInput.substring(toIndex + 3).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            printMessage("OOPS!!! Event description and time cannot be empty.");
            return;
        }
        addTask(new Event(description, from, to));
    }

    // --- Core Logic Helpers ---

    private static void addTask(Task task) {
        tasks.add(task); // ArrayLists grow automatically
        printMessage("Got it. I've added this task:\n       " + task +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    private static int parseIndex(String userInput) {
        try {
            // Split by space and take the second part (the number)
            return Integer.parseInt(userInput.split(" ")[1]) - 1;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printMessage("OOPS!!! Please provide a valid task number.");
            return -1;
        }
    }

    private static boolean isValidIndex(int index) {
        if (index < 0 || index >= tasks.size()) { // Changed taskCount to tasks.size()
            printMessage("OOPS!!! That task number is invalid.");
            return false;
        }
        return true;
    }

    // --- UI Helpers ---

    private static void printWelcome() {
        printMessage("Hello! I'm Mexicola\n     What can I do for you?");
    }

    private static void printExit() {
        printMessage("Bye. Hope to see you again soon!");
    }

    private static void printMessage(String message) {
        System.out.println("    " + LINE);
        System.out.println("     " + message);
        System.out.println("    " + LINE);
    }
}