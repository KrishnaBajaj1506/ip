/**
 * Represents a task that can be tracked by the chatbot.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initializes a new task with the given description.
     *
     * @param description The text description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon based on whether the task is done.
     *
     * @return The formatted string representation.
     */
    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {

        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {

        this.isDone = false;
    }
    /**
     * Returns the string representation of the task.
     * Format: "[Status] Description"
     *
     * @return The formatted string representation.
     */
    @Override
    public String toString() {

        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toFileFormat();
}