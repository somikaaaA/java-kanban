import java.util.*;

public class Epic extends Task {
    private final List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", Subtasks: ");
        for (Subtask subtask : subtasks) {
            sb.append(subtask.getName()).append(", ");
        }
        return sb.toString();
    }
}
