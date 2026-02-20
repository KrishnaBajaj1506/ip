import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the main entry point for the Mexicola chatbot.
 */
public class Mexicola {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String LINE = "____________________________________________________________";

    // Class-level state to track tasks

    public static void main(String[] args) {
        printWelcome();
        runBot();
        printExit();
    }

    private static void runBot() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }
            try {
                handleCommand(userInput);
            } catch (MexicolaException e) {
                printMessage(e.getMessage());
            }
        }
        sc.close();
    }

    /**
     * Routes the user's input to the correct helper method.
     * @throws MexicolaException If the command is unrecognized or arguments are missing.
     */
    private static void handleCommand(String userInput) throws MexicolaException {
        String command = userInput.split(" ")[0].toLowerCase();

        switch (command) {
            case "list":
                handleList();
                break;
            case "mark":
                handleMark(userInput);
                break;
            case "unmark":
                handleUnmark(userInput);
                break;
            case "todo":
                handleTodo(userInput);
                break;
            case "deadline":
                handleDeadline(userInput);
                break;
            case "event":
                handleEvent(userInput);
                break;
            case "delete":
                handleDelete(userInput);
                break;
            default:
                throw new MexicolaException("OOPS!!! I'm sorry, but I don't know what '" + command + "' means :-(");
        }
    }

    // --- Helper Methods for Commands ---

    private static void handleList() {
        System.out.println("    " + LINE);
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) { // Use .size()
            System.out.println("     " + (i + 1) + "." + tasks.get(i)); // Use .get()
        }
        System.out.println("    " + LINE);
    }

    private static void handleMark(String userInput) {
        int index = parseIndex(userInput);
        if (isValidIndex(index)) {
            tasks.get(index).markAsDone(); // Changed tasks[index] to tasks.get(index)
            printMessage("Nice! I've marked this task as done:\n       " + tasks.get(index));
        }
    }

    private static void handleUnmark(String userInput) {
        int index = parseIndex(userInput);
        if (isValidIndex(index)) {
            tasks.get(index).unmark(); // Changed tasks[index] to tasks.get(index)
            printMessage("OK, I've marked this task as not done yet:\n       " + tasks.get(index));
        }
    }

    private static void handleTodo(String userInput) throws MexicolaException {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            throw new MexicolaException("OOPS!!! The description of a todo cannot be empty.");
        }
        addTask(new Todo(description));
    }

    private static void handleDeadline(String userInput) throws MexicolaException {
        int byIndex = userInput.indexOf("/by");
        if (byIndex == -1) {
            throw new MexicolaException("OOPS!!! Please use '/by' to specify the deadline date.");
        }

        String description = userInput.substring(8, byIndex).trim();
        String by = userInput.substring(byIndex + 3).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new MexicolaException("OOPS!!! The description or date cannot be empty.");
        }
        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String userInput) throws MexicolaException {
        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            throw new MexicolaException("Wait! You forgot the tags. Please use '/from' and '/to'.");
        }

        String description = userInput.substring(5, fromIndex).trim();
        String from = userInput.substring(fromIndex + 5, toIndex).trim();
        String to = userInput.substring(toIndex + 3).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MexicolaException("Whoops! The event description and times cannot be empty.");
        }

        addTask(new Event(description, from, to));
    }

    private static void handleDelete(String userInput) throws MexicolaException {
        int index = parseIndex(userInput);
        if (index < 0 || index >= tasks.size()) {
            throw new MexicolaException("I can't delete what isn't there! Pick a valid number.");
        }

        Task removedTask = tasks.remove(index); // ArrayList handles the "shifting" for you!
        printMessage("Noted. I've removed this task:\n       " + removedTask +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    // --- Core Logic Helpers ---

    private static void addTask(Task task) {
        tasks.add(task); // ArrayLists grow automatically!
        printMessage("Got it. I've added this task:\n       " + task +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    private static int parseIndex(String userInput) {
        try {
            return Integer.parseInt(userInput.split(" ")[1]) - 1;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printMessage("OOPS!!! Please provide a valid task number.");
            return -1;
        }
    }

    private static boolean isValidIndex(int index) {
        if (index < 0 || index >= tasks.size()) { // Use .size()
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