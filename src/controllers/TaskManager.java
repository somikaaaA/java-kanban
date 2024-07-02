package controllers;

import model.Task;
import model.Subtask;
import model.Epic;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    public TaskManager() {}

    public int addNewTask(Task task) {
        final int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        final int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

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
        updateEpicStatusByEpicId(epicId);
        return id;
    }

    // Получение списка всех задач
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> tasks = new ArrayList<>(subtasks.values());
        return tasks;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> tasks = new ArrayList<>(epics.values());
        return tasks;
    }

    // Удаление всех задач
    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteAllEpicsAndSubtasks() {
        epics.clear();
        subtasks.clear();
    }

    // Получение по идентификатору
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    // Обновление
    public void updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
    }

    // Удаление по идентификатору
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Получение списка всех подзадач определенного эпика
    public void printSubtasksOfEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            System.out.println("Эпик " + epic.getName() + ":");
            for (Subtask subtask : epic.getSubtasks()) {
                System.out.println("  Подзадача " + subtask.getName());
            }
        } else {
            System.out.println("Эпик с ID " + epicId + " не найден.");
        }
    }

    public void updateEpicStatusByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic!= null) {
            epic.updateEpicStatus();
        }
    }
}
