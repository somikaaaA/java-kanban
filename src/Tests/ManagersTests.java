package Tests;

import controllers.HistoryManager;
import controllers.Managers;
import controllers.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagersTests {

    //утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    @Test
    public void testManagersReturnInitializedInstances() {
        // Проверка, что getDefault() возвращает не-null экземпляр TaskManager
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач должен быть проинициализирован.");

        // Проверка, что getDefaultHistory() возвращает не-null экземпляр HistoryManager
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "История задач должна быть проинициализирована.");
    }
}
