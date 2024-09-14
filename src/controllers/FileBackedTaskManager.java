package controllers;

import exceptions.ManagerSaveException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import model.TaskType;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public int addSubTask(Subtask subtask) {
        final int id = super.addSubTask(subtask);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        final int id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public int addTask(Task task) {
        final int id = super.addTask(task);
        save();
        return id;
    }

    private static final String HEADER =
            "id, type, name, status, description, epic, startTime, duration, endTime";

    public static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",")
                .append(task.getType()).append(",")
                .append(task.getName()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription())
                .append(task.getStartTime() != null ? "," +
                        task.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "").append(",")
                .append(task.getDuration().toMinutes()).append(",")
                .append(task.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        if (task.getType().equals(TaskType.SUBTASK)) {
            sb.append(",").append(((Subtask) task).getIdEpic());
        }

        return sb.toString();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(HEADER);
            writer.newLine();

            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }

            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Файл нельзя сохранить: " + file.getName(), e);
        }
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status progress = Status.valueOf(parts[3]);
        String description = parts[4];
        int length = parts.length;
        String startTime = parts[length - 3];
        String duration = parts[length - 2];
        String endTime = parts[length - 1];

        LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Duration durationTask = Duration.ofMinutes(Long.parseLong(duration));
        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        switch (type) {
            case TASK:
                return new Task(id, name, description, progress, start, durationTask);

            case EPIC:
                List<Integer> subTasks = new ArrayList<>();
                if (parts.length > 5) {
                    String[] subTaskIds = parts[5].split(",");
                    for (String subTaskId : subTaskIds) {
                        subTasks.add(Integer.parseInt(subTaskId));
                    }
                }
                return new Epic(id, name, description, progress, subTasks, start, durationTask, end);
            case SUBTASK:
                int epicId = Integer.parseInt(parts[5]);
                return new Subtask(id, description, name, progress, epicId, durationTask, start);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split(System.lineSeparator());
            int generatorId = 0;
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    break;
                }
                final Task task = fromString(line);
                final int id = task.getId();
                if (id > generatorId) {
                    generatorId = id;
                }
                taskManager.addAnyTask(task);
            }
            for (Map.Entry<Integer, Subtask> e : taskManager.subtasks.entrySet()) {
                final Subtask subtask = e.getValue();
                final Epic epic = taskManager.epics.get(subtask.getIdEpic());
                epic.getSubtasks().add(subtask.getId());
            }
            InMemoryTaskManager.generatorId = generatorId;
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file: " + file.getName(), e);
        }
        return taskManager;
    }

    protected void addAnyTask(Task task) {
        final int id = task.getId();
        switch (task.getType()) {
            case TASK:
                tasks.put(id, task);
                break;
            case SUBTASK:
                subtasks.put(id, (Subtask) task);
                break;
            case EPIC:
                epics.put(id, (Epic) task);
                break;
            default:
        }
    }
}