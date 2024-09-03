package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {
    private int id;
    private String name;
    private String description;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(Task task) {
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.status = task.status;
        this.startTime = task.startTime;
        this.duration = task.duration;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ofMinutes(0);
    }

    public Task(String nameTask, String descriptionTask, Status status) {
        this.description = descriptionTask;
        this.name = nameTask;
        this.status = status;
    }

    public Task(int id, String name, String description,
                Status status, LocalDateTime start, Duration durationTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = durationTask;
        this.startTime = start;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plusMinutes(duration.toMinutes());
    }

    @Override
    public String toString() {
        return "Task { " +
                " id = " + id +
                ", name ='" + name + '\'' +
                ", description ='" + description + '\'' +
                ", status = " + status +
                ", type = " + TaskType.TASK +
                ", duration = " + duration +
                ", startTime = " + (startTime != null ?
                getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return id == other.id &&
                Objects.equals(name, other.name) &&
                Objects.equals(description, other.description) &&
                status == other.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    public TaskType getType() {
        if (this instanceof Epic) {
            return TaskType.EPIC;
        } else if (this instanceof Subtask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    @Override
    public int compareTo(Task o) {
        return this.startTime.compareTo(o.getStartTime());
    }
}