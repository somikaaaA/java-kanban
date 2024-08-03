import controllers.TaskManager;
import controllers.Managers;

import model.Task;
import model.Subtask;
import model.Epic;

import java.util.List;

public class Main {
    private static List<String> subtaskIds;

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        // Добавление задач в систему
        manager.addNewTask(task1);
        manager.addNewTask(task2);

        // Создание эпика
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", subtaskIds);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", subtaskIds);

        // Добавление эпика в систему
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);

        // Создание подзадачи
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 3);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 3);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 4);
        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", 4);

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        manager.addNewSubtask(subtask4);


        // Вызов методов и печать истории
        printAllTasks(manager);
        manager.getTask(1);
        printAllTasks(manager);
        manager.getSubtask(1);
        printAllTasks(manager);
        manager.getEpic(1);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);
            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
