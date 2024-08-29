package model;

import java.util.Objects;
import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.id = -1; // Инициализируем с некорректным значением, чтобы проверить корректность генерации ID
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = TaskType.TASK;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( id + ","
                + type + ","
                + name + ","
                + status + ","
                + description + ","
                + duration.toMinutes() + ","
                + startTime.toString());

        return sb.toString();
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        long durationMinutes = Long.parseLong(parts[5]);
        String startTimeStr = parts[6];

        Duration duration = Duration.ofMinutes(durationMinutes);
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        //int epicId = parts.length > 5 ? Integer.parseInt(parts[5]) : -1;
        return new Task(name, description, duration, startTime);
    }

//    private Task(String name, String description, Duration duration, LocalDateTime startTime) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.status = status;
//        this.type = TaskType.TASK;
//        this.duration = duration;
//        this.startTime = startTime;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                Objects.equals(duration, task.duration) &&
                Objects.equals(startTime, task.startTime);

//        Duration duration = Duration.ofMinutes(durationMinutes);
//        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, duration, startTime);
    }
}