import controllers.TaskManager;
import controllers.Managers;
import controllers.HistoryManager;
import model.Status;
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
}