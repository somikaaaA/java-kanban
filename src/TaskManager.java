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

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    //Метод для удаления задачи по идентификатору
    public void removeTask(int id) {
        tasks.remove(id);
    }

    //Метод для обновления задачи
    public void updateTask(Task updatedTask) {
        if (!tasks.containsKey(updatedTask.getId())) {
            System.err.println("\nЗадача с указанным ID не найдена.");
            return;
        }

        tasks.replace(updatedTask.getId(), updatedTask);
        System.out.println("\nЗадача с ID " + updatedTask.getId() + " успешно обновлена.");
    }

    //Метод для удаления всех задач
    public void deleteAllTasks() {
        tasks.clear();
        System.out.println("\nВсе задачи успешно удалены.");
    }

    //Метод для вывода всех задач
    public void printAllTasks() {
        System.out.println("\nСписок всех задач");
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }


}