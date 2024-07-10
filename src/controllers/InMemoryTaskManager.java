package controllers;

import model.Task;
import model.Subtask;
import model.Epic;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, Epic> epics = new HashMap<>();
    private static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final ArrayList<Task> history = new ArrayList<>();
    private int generatorId = 0;

    public InMemoryTaskManager() {}

    public static Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    @Override
    public int addNewTask(Task task) {
        final int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        final int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        final int id = ++generatorId;
        subtask.setId(id);
        subtasks.put(id, subtask);
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epicId);
        return id;
    }

    // Получение списка всех задач
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> tasks = new ArrayList<>(subtasks.values());
        return tasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> tasks = new ArrayList<>(epics.values());
        return tasks;
    }

    // Удаление всех задач
    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpicsAndSubtasks() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    // Получение по идентификатору
    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Task getSubtask(int id) {
        Task task = tasks.get(id);
        history.add(task); // Добавляем задачу в историю
        return task;
    }

    @Override
    public Task getEpic(int id) {
        Task task = tasks.get(id);
        history.add(task); // Добавляем задачу в историю
        return task;
    }

    // Обновление
    @Override
    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
    }

    // Удаление по идентификатору
    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Получение списка всех подзадач определенного эпика
    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtasks.get(id));
        }
        return tasks;
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic!= null) {
            epic.updateEpicStatus();
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        // Ограничиваем размер истории до последних 10 просмотров
        if (history.size() > 10) {
            history.subList(0, 10).clear(); // Очищаем старые записи
        }
        return history;
    }
}