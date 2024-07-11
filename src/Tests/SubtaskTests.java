package Tests;

import controllers.Managers;
import controllers.TaskManager;
import model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubtaskTests {

    //объект Subtask нельзя сделать своим же эпиком
    @Test
    public void testCannotMakeSubtaskItsOwnEpic(){
        TaskManager manager = Managers.getDefault();
        Subtask subtask = new Subtask("Тестовая подзадача", "Описание", 1);
        // Действие
        boolean result = manager.isSelfEpic(subtask);

        // Проверка
        assertFalse(result, "Subtask не должен иметь возможность стать своим эпиком.");
    }
}
