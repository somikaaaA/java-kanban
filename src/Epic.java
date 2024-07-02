import java.util.*;

public class Epic extends Task {
    private final List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateEpicStatus(); // Автоматическое обновление статуса эпика
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // Метод для вывода всех подзадач
    public void printSubtasks() {
        for (Task subtask : subtasks) {
            System.out.println("Name: " + subtask.getName() + ", Description: " + subtask.getDescription() + ", Статус: " + subtask.getStatus());
        }
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks.clear();
        this.subtasks.addAll(subtasks);
        updateEpicStatus(); // После добавления подзадач обновляем статус эпика
    }

    private void updateEpicStatus() {
        boolean allDone = true;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus()!= Status.DONE) {
                allDone = false;
                break;
            }
        }

        if (allDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder(super.toString());
//        sb.append(", Subtasks: ");
//        for (Subtask subtask : subtasks) {
//            sb.append(subtask.getName()).append(", ");
//        }
//        return sb.toString();
//    }

    @Override
    public String toString() {
        return super.toString() + ", Number of Subtasks: " + subtasks.size();
    }
}
