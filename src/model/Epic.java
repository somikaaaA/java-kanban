package model;

import java.util.Arrays;
import java.util.Objects;

import controllers.InMemoryTaskManager;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description, List<String> subtaskIds) {
        super(name, description);
        this.type = TaskType.EPIC;
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

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
        sb.append(",").append(subtaskIds.size());
        return sb.toString();
    }

    public static Epic fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        List<String> subtaskIds = Arrays.asList(parts.length > 5 ? parts[5].split(",") : new String[]{});
        return new Epic(name, description, subtaskIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }
}