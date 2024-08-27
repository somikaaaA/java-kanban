import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import model.Task;
import model.Epic;
import model.Subtask;

import controllers.FileBackedTaskManager;

public class FileBackedTaskManagerTests {
    private File tempFile;
    private FileBackedTaskManager fileBackedTaskManager;
    //private List<String> subtaskIds;
    private static final String TEST_DATA = "1,TASK,Task1,NEW,Description task1,\n" +
            "2,EPIC,Epic2,DONE,Description epic2,\n" +
            "3,SUBTASK,Sub Task3,DONE,Description sub task3,2";

    @BeforeEach
    void setUp() throws IOException{
        tempFile = File.createTempFile("temp", ".csv");
    }

    @AfterEach
    void tearDown() throws IOException {
        assertTrue(tempFile.delete());
    }

    @Test
    void saveAndLoadEmptyFile() throws IOException {

        fileBackedTaskManager = new FileBackedTaskManager(tempFile);

        assertTrue(tempFile.exists());
        assertEquals(0, tempFile.length());

        List<Object> loadedData = FileBackedTaskManager.loadFromFile(tempFile);

        assertNotNull(loadedData);
        assertTrue(loadedData.isEmpty());
    }

    @Test
    void saveDifferentTypesOfTasksToFile() throws IOException {

        Task task = new Task("Task Name", "Task Description", Duration.ZERO, LocalDateTime.MAX);
        Epic epic = new Epic("Epic Name", "Epic Description", Arrays.asList());
        Subtask subtask = new Subtask("Subtask Name", "Subtask Description", Duration.ZERO, LocalDateTime.MAX, 1);

        fileBackedTaskManager = new FileBackedTaskManager(tempFile);

        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpic(epic);
        fileBackedTaskManager.addNewSubtask(subtask);

        fileBackedTaskManager.save();

        assertTrue(tempFile.exists());
        long fileSize = tempFile.length();
        assertTrue(fileSize > 0);

        Scanner scanner = new Scanner(tempFile);
        int expectedLines = 3;
        int actualLines = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            actualLines++;
        }
        scanner.close();
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void testLoadFromFileWithDifferentTypesOfTasks() throws IOException {

        Files.writeString(tempFile.toPath(), TEST_DATA);

        fileBackedTaskManager = new FileBackedTaskManager(tempFile);

        List<Object> loadedData = FileBackedTaskManager.loadFromFile(tempFile);

        assertNotNull(loadedData);
        assertEquals(3, loadedData.size());

        assertTrue(loadedData.stream().anyMatch(data -> data instanceof Task));
        assertTrue(loadedData.stream().anyMatch(data -> data instanceof Epic));
        assertTrue(loadedData.stream().anyMatch(data -> data instanceof Subtask));

        Task loadedTask = (Task) loadedData.get(0);
        assertEquals("Task1", loadedTask.getName());
        assertEquals("Description task1", loadedTask.getDescription());
        assertEquals(Status.NEW, loadedTask.getStatus());

        Epic loadedEpic = (Epic) loadedData.get(1);
        assertEquals("Epic2", loadedEpic.getName());
        assertEquals("Description epic2", loadedEpic.getDescription());
        assertEquals(Status.DONE, loadedEpic.getStatus());

        Subtask loadedSubtask = (Subtask) loadedData.get(2);
        assertEquals("Sub Task3", loadedSubtask.getName());
        assertEquals("Description sub task3", loadedSubtask.getDescription());
        assertEquals(Status.DONE, loadedSubtask.getStatus());
        assertEquals(2, loadedSubtask.getEpicId()); // Проверяем, что подзадача связана с правильным эпиком
    }
}
