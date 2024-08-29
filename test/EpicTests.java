import controllers.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Epic epic = new Epic("Test Epic", "Description", Arrays.asList());
        Subtask subtask = new Subtask("Test Subtask", "Description", Duration.ofMinutes(15), LocalDateTime.now(), 1);
        Integer result = taskManager.addNewSubtask(subtask);
        assertEquals(null, result);
        //assertThrows(IllegalArgumentException.class, () -> taskManager.addNewSubtask(subtask));
    }
}