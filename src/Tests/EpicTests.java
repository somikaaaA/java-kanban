package Tests;

import model.Epic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EpicTests {

    //Объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void testAddSelfAsSubtask() {
        // Создаем объект Epic
        Epic epic = new Epic("Тестовый Эпик", "Описание Тестового Эпика");

        // Пытаемся добавить Epic в качестве подзадачи к самому себе
        try {
            epic.addSubtaskId(epic.getId()); // Предполагается, что метод addSubtaskId существует и доступен
            fail("Ожидалось исключение при попытке добавить Epic в качестве подзадачи к самому себе.");
        } catch (Exception e) {
            // Ожидаемое исключение, проверяем тип и сообщение
            assertInstanceOf(IllegalArgumentException.class, e, "Ожидалось IllegalArgumentException");
            assertEquals("Epic не может быть добавлен в качестве подзадачи к самому себе.", e.getMessage());
        }

        // Проверяем, что подзадача не была добавлена
        assertFalse(epic.getSubtaskIds().contains(epic.getId()));
    }
}
