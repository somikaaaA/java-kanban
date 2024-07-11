package Tests;

import controllers.HistoryManager;
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
        // Инициализация HistoryManager
        HistoryManager historyManager = new InMemoryHistoryManager();

        // Создание первой версии задачи
        Task originalTask = new Task("Оригинальная задача", "Описание задачи");

        // Добавление задачи в историю
        historyManager.add(originalTask);

        // Изменение данных задачи
        originalTask.setName("Измененное имя");
        originalTask.setDescription("Измененное описание");

        // Повторное добавление задачи в историю
        historyManager.add(originalTask);

        // Получение истории
        ArrayList<Task> history = historyManager.getHistory();

        // Проверка, что в истории присутствуют обе версии задачи
        assertEquals(2, history.size(), "В истории должно быть две версии задачи.");
        assertTrue(history.contains(originalTask), "Оригинальная версия задачи должна быть в истории.");
        assertTrue(history.contains(new Task("Оригинальная задача", "Описание задачи")), "Первая версия задачи должна быть в истории.");
    }
}
