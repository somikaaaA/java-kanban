package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Task;
import model.Epic;
import model.Subtask;

import controllers.InMemoryTaskManager;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            // Заголовок таблицы
            writer.write("id,type,name,status,description,epic\n");

            // Сохраняем задачи
            for (Task task : tasks.values()) {
                writer.write(task.toString() + "\n");
            }

            // Сохраняем подзадачи
            for (Subtask subtask : subtasks.values()) {
                writer.write(subtask.toString() + "\n");
            }

            // Сохраняем эпики
            for (Epic epic : epics.values()) {
                writer.write(epic.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении в файл", e);
        }
    }
}
