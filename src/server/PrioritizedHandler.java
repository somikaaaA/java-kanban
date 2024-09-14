package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.TaskManager;
import java.io.IOException;
import java.util.List;
import model.Task;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handle(exchange);
        String method = exchange.getRequestMethod();
        String response;

        if ("GET".equals(method)) {
            List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
            response = gson.toJson(prioritizedTasks);
            writeResponse(exchange, response, 200);
        } else {
            writeResponse(exchange, "Метод не поддерживается", 405);
        }
    }
}
