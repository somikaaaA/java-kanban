package controllers;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    // Получение списка всех задач
    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    // Удаление всех задач
    void deleteTasks();

    void deleteAllEpicsAndSubtasks();

    void deleteSubtasks();

    // Получение по идентификатору
    Task getTask(int id);

    Task getSubtask(int id);

    Task getEpic(int id);

    // Обновление
    void updateTask(Task updatedTask);

    // Удаление по идентификатору
    void deleteTaskById(int id);

    // Получение списка всех подзадач определенного эпика
    ArrayList<Subtask> getEpicSubtasks(int epicId);

    void updateEpicStatus(int epicId);

    ArrayList<Task> getHistory();

    boolean isSelfEpic(Subtask subtask);
}