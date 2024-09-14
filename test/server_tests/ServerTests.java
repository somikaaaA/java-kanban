package server_tests;

import com.google.gson.Gson;
import controllers.InMemoryTaskManager;
import controllers.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.BaseHttpHandler;
import server.HttpTaskServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {
    TaskManager taskManager = new InMemoryTaskManager();

    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);

    BaseHttpHandler baseHttpHandler = new BaseHttpHandler(taskManager);

    Gson gson = baseHttpHandler.getGson();

    public ServerTests() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        taskManager.deleteTask(1);
        taskManager.deleteEpic(1);
        taskManager.deleteSubtask(1);
        httpTaskServer.start();
    }

    @AfterEach
    public void shutDown(){
        httpTaskServer.stop();
    }

    @Test
    public void testAddTAsk() throws IOException, InterruptedException {
        Task task = new Task(1, "Task1", "Description1",
                Status.NEW, LocalDateTime.now(), Duration.ofMinutes(10));

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        Epic epic = new Epic(1, "Epic1", "Описание эпика", Status.NEW,
                new ArrayList<>(), LocalDateTime.now(), Duration.ofMinutes(10), LocalDateTime.now().plusMinutes(10));
        String json = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertNotNull(taskManager, "Список пуст");
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic(1, "Epic1", "Описание эпика", Status.NEW,
                new ArrayList<>(), LocalDateTime.now(), Duration.ofMinutes(10), LocalDateTime.now().plusMinutes(10));
        taskManager.addEpic(epic);
        Subtask subTask = new Subtask(2, "Описание", "Подзадача",
                Status.NEW, epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        String json = gson.toJson(subTask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertNotNull(taskManager, "Список пуст");
    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {
        Epic epic = new Epic(1, "Эпик", "Описание эпика", Status.NEW,
                new ArrayList<>(), LocalDateTime.now(), Duration.ofMinutes(10), LocalDateTime.now().plusMinutes(10));
        String epicJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlForDelete = URI.create("http://localhost:8080/epics/1");
        HttpRequest requestForDelete = HttpRequest.newBuilder()
                .uri(urlForDelete)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestForDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Задача удалена!", responseDelete.body());
    }

    @Test
    public void testDeleteSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic(1, "Epic1", "Описание эпика", Status.NEW,
                new ArrayList<>(), LocalDateTime.now(), Duration.ofMinutes(10), LocalDateTime.now().plusMinutes(10));
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask(2, "Описание", "Подзадача 1",
                Status.NEW, epic.getId(), Duration.ofMinutes(30), LocalDateTime.now());
        String json = gson.toJson(subtask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlForDelete = URI.create("http://localhost:8080/tasks/1");
        HttpRequest requestForDelete = HttpRequest.newBuilder()
                .uri(urlForDelete)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestForDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Задача удалена!", responseDelete.body());
    }
}