package controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class InMemoryTaskManager implements TaskManager {
    public final Map<Integer, Task> tasks;
    public final Map<Integer, Subtask> subTasks;
    public final Map<Integer, Epic> epics;
    protected static int generatorId = 0;
    protected final HistoryManager historyManager = new InMemoryHistoryManager();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getId));

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public ArrayList<Task> getEpics() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public ArrayList<Task> getSubTasks() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public int addTask(Task task) {
        if (isInsertionTask(task)) {
            throw new RuntimeException("Пересечение задачи");
        }
        int id = ++generatorId;
        task.setId(id);
        tasks.put(task.getId(), task);
        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        }
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        if (epic.getId() == 0) {
            int id = ++generatorId;
            epic.setId(id);
        }

        if (epics.containsKey(epic.getId())) {
            return -1;
        }
        int id = ++generatorId;
        epic.setId(id);
        epics.put(epic.getId(), epic);
        return id;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public int addSubTask(Subtask subTask) {
        if (isInsertionTask(subTask)) {
            throw new RuntimeException("Пересечение задачи");
        }

        if (subTask.getId() == 0) {
            int id = ++generatorId;
            subTask.setId(id);
        }

        int idEpicTask = subTask.getIdEpic();
        int idSubTask = subTask.getId();
        Epic epic = epics.get(idEpicTask);

        int id = ++generatorId;
        subTask.setId(id);
        if (idEpicTask == idSubTask) {
            return -1;
        }

        if (subTask.getStartTime() != null) {
            prioritizedTasks.add(subTask);
        }

        epic.getSubtasks().add(id);
        subTask.setEpic(epic);

        subTasks.put(subTask.getId(), subTask);

        updateEpicStatus(idEpicTask);
        updateEpicStartTime(epic);
        return id;
    }

    private void updateEpicStartTime(Epic epic) {
        List<Integer> subtaskId = epic.getSubtasks();
        if (subtaskId.isEmpty()) {
            epic.setDuration(Duration.ofMinutes(0));
        }
        LocalDateTime start = LocalDateTime.MAX;
        LocalDateTime end = LocalDateTime.MIN;
        long duration = 0;

        for (int id : subtaskId) {
            final Subtask subTask = subTasks.get(id);
            final LocalDateTime startTime = subTask.getStartTime();
            final LocalDateTime endTime = subTask.getEndTime();
            if (startTime.isBefore(start)) {
                start = startTime;
            }
            if (endTime.isAfter(end)) {
                end = endTime;
            }
            duration += subTask.getDuration().toMinutes();
        }
        epic.setStartTime(start);
        epic.setEndTime(end);
        epic.setDuration(Duration.ofMinutes(duration));
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubTask(int id) {
        Subtask subtask = subTasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Subtask> getTasks(int epicId) {
        return subTasks.values().stream()
                .filter(subTask -> subTask.getIdEpic() == epicId)
                .collect(Collectors.toList());
    }

    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        epic.getSubtasks().clear();
        epic.getSubtasks().addAll(savedEpic.getSubtasks());
        epics.put(epic.getId(), epic);
        updateEpicStartTime(epic);
    }

    public boolean isInsertionTask(Task task) {
        if (task.getStartTime() == null) {
            return false;
        }
        return prioritizedTasks.stream()
                .filter(p -> task.getStartTime().isBefore(p.getEndTime()))
                .anyMatch(p -> task.getEndTime().isAfter(p.getStartTime()));
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        final int id = subtask.getId();
        final int epicId = subtask.getIdEpic();
        final Subtask savedSubtask = subTasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        final Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subTasks.put(id, subtask);
        updateEpicStatus(epicId);
        updateEpicStartTime(epic);
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        List<Integer> subtaskIdsToRemove = subTasks.values().stream()
                .filter(subTask -> subTask.getIdEpic() == id)
                .map(Subtask::getId)
                .collect(Collectors.toList());

        subtaskIdsToRemove.forEach(subTasks::remove);
        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subTask = subTasks.remove(id);
        if (subTask == null) {
            return;
        }
        Epic epic = epics.get(subTask.getIdEpic());
        if (epic != null) {
            updateEpicStatus(epic.getId());
        }
    }

    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        int doneCounter = 0;
        int newCounter = 0;
        for (Subtask subTask : subTasks.values()) {
            if (subTask.getIdEpic() == epicId) {
                if (subTask.getStatus() == Status.NEW) {
                    newCounter++;
                }
                if (subTask.getStatus() == Status.DONE) {
                    doneCounter++;
                }
            }
        }

        if (newCounter == epic.getSubtasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (doneCounter == epic.getSubtasks().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}