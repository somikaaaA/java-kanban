package controllers;

import java.util.ArrayList;
import java.util.List;
import model.Epic;
import model.Subtask;
import model.Task;

public interface TaskManager {
    List<Task> getHistory();

    ArrayList<Task> getTasks();

    ArrayList<Task> getEpics();

    ArrayList<Task> getSubtasks();

    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubTask(Subtask subtask);

    Task getTask(int idTask);

    Epic getEpic(int idTask);

    Subtask getSubTask(int idTask);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subTask);

    void updateTask(Task task);

    void deleteTask(int idTask);

    void deleteEpic(int idTask);

    void deleteSubtask(int id);

    List<Subtask> getTasks(int epicId);

    List<Task> getPrioritizedTasks();
}