import java.util.ArrayList;

/**
 * Manages the list of tasks in the Mexicola application.
 * <p>
 * Wraps an {@link ArrayList} of {@link Task} objects and provides
 * convenient methods for adding, deleting, and retrieving tasks.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} pre-populated with an existing list of tasks.
     *
     * @param tasks the existing {@link ArrayList} of tasks to manage
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the {@link Task} to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified 0-based index.
     *
     * @param index the zero-based index of the task to remove
     * @return the {@link Task} that was removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified 0-based index without removing it.
     *
     * @param index the zero-based index of the task to retrieve
     * @return the {@link Task} at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return the {@link ArrayList} of {@link Task} objects
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
