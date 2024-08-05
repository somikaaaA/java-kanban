package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import exceptions.ManagerSaveException;
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

            for (Task task : tasks.values()) {
                writer.write(task.toString() + "\n");
            }

            for (Subtask subtask : subtasks.values()) {
                writer.write(subtask.toString() + "\n");
            }

            for (Epic epic : epics.values()) {
                writer.write(epic.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении в файл", e);
        }
    }

    public static List<Object> loadFromFile(File file){
        List<Object> records = new ArrayList<>();
        try{
            String content = Files.readString(file.toPath());
            String[] lines = content.split("\n"); //делим содержимое файла на строки

            for (String line : lines){
                if (!line.isEmpty()){
                    String[] fields = line.split(","); //делим строки на поля

                    Object record = null;
                    switch (line.split(",")[1]) {
                        case "TASK":
                            record = Task.fromString(line);
                            break;
                        case "EPIC":
                            record = Epic.fromString(line);
                            break;
                        case "SUBTASK":
                            record = Subtask.fromString(line);
                            break;
                        default: continue;
                    }

                    if (record != null) {
                        records.add(record);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении данных из файла", e);
        }
        return records;
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
