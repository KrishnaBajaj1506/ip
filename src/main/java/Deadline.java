import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task, which is a task that needs to be done before a
 * specific date/time.
 */
public class Deadline extends Task {
    /**
     * Formatter used to parse user input: {@code yyyy-MM-dd HHmm} (e.g.
     * {@code 2019-12-02 1800}).
     */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Formatter used when saving to the data file (same as input so load/save
     * round-trips cleanly).
     */
    private static final DateTimeFormatter FILE_FORMAT = INPUT_FORMAT;

    /**
     * Formatter used when displaying the deadline to the user:
     * {@code MMM dd yyyy, h:mma}.
     */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    protected LocalDateTime by;

    /**
     * Constructs a Deadline task from a parsed {@link LocalDateTime}.
     *
     * @param description the description of the task
     * @param by          the deadline as a {@code LocalDateTime}
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date/time string in {@code yyyy-MM-dd HHmm} format and returns a
     * {@code Deadline} task.
     *
     * @param description the task description
     * @param byString    date/time string, e.g. {@code "2019-12-02 1800"}
     * @return a new {@code Deadline} with the parsed date
     * @throws MexicolaException if {@code byString} is not in the expected format
     */
    public static Deadline of(String description, String byString) throws MexicolaException {
        try {
            LocalDateTime by = LocalDateTime.parse(byString, INPUT_FORMAT);
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new MexicolaException(
                    "OOPS!!! The date must be in 'yyyy-MM-dd HHmm' format (e.g. 2019-12-02 1800).");
        }
    }

    /**
     * Returns the string representation of the deadline task.
     * Format: {@code [D][Status] Description (by: MMM dd yyyy, h:mma)}
     *
     * @return the formatted string representation
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns the file-serialisable representation of this task.
     * The date is stored in {@code yyyy-MM-dd HHmm} format so it can be re-parsed
     * on load.
     *
     * @return the pipe-separated file format string
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(FILE_FORMAT);
    }
}