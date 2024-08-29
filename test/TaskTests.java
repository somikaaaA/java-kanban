
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTests {
    private List<String> subtaskIds;
    //Экземпляры класса Task равны друг другу, если равен их id
    @Test
    void taskEqualsbySameId() {
        Task task1 = new Task("Test 1", "Описание 1", Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 30, 10, 0));
        Task task2 = new Task("Test 2", "Описание 2", Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 31, 10, 0));
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1.getId(), task2.getId(), "Задачи должны быть равны по ID");
    }

    //Наследники класса Task равны друг другу, если равен их id
    @Test
    void epicEqualsSubtaskBySameId() {
        Epic epic = new Epic("Epic Test", "Epic Description", Arrays.asList());

        Subtask subtask = new Subtask("Subtask Test", "Subtask Description", Duration.ofMinutes(10), LocalDateTime.of(2024, 8, 29, 10, 0), 1);
        epic.setId(1);
        subtask.setId(1);

        assertEquals(epic.getId(), subtask.getId(), "Наследники должны быть равны по ID");
    }
}
