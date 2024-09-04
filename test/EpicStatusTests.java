import controllers.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicStatusTests {
    private InMemoryTaskManager manager = new InMemoryTaskManager();

    @Test
    void testEpicStatusAllSubtasksNew() {
        Epic epic = new Epic(1, "Epic Test", "Описание эпика", Status.NEW, Arrays.asList());
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", Status.NEW, epicId);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.NEW, epicId);
        manager.addSubTask(subtask2);
        // Act
        manager.updateEpicStatus(epicId);

        // Assert
        assertEquals(Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void testEpicStatusAllSubtasksDone() {
        Epic epic = new Epic(1, "Epic Test", "Описание эпика", Status.NEW, Arrays.asList());
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", Status.DONE, epicId);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.DONE, epicId);
        manager.addSubTask(subtask2);
        // Act
        manager.updateEpicStatus(epicId);

        // Assert
        assertEquals(Status.DONE, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void testEpicStatusAllSubtasksNewAndDone() {
        Epic epic = new Epic(1, "Epic Test", "Описание эпика", Status.NEW, Arrays.asList());
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", Status.NEW, epicId);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.DONE, epicId);
        manager.addSubTask(subtask2);
        // Act
        manager.updateEpicStatus(epicId);

        // Assert
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void testEpicStatusAllSubtasksInProgress() {
        Epic epic = new Epic(1, "Epic Test", "Описание эпика", Status.NEW, Arrays.asList());
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", Status.IN_PROGRESS, epicId);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.IN_PROGRESS, epicId);
        manager.addSubTask(subtask2);
        // Act
        manager.updateEpicStatus(epicId);

        // Assert
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть NEW");
    }
}
