import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

import model.Task;
import model.Epic;
import model.Subtask;
import controllers.FileBackedTaskManager;

public class FileBackedTaskManagerTests {
    private FileBackedTaskManager fileBackedTaskManager;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".json");
        fileBackedTaskManager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void testSavingAndLoadingEmptyFile() throws IOException {
        // Сохраняем пустой файл
        fileBackedTaskManager.save();

        // Загружаем файл
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем, что после загрузки файл остается пустым
        assertTrue(loadedManager.getTasks().isEmpty());
    }

    @Test
    public void testSavingMultipleTasks() throws IOException {
        // Добавляем несколько задач
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        fileBackedTaskManager.addNewTask(task1);
        fileBackedTaskManager.addNewTask(task2);

        // Сохраняем файл
        fileBackedTaskManager.save();

        // Загружаем файл
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем, что все задачи были сохранены и загружены
        assertEquals(2, loadedManager.getTasks().size());
        assertTrue(loadedManager.getTasks().contains(task1));
        assertTrue(loadedManager.getTasks().contains(task2));
    }

    @Test
    public void testLoadingMultipleTasks() throws IOException {
        // Добавляем несколько задач
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        fileBackedTaskManager.addNewTask(task1);
        fileBackedTaskManager.addNewTask(task2);

        // Сохраняем файл
        fileBackedTaskManager.save();

        // Загружаем файл
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем, что все задачи были сохранены и загружены
        assertEquals(2, loadedManager.getTasks().size());
        assertTrue(loadedManager.getTasks().contains(task1));
        assertTrue(loadedManager.getTasks().contains(task2));
    }
}
