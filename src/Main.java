import controllers.InMemoryTaskManager;
import controllers.TaskManager;
import controllers.Managers;

import model.Task;
import model.Subtask;
import model.Epic;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        // Добавление задач в систему
        manager.addNewTask(task1);
        manager.addNewTask(task2);

        // Создание эпика с тремя подзадачами
        Epic epicWithSubtasks = new Epic("Эпик с подзадачами", "Описание эпика с подзадачами");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 3);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 3);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 3);
        epicWithSubtasks.addSubtaskId(subtask1.getId());
        epicWithSubtasks.addSubtaskId(subtask2.getId());
        epicWithSubtasks.addSubtaskId(subtask3.getId());

        // Добавление эпика в систему
        manager.addNewEpic(epicWithSubtasks);

        // Создание эпика без подзадач
        Epic epicWithoutSubtasks = new Epic("Эпик без подзадач", "Описание эпика без подзадач");
        manager.addNewEpic(epicWithoutSubtasks);

        // Запросы к системе
        manager.getTask(1);
        manager.getTask(2);
        manager.getTask(1);
        manager.getEpic(1);
        manager.getEpic(2);
        manager.getSubtask(1);
        manager.getSubtask(2);
        manager.getSubtask(3);

        // Печать истории после каждого запроса
        printAllTasks(manager);
        printAllTasks(manager);
        printAllTasks(manager);
        printAllTasks(manager);
        printAllTasks(manager);
        printAllTasks(manager);
        printAllTasks(manager);

        // Удаление задачи из истории
        manager.deleteTaskById(1);
        printAllTasks(manager);

        // Удаление эпика с подзадачами
        manager.deleteEpicById(1);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
