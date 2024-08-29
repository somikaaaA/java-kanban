import controllers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = createInstanceUnderTest();
    }

    protected abstract T createInstanceUnderTest();

    @Test
    public void testCalculateEpicStatus() {
        // Тесты для различных комбинаций статусов подзадач
        testAllSubtasksNew();
        testAllSubtasksDone();
        testMixedSubtasksStatuses();
        testInProgressSubtasks();
    }

    protected void testAllSubtasksNew() {
        // Реализация для InMemoryTaskManager и FileBackedTaskManager
    }

    protected void testAllSubtasksDone() {
        // Реализация для InMemoryTaskManager и FileBackedTaskManager
    }

    protected void testMixedSubtasksStatuses() {
        // Реализация для InMemoryTaskManager и FileBackedTaskManager
    }

    protected void testInProgressSubtasks() {
        // Реализация для InMemoryTaskManager и FileBackedTaskManager
    }
}