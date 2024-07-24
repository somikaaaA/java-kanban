package tests;

import model.Epic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTests {

    //Объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void testAddSelfAsSubtask() {
        Epic epic = new Epic("Тестовый Эпик", "Описание Тестового Эпика");
        epic.setId(1); // Установка ID для теста
        assertFalse(epic.getSubtaskIds().contains(epic.getId()), "Epic не может содержать себя как подзадачу.");
    }
}
