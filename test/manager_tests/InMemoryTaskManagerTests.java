package manager_tests;

import controllers.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTests {

    private InMemoryTaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }
    //InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    public void testAddAndGetTasksByType() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Status.IN_PROGRESS);
        Epic epic = new Epic(3, "Epic", "Epic Description", Status.DONE, Arrays.asList());
        Subtask subtask = new Subtask(4, "Subtask", "Subtask Description", Status.NEW, 7);

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic);
        manager.addSubTask(subtask);

        assertEquals(2, manager.tasks.size());
        assertEquals(1, manager.epics.size());
        assertEquals(1, manager.subtasks.size());

        assertSame(task1, manager.getTask(5));
        assertSame(task2, manager.getTask(6));
        assertSame(epic, manager.getEpic(7));
        assertSame(subtask, manager.getSubTask(8));
    }

    //что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
    @Test
    public void testNoConflictBetweenKnownAndGeneratedId() {

        Task taskWithId = new Task(1, "Task with Id", "Description", Status.NEW);
        Task generatedTask = new Task(54, "Generated Task", "Description", Status.NEW);

        manager.addTask(taskWithId);
        manager.addTask(generatedTask);

        assertEquals(2, manager.tasks.size());
        assertNotNull(manager.getTask(1));
        assertNotNull(manager.getTask(2));

        assertNotEquals(taskWithId, manager.getTask(2));
        assertNotEquals(generatedTask, manager.getTask(1));
    }

    @Test
    public void testSubtaskHasEpic() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        Subtask subtask = new Subtask(2, "Подзадача", "Описание подзадачи", Status.NEW, 3);
        manager.addEpic(epic);
        manager.addSubTask(subtask);

        boolean hasEpic = (subtask.getIdEpic()==3);
        assertTrue(hasEpic, "Подзадача должна иметь связанный эпик");
    }

    @Test
    public void testIntersectionOfIntervals() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Status.NEW,
                LocalDateTime.of(2023, 1, 1, 9, 0),
                Duration.ofHours(3));

        Task task2 = new Task(2, "Задача 2", "Описание 2", Status.IN_PROGRESS,
                LocalDateTime.of(2023, 1, 1, 10, 30),
                Duration.ofHours(4));

        Task task3 = new Task(3, "Задача 3", "Описание 3", Status.DONE,
                LocalDateTime.of(2023, 1, 1, 22, 0),
                Duration.ofHours(2));

        // Добавляем задачи в менеджер
        try {
            manager.addTask(task1); // Эта задача должна быть добавлена без ошибок
            assertTrue(true, "Задача 1 успешно добавлена");
        } catch (Exception e) {
            fail("Ошибка при добавлении задачи 1: " + e.getMessage());
        }

        try {
            manager.addTask(task2); // Эта задача должна вызвать исключение
            fail("Не было ожидаемого исключения при добавлении задачи 2");
        } catch (Exception e) {
            assertTrue(true, "Задача 2 не была добавлена из-за пересечения");
        }

        try {
            manager.addTask(task3); // Эта задача должна быть добавлена без ошибок
            assertTrue(true, "Задача 3 успешно добавлена");
        } catch (Exception e) {
            fail("Ошибка при добавлении задачи 3: " + e.getMessage());
        }
    }
}