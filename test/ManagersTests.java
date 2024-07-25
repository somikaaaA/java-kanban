import controllers.TaskManager;
import controllers.Managers;
import controllers.HistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTests {
    //утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    @Test
    public void testManagersReturnInitializedInstances() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач должен быть проинициализирован.");

        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "История задач должна быть проинициализирована.");
    }

    @Test
    public void testSetterImpactOnDataManager() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Задача 1", "Описание задачи 1");
        int taskId = manager.addNewTask(task);

        // Изменение через сеттеры
        task.setName("Измененная задача");
        task.setDescription("Новое описание задачи");

        // Проверка, что изменения отражены в менеджере
        Task updatedTask = manager.getTask(taskId);
        assertEquals("Измененная задача", updatedTask.getName());
        assertEquals("Новое описание задачи", updatedTask.getDescription());
    }

}
