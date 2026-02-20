/**
 * Represents a Deadline task, which is a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Initializes a Deadline task with a description and a due date.
     *
     * @param description The description of the task.
     * @param by The deadline date/time as a string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline task.
     * Format: "[D][Status] Description (by: time)"
     *
     * @return The formatted string representation.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}