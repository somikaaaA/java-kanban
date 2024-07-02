import controllers.TaskManager;
import model.Task;
import model.Subtask;
import model.Epic;
import model.Status;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        // Добавление задач в систему
        manager.addNewTask(task1);
        manager.addNewTask(task2);

        // Получение всех задач
        System.out.println("Список задач:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }

        // Создание эпика
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");

        // Добавление эпика в систему
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);

        // Получение всех эпиков
        System.out.println("\nСписок эпиков:");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
        }

        // Создание подзадачи
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic2);
        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", epic2);

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        manager.addNewSubtask(subtask4);

        // Получение всех подзадач
        System.out.println("\nСписок подзадач:");
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        // Получение задачи по ID
        Task retrievedTask = manager.getTaskById(task1.getId());
        System.out.println("\nЗадача с ID " + task1.getId() + " получена: " + retrievedTask);

        // Обновление задачи
        task1.setName("Обновленная задача 1");
        manager.updateTask(task1);
        System.out.println("Задача с ID " + task1.getId() + " обновлена до: " + task1);

        // Удаление задачи по ID
        manager.deleteTaskById(task2.getId());
        System.out.println("Задача с ID " + task2.getId() + " удалена.");

        // Обновление статуса подзадач на DONE
        subtask3.setStatus(Status.DONE);
        subtask4.setStatus(Status.DONE);
        // Обновление статуса эпика по его ID
        manager.updateEpicStatusByEpicId(epic1.getId());
        System.out.println("Статус эпика с ID: " + epic1.getId() + " обновлен.");
        // Вывод статуса эпика
        System.out.println("Статус эпика с ID: " + epic1.getId() + ": " + epic1.getStatus());

        // Удаление всех задач
        manager.deleteTasks();
        System.out.println("\nВсе задачи были удалены");

        // Удаление всех эпиков и подзадач
        manager.deleteAllEpicsAndSubtasks();
        System.out.println("Все эпики и подзадачи удалены");

        // Проверяем, что все элементы были удалены
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }
    }
}
