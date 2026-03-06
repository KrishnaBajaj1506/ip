/**
 * Parses and executes user commands for the Mexicola chatbot.
 * <p>
 * This class interprets raw command strings entered by the user, performs
 * the appropriate operation on the {@link TaskList}, and communicates
 * results back via the {@link Ui}.
 * </p>
 */
public class Parser {

    /**
     * Parses a raw user input string and executes the corresponding command.
     *
     * @param userInput the full line of text entered by the user
     * @param tasks     the current task list to operate on
     * @param ui        the UI handler for displaying output
     * @return {@code true} if the bot should continue running, {@code false} if the
     *         user typed "bye"
     * @throws MexicolaException if the command is unrecognised or arguments are
     *                           invalid
     */
    public static boolean parse(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        String command = userInput.split(" ")[0].toLowerCase();

        switch (command) {
            case "bye":
                return false;
            case "list":
                ui.showTaskList(tasks);
                break;
            case "mark":
                handleMark(userInput, tasks, ui);
                break;
            case "unmark":
                handleUnmark(userInput, tasks, ui);
                break;
            case "todo":
                handleTodo(userInput, tasks, ui);
                break;
            case "deadline":
                handleDeadline(userInput, tasks, ui);
                break;
            case "event":
                handleEvent(userInput, tasks, ui);
                break;
            case "delete":
                handleDelete(userInput, tasks, ui);
                break;
            default:
                throw new MexicolaException("OOPS!!! I'm sorry, but I don't know what '" + command + "' means :-(");
        }
        return true;
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    /**
     * Marks the task at the specified 1-based index as done.
     *
     * @param userInput raw input containing the task number
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the index is missing or out of range
     */
    private static void handleMark(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        int index = parseIndex(userInput, tasks);
        tasks.get(index).markAsDone();
        ui.showMarked(tasks.get(index));
    }

    /**
     * Marks the task at the specified 1-based index as not done.
     *
     * @param userInput raw input containing the task number
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the index is missing or out of range
     */
    private static void handleUnmark(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        int index = parseIndex(userInput, tasks);
        tasks.get(index).unmark();
        ui.showUnmarked(tasks.get(index));
    }

    /**
     * Creates a new {@link Todo} task from the user input and adds it to the list.
     *
     * @param userInput raw input starting with "todo"
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the description is empty
     */
    private static void handleTodo(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            throw new MexicolaException("OOPS!!! The description of a todo cannot be empty.");
        }
        Task t = new Todo(description);
        tasks.addTask(t);
        ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Creates a new {@link Deadline} task from the user input and adds it to the
     * list.
     *
     * @param userInput raw input starting with "deadline"
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the description, date, or {@code /by} tag is
     *                           missing
     */
    private static void handleDeadline(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        int byIndex = userInput.indexOf("/by");
        if (byIndex == -1) {
            throw new MexicolaException("OOPS!!! Please use '/by' to specify the deadline date.");
        }
        String description = userInput.substring(8, byIndex).trim();
        String by = userInput.substring(byIndex + 3).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new MexicolaException("OOPS!!! The description or date cannot be empty.");
        }
        Task t = new Deadline(description, by);
        tasks.addTask(t);
        ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Creates a new {@link Event} task from the user input and adds it to the list.
     *
     * @param userInput raw input starting with "event"
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the description, start, end, or the required
     *                           tags are missing
     */
    private static void handleEvent(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
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
        Task t = new Event(description, from, to);
        tasks.addTask(t);
        ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Deletes the task at the specified 1-based index from the list.
     *
     * @param userInput raw input containing the task number
     * @param tasks     the task list
     * @param ui        the UI handler
     * @throws MexicolaException if the index is missing or out of range
     */
    private static void handleDelete(String userInput, TaskList tasks, Ui ui) throws MexicolaException {
        int index = parseIndex(userInput, tasks);
        Task removed = tasks.delete(index);
        ui.showTaskDeleted(removed, tasks.size());
    }

    /**
     * Parses a 1-based task index from the second token of the user input.
     *
     * @param userInput the raw user input
     * @param tasks     the task list (used to validate range)
     * @return the 0-based index of the task
     * @throws MexicolaException if the number is missing, not a number, or out of
     *                           range
     */
    private static int parseIndex(String userInput, TaskList tasks) throws MexicolaException {
        try {
            int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MexicolaException("OOPS!!! That task number is invalid.");
            }
            return index;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new MexicolaException("OOPS!!! Please provide a valid task number.");
        }
    }
}
