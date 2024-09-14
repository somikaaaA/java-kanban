package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;

public class Managers {
    public static TaskManager getDefault() {
        try {
            Path file = Path.of("src/resources/savedTask.csv");
            Path dir = Path.of("src/resources");
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
                Files.createFile(file);
            }
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            return new FileBackedTaskManager(file.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public static Gson createGson() {
        return GSON;
    }
}