package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private LocalDateTime endTime;
    private List<Integer> subtasks;

    public Epic(String nameTask, String descriptionTask) {
        super(0, nameTask, descriptionTask, Status.NEW);
        this.endTime = LocalDateTime.now();
        this.subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, List<Integer> subtasks) {
        super(id, name, description, status);
        this.subtasks = new ArrayList<>(subtasks);
        this.endTime = LocalDateTime.now();
    }

    public Epic(int id, String name, String description,
                Status status, List<Integer> subtasks, LocalDateTime start,
                Duration durationTask, LocalDateTime endTime) {
        super(id, name, description, status, start, durationTask);
        this.endTime = endTime;
        this.subtasks = subtasks;
    }

    public Epic(Epic epic) {
        super(epic);
        this.subtasks = epic.subtasks;
        this.endTime = epic.endTime;
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void removeSubtask(int id) {
        subtasks.remove(Integer.valueOf(id));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(",")
                .append(TaskType.EPIC).append(",")
                .append(getName()).append(",")
                .append(getStatus()).append(",")
                .append(getDescription()).append(",");

        List<Integer> subtaskIds = getSubtasks();
        if (!subtaskIds.isEmpty()) {
            boolean isFirst = true;
            for (Integer subTaskId : subtaskIds) {
                if (isFirst) {
                    sb.append(subTaskId);
                    isFirst = false;
                } else {
                    sb.append(",").append(subTaskId);
                }
            }
        }

        return sb.toString();
    }
}