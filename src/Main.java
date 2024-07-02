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

        Epic epic = new Epic("Название Эпика", "Описание Эпика");
        manager.addTask(epic);

        Subtask subtask1 = new Subtask("Название Подзадачи 1", "Описание Подзадачи 1", epic);
        Subtask subtask2 = new Subtask("Название Подзадачи 2", "Описание Подзадачи 2", epic);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        manager.printAllTasks();

        // Обновление одной из задач
        task1.setName("Новое название задачи 1");
        manager.updateTask(task1);

        // Получение задачи по идентификатору
        Task foundTask = manager.getTaskById(task1.getId());
        System.out.println("\nНайденная задача: " + foundTask);

        // Обновление статусов подзадач, чтобы статус эпика тоже изменился
        List<Subtask> updatedSubtasks = Arrays.asList(
                new Subtask("Измененное название Подзадачи 1", "Измененное описание Подзадачи 1", epic),
                new Subtask("Новая Подзадача", "Описание новой подзадачи", epic)
        );
        epic.setSubtasks(updatedSubtasks);

        // Удаление одной задачи по идентификатору
        manager.removeTask(task2.getId());

        // Удаление всех задач
        manager.deleteAllTasks();

        // Проверка, что все задачи были удалены
        manager.printAllTasks();
    }
}
