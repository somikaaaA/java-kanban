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

//    public int getId() {
//        return Counter.nextId();
//    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

//    public List<Subtask> getSubtasksOfEpic(int epicId) {
//        Epic epic = (Epic) getTaskById(epicId);
//        if (epic!= null) {
//            return epic.getSubtasks();
//        } else {
//            return Collections.emptyList();
//        }
//    }
}