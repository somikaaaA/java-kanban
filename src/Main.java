import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = TaskManager.getInstance();

        // Создание задач, эпиков и подзадач, добавление их в список и вывод всех задач
        Task task1 = new Task("Название задачи 1", "Описание задачи 1");
        Task task2 = new Task("Название задачи 2", "Описание задачи 2");
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Название Эпика 1", "Описание Эпика 1");
        manager.addTask(epic1);

        Subtask subtask1 = new Subtask("Название Подзадачи 1", "Описание Подзадачи 1", epic1);
        Subtask subtask2 = new Subtask("Название Подзадачи 2", "Описание Подзадачи 2", epic1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        Epic epic2 = new Epic("Название Эпика 2", "Описание Эпика 2");
        manager.addTask(epic2);

        Subtask subtask3 = new Subtask("Название Подзадачи 4", "Описание Подзадачи 4", epic2);
        Subtask subtask4 = new Subtask("Название Подзадачи 5", "Описание Подзадачи 5", epic2);
        epic2.addSubtask(subtask3);
        epic2.addSubtask(subtask4);

        manager.printAllTasks();

        // Обновление одной из задач
        task1.setName("Новое название задачи 1");
        manager.updateTask(task1);

        // Получение задачи по идентификатору
        Task foundTask = manager.getTaskById(task1.getId());
        System.out.println("\nНайденная задача: " + foundTask);

        // Обновление статусов подзадач, чтобы статус эпика тоже изменился
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        epic1.updateEpicStatus();

        // Удаление одной задачи по идентификатору
        manager.removeTask(task2.getId());

        // Проверка, что произошло обновление одной из задач,
        // обновление статуса эпика, удаление одной задачи по идентификатору
        manager.printAllTasks();

        // Удаление всех задач
        manager.deleteAllTasks();

        // Проверка, что все задачи были удалены
        manager.printAllTasks();
    }
}