import java.io.IOException;

/**
 * Main entry point for the Mexicola chatbot.
 * Instantiates the {@link Ui}, {@link Storage}, {@link TaskList}, and
 * {@link Parser} classes to run the chatbot loop.
 */
public class Mexicola {

    private static final String FILE_PATH = "./data/mexicola.txt";

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Initialises the chatbot by setting up the UI, storage, and task list.
     * If the saved data file cannot be loaded, an empty task list is used instead.
     *
     * @param filePath path to the persistent data file
     */
    public Mexicola(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MexicolaException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main command loop.
     * <p>
     * Reads commands from the user, delegates parsing and execution to
     * {@link Parser}, and saves the task list after every mutating command.
     * The loop exits when the user types {@code bye}.
     * </p>
     */
    public void run() {
        ui.showWelcome();
        boolean isRunning = true;

        while (isRunning) {
            String userInput = ui.readCommand();
            ui.showLine();
            try {
                isRunning = Parser.parse(userInput, tasks, ui);
                // Persist after every successful command (save is cheap)
                storage.save(tasks.getTasks());
            } catch (MexicolaException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        }

        ui.showExit();
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Mexicola(FILE_PATH).run();
    }
}