package manager_tests;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTests {
    private List<String> subtaskIds;
    //Экземпляры класса Task равны друг другу, если равен их id
    @Test
    void taskEqualsbySameId() {
        Task task1 = new Task("Test 1", "Описание 1", Status.NEW);
        Task task2 = new Task("Test 2", "Описание 2", Status.NEW);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1.getId(), task2.getId(), "Задачи должны быть равны по ID");
    }

    //Наследники класса Task равны друг другу, если равен их id
    @Test
    void epicEqualsSubtaskBySameId() {
        Epic epic = new Epic(1, "Epic Test", "Epic Description", Status.NEW, Arrays.asList());

        Subtask subtask = new Subtask(1, "Subtask Test", "Subtask Description", Status.NEW, 1);
        epic.setId(1);
        subtask.setId(1);

        assertEquals(epic.getId(), subtask.getId(), "Наследники должны быть равны по ID");
    }
}