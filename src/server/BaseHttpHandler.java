package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import controllers.Managers;
import controllers.TaskManager;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler {
    public static final Charset UTF = StandardCharsets.UTF_8;
    public Gson gson;
    public TaskManager taskManager;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = Managers.createGson();
    }

    public void writeResponse(HttpExchange exchange,
                              String responseText, int responseCode) throws IOException {
        if (responseText.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseText.getBytes(UTF);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    public Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getQuery().split("=");
        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public Gson getGson() {
        return gson;
    }
}