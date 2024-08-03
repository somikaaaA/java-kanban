import controllers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EpicTests {
    //Объект Epic нельзя добавить в самого себя в виде подзадачи;
    private InMemoryTaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void testAddSubtaskToEpicThatIsItself() {
        Epic epic = new Epic("Test Epic", "Description", null);
        Subtask subtask = new Subtask("Test Subtask", "Description", epic.getId());
        assertThrows(IllegalArgumentException.class, () -> taskManager.addNewSubtask(subtask));
    }
}