package taskflow;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Maintains tasks in creation order and applies changes using stable identifiers.
 * @author TaskFlow project
 */
public final class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    /** Starts an empty workspace. */
    public TaskManager() { }

    /** Returns the tasks in creation order.
     * @return a read-only snapshot
     */
    public List<Task> all() {
        return List.copyOf(tasks);
    }

    /** Creates a task.
     * @param title required title
     * @param description optional notes
     * @param status initial stage
     * @return the new task
     */
    public Task add(String title, String description, Status status) {
        Task task = new Task(UUID.randomUUID(), title, description, status);
        tasks.add(task);
        return task;
    }

    /** Replaces a task in its original list position.
     * @param id original identifier
     * @param title revised title
     * @param description revised notes
     * @param status revised stage
     * @return the replacement task
     * @throws NoSuchElementException if the task is missing
     */
    public Task update(UUID id, String title, String description, Status status) {
        int position = indexOf(id);
        Task replacement = new Task(id, title, description, status);
        tasks.set(position, replacement);
        return replacement;
    }

    /** Removes the task with the supplied identifier.
     * @param id task to remove
     * @throws NoSuchElementException if the task is missing
     */
    public void delete(UUID id) {
        tasks.remove(indexOf(id));
    }

    /** Finds the current list position for an identifier. */
    private int indexOf(UUID id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id().equals(id)) return i;
        }
        throw new NoSuchElementException("This task no longer exists.");
    }
}
