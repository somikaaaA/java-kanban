package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.TaskManager;
import java.io.IOException;
import java.util.List;
import model.Task;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response;

        if ("GET".equals(method)) {
            List<Task> history = taskManager.getHistory();
            response = gson.toJson(history);
            writeResponse(exchange, response, 200);
        } else {
            writeResponse(exchange, "Метод не поддерживается", 405);
        }
    }
}