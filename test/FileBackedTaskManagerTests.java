import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

import model.Task;

import controllers.FileBackedTaskManager;

public class FileBackedTaskManagerTests {
    private FileBackedTaskManager manager;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = File.createTempFile("test", ".csv");
        manager = new FileBackedTaskManager(testFile);
    }

    @Test
    void shouldLoadEmptyFileCorrectly() throws IOException {
        // Проверяем, что пустой файл корректно загружается
        manager.loadFromFile(testFile);

        assertEquals(Collections.emptyMap(), manager.tasks);
        assertEquals(Collections.emptyMap(), manager.epics);
        assertEquals(Collections.emptyMap(), manager.subtasks);
    }

    @Test
    void shouldWriteAndReadTasks() throws IOException {
        Task task = new Task(1,"Name", "Description", Status.NEW);
        manager.addTask(task);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(testFile);
        Task loadedTask = loadedManager.getTask(task.getId());

        assertEquals(task, loadedTask);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFile.getAbsolutePath()));
    }
}