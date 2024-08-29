
import controllers.InMemoryTaskManager;
import model.Subtask;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubtaskTests {
    //объект Subtask нельзя сделать своим же эпиком
    @Test
    public void testCannotMakeSubtaskItsOwnEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Тестовая подзадача", "Описание", Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 30, 10, 0), 1);

        assertFalse(manager.isSelfEpic(subtask), "Subtask не должен иметь возможность стать своим эпиком.");
    }
}