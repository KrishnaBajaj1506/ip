import java.util.Scanner;

/**
 * Handles all user interface interactions for the Mexicola application.
 * <p>
 * This class is responsible for reading user input from the console and
 * displaying output such as welcome messages, divider lines, error messages,
 * and task-related feedback.
 * </p>
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    private static final String LINE = "____________________________________________________________";

    /**
     * Displays the welcome message when the application starts.
     * The message is surrounded by divider lines for visual clarity.
     */
    public void showWelcome() {
        showLine();
        System.out.println("     Hello! I'm Mexicola\n     What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the user exits.
     */
    public void showExit() {
        showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a warning that the saved task file could not be loaded.
     */
    public void showLoadingError() {
        showMessage("No saved data found or data file is corrupted. Starting with an empty task list.");
    }

    /**
     * Reads a command entered by the user from standard input.
     *
     * @return the line of text entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a horizontal divider line to the console.
     * Used to visually separate sections of output.
     */
    public void showLine() {
        System.out.println("    " + LINE);
    }

    /**
     * Displays an error message to the user, surrounded by divider lines.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("    " + LINE);
        System.out.println("     " + message);
        System.out.println("    " + LINE);
    }

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks the {@link TaskList} whose contents should be displayed
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Notifies the user that a new task has been added to the list.
     *
     * @param task      the task that was added
     * @param totalSize the new total number of tasks in the list
     */
    public void showTaskAdded(Task task, int totalSize) {
        showMessage("Got it. I've added this task:\n       " + task
                + "\n     Now you have " + totalSize + " tasks in the list.");
    }

    /**
     * Notifies the user that a task has been removed from the list.
     *
     * @param task      the task that was removed
     * @param totalSize the new total number of tasks remaining in the list
     */
    public void showTaskDeleted(Task task, int totalSize) {
        showMessage("Noted. I've removed this task:\n       " + task
                + "\n     Now you have " + totalSize + " tasks in the list.");
    }

    /**
     * Notifies the user that a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void showMarked(Task task) {
        showMessage("Nice! I've marked this task as done:\n       " + task);
    }

    /**
     * Notifies the user that a task has been marked as not done.
     *
     * @param task the task that was unmarked
     */
    public void showUnmarked(Task task) {
        showMessage("OK, I've marked this task as not done yet:\n       " + task);
    }

    /**
     * Displays the results of a {@code find} search.
     * <p>
     * If no tasks match, a friendly message is shown instead of an empty list.
     * </p>
     *
     * @param matches the list of tasks that matched the search keyword
     */
    public void showMatchingTasks(java.util.ArrayList<Task> matches) {
        showLine();
        if (matches.isEmpty()) {
            System.out.println("     No matching tasks found.");
        } else {
            System.out.println("     Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("     " + (i + 1) + "." + matches.get(i));
            }
        }
        showLine();
    }

    // -------------------------------------------------------------------------
    // Private helper
    // -------------------------------------------------------------------------

    /**
     * Prints a message surrounded by horizontal divider lines.
     *
     * @param message the message to display
     */
    private void showMessage(String message) {
        System.out.println("    " + LINE);
        System.out.println("     " + message);
        System.out.println("    " + LINE);
    }
}