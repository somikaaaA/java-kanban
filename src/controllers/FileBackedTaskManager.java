package controllers;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Task;
import model.Epic;
import model.Subtask;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile(file);
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
            throw new ManagerSaveException("Ошибка при сохранении в файл", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            manager.loadFromFile(file); // Загружаем данные из файла
        } catch (ManagerSaveException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + e.getMessage());
        }
        return manager;
    }



    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        Integer result = super.addNewSubtask(subtask);
        save();
        return result;
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }
}
