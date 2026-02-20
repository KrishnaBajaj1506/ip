/**
 * Represents a Todo task, which is a task without any associated date or time.
 */
public class Todo extends Task {

    /**
     * Initializes a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task.
     * Format: "[T][Status] Description"
     *
     * @return The formatted string representation.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}