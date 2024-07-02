import java.util.*;

public class TaskManager {
    private static TaskManager instance;
    private final Map<Integer, Task> tasks = new HashMap<>();

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task updatedTask) {
        if (!tasks.containsKey(updatedTask.getId())) {
            System.err.println("Задача с указанным ID не найдена.");
            return;
        }
        tasks.replace(updatedTask.getId(), updatedTask);
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void printAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
            if (task instanceof Epic) {
                ((Epic) task).printSubtasks();
            }
        }
    }
}
