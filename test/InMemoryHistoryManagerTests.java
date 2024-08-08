import controllers.HistoryManager;
import controllers.Managers;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTests {
    //добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    public void testSavingPreviousVersionOfTask() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task originalTask = new Task("Оригинальная задача", "Описание задачи");
        originalTask.setId(1); // Установка ID для теста

        historyManager.add(originalTask);

        originalTask.setName("Измененное имя");
        originalTask.setDescription("Измененное описание");

        historyManager.add(originalTask);

        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertEquals(1, history.size(), "В истории должна быть одна версия задачи.");
    }
}