/**
 * Represents an Event task, which occurs within a specific time range.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Initializes an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from        The start time/date.
     * @param to          The end time/date.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the event task.
     * Format: "[E][Status] Description (from: start to: end)"
     *
     * @return The formatted string representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the file-serialisable representation of this event task.
     *
     * @return the pipe-separated string for the data file, e.g.
     *         {@code E | 0 | meeting | Mon 2pm | Mon 4pm}
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}