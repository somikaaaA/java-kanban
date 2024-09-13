package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import controllers.InMemoryTaskManager;
import controllers.Managers;
import controllers.TaskManager;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    public TaskManager taskManager;
    public HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = new InMemoryTaskManager();

        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.createContext("/epics", new EpicHandler(taskManager));
        httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
        httpServer.createContext("/history", new HistoryHandler(taskManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));
    }

    public static void main(String[] args) {
        try {
            HttpTaskServer server = new HttpTaskServer(new InMemoryTaskManager());
            server.start();
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер");
        }
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту");
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("HTTP-сервер остановлен");
    }
}