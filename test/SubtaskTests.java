
import controllers.InMemoryTaskManager;
import model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubtaskTests {
    //объект Subtask нельзя сделать своим же эпиком
    @Test
    public void testCannotMakeSubtaskItsOwnEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Тестовая подзадача", "Описание", 1);

        assertFalse(manager.isSelfEpic(subtask), "Subtask не должен иметь возможность стать своим эпиком.");
    }
}
