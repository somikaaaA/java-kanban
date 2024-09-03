package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String descriptionTask, String nameTask,
                   Status status, int idEpicTask, Duration durationTask, LocalDateTime start) {
        super(descriptionTask, nameTask, Status.NEW);
        this.epicId = idEpicTask;
        this.setDuration(durationTask);
        this.setStartTime(start);
    }

    public Subtask(Subtask subtask) {
        super(subtask);
        this.epicId = subtask.epicId;
    }

    public Subtask(String name, String description, Status status,
                   int epicId, Duration duration, LocalDateTime startTime) {
        super(name, description, status);
        this.epicId = epicId;
        setDuration(duration);
        setStartTime(startTime);
    }

    public Subtask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public void setEpic(Epic epic) {
        this.epicId = epic.getId();
    }

    public int getIdEpic() {
        return epicId;
    }

    public void setIdEpicTask(int idEpicTask) {
        this.epicId = idEpicTask;
    }

    @Override
    public String toString() {
        return "SubTask {" +
                " id = " + getId() +
                ", name = '" + getName() +
                "', description='" + getDescription() +
                "', status=" + getStatus() +
                ", taskType = " + TaskType.SUBTASK +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                '}';
    }
}
