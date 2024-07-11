package model;

import controllers.InMemoryTaskManager;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

//    public void addSubtask(Subtask subtask) {
//        subtaskIds.add(subtask.getId());
//    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    public void updateEpicStatus() {
        boolean allDone = true;
        for (int subtaskId : subtaskIds) {
            Subtask subtask = InMemoryTaskManager.getSubtaskById(subtaskId); // Использование статического метода
            if (subtask != null && subtask.getStatus() != Status.DONE) {
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

    // Метод для получения списка ID подзадач
    public List<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds); // Возвращаем копию списка, чтобы избежать внешнего изменения исходного списка
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", Number of Subtasks: ").append(subtaskIds.size());
        return sb.toString();
    }
}