package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import controllers.InMemoryTaskManager;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected final List<Integer> subtaskIds = new ArrayList<>();
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Epic(String name, String description, List<Integer> subtaskIds) {
        super(name, description, Duration.ZERO, LocalDateTime.MAX);
        this.type = TaskType.EPIC;
        this.duration = calculateDuration(subtaskIds);
        this.startTime = calculateStartTime(subtaskIds);
        this.endTime = calculateEndTime();
    }

    private Duration calculateDuration(List<Integer> subtaskIds) {
        Duration totalDuration = Duration.ZERO;
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = InMemoryTaskManager.getSubtaskById(subtaskId);
            if (subtask != null && subtask.getDuration() != null) {
                totalDuration = totalDuration.plus(subtask.getDuration());
            }
        }
        return totalDuration;
    }

    private LocalDateTime calculateStartTime(List<Integer> subtaskIds) {
        LocalDateTime earliestStartTime = LocalDateTime.MAX;
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = InMemoryTaskManager.getSubtaskById(subtaskId);
            if (subtask != null && subtask.getStartTime() != null) {
                earliestStartTime = earliestStartTime.isBefore(subtask.getStartTime()) ? earliestStartTime : subtask.getStartTime();
            }
        }
        return earliestStartTime;
    }

    private LocalDateTime calculateEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
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
        sb.append(",").append(duration.toMinutes());
        sb.append(",").append(startTime.toString());
        sb.append(",").append(endTime.toString());
        return sb.toString();
    }

    public static Epic fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        long durationMinutes = Long.parseLong(parts[5]);
        String startTimeStr = parts[6];
        String endTimeStr = parts[7];

        Duration duration = Duration.ofMinutes(durationMinutes);
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);

        return new Epic(name, description, Arrays.asList());
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