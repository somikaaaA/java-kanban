import controllers.HistoryManager;
import controllers.Managers;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTests {
    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
        task = new Task(1, "Название задачи", "Описание задачи", Status.NEW);
    }

    //добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    public void testSavingPreviousVersionOfTask() {
        historyManager.add(task);

        task.setName("Измененное имя");
        task.setDescription("Измененное описание");

        historyManager.add(task);

        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertEquals(1, history.size(), "В истории должна быть одна версия задачи.");
    }

    @Test
    public void testRemovingTaskFromHistory() {
        historyManager.add(task);

        historyManager.add(task);

        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertEquals(1, history.size(), "В истории должна быть одна версия задачи.");

        historyManager.remove(1);

        history = (ArrayList<Task>) historyManager.getHistory();

        assertTrue(history.isEmpty(), "История должна быть пустой после удаления задачи");
    }

    @Test
    public void testAddingMultipleTasksToHistory() {
        historyManager.add(task);

        Task anotherTask = new Task(2, "Новое название задачи", "Новое описание задачи", Status.IN_PROGRESS);
        historyManager.add(anotherTask);

        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertEquals(2, history.size(), "В истории должны быть две версии задачи.");
        assertEquals(task, history.get(0), "Первая задача сохранена в истории");
        assertEquals(anotherTask, history.get(1), "Вторая задача сохранена в истории");
    }

    @Test
    public void testEmptyHistoryAtStart() {
        ArrayList<Task> history = (ArrayList<Task>) historyManager.getHistory();

        assertTrue(history.isEmpty(), "История должна быть пустой при запуске");
    }
}