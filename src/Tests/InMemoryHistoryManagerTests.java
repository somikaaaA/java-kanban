package Tests;

import controllers.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryHistoryManagerTests {

    //добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    public void testSavingPreviousVersionOfTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task originalTask = new Task("Оригинальная задача", "Описание задачи");
        originalTask.setId(1); // Установка ID для теста

        historyManager.add(originalTask);

        originalTask.setName("Измененное имя");
        originalTask.setDescription("Измененное описание");

        historyManager.add(originalTask);

        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertEquals(2, history.size(), "В истории должно быть две версии задачи.");
        assertTrue(history.contains(originalTask), "Оригинальная версия задачи должна быть в истории.");
        assertTrue(history.contains(new Task("Оригинальная задача", "Описание задачи")), "Первая версия задачи должна быть в истории.");
    }
}