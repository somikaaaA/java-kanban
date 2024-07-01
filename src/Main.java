import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = TaskManager.getInstance();

        // Создание задач
        Task task1 = new Task("Название задачи 1", "Описание задачи 1");
        Task task2 = new Task("Название задачи 2", "Описание задачи 2");

        // Добавление задач
        manager.addTask(task1);
        manager.addTask(task2);

        // Вывод списка всех задач
        System.out.println("Список всех задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        // Создание эпика
        Epic epic = new Epic("Название Эпика", "Описание Эпика");

        // Добавление эпика
        manager.addTask(epic);

        // Вывод списка всех задач (включая эпик)
        System.out.println("\nСписок всех задач (включая эпик):");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        // Создание подзадачи
        Subtask subtask = new Subtask("Название Подзадачи", "Описание Подзадачи", epic);

        // Добавление подзадачи к эпику
        epic.addSubtask(subtask);

        // Вывод списка всех подзадач определенного эпика
        System.out.println("\nСписок всех подзадач определенного эпика:");
        List<Subtask> subtasks = epic.getSubtasks();
        for (Subtask sub : subtasks) {
            System.out.println(sub);
        }

        // Обновление статуса задачи
        int indexToUpdate1 = 1; // Индекс задачи, которую мы хотим обновить
        task1.setStatus(Status.IN_PROGRESS);
        System.out.println("\nСтатус задачи №" + (indexToUpdate1) + " обновлен до: " + task1.getStatus());

        int indexToUpdate2 = 2; // Индекс задачи, которую мы хотим обновить
        task2.setStatus(Status.DONE);
        System.out.println("\nСтатус задачи №" + (indexToUpdate2) + " обновлен до: " + task1.getStatus());

        // Удаление задачи по идентификатору
        manager.removeTask(task2.getId());
        System.out.println("\nЗадача с ID " + task2.getId() + " удалена.");

        // Попытка удаления несуществующей задачи
        manager.removeTask(task1.getId());
        System.out.println("\nПопытка удаления несуществующей задачи с ID " + task1.getId() + ". Результат: " + (manager.getTaskById(task1.getId()) == null));
    }
}

