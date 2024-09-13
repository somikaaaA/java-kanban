package manager_tests;

import controllers.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager>{
    protected T manager;

    protected TaskManagerTest(T manager) {
        this.manager = manager;
    }

    @Test
    void testGetHistory() {
        List<Task> history = manager.getHistory();
        assertNotNull(history, "История должна быть непустой");
        assertTrue(!history.isEmpty(), "История не должна быть пустой");
    }

    @Test
    void testGetTasks() {
        ArrayList<Task> tasks = manager.getTasks();
        assertNotNull(tasks, "Задачи должны быть непустыми");
        assertFalse(tasks.isEmpty(), "Задачи не должны быть пустыми");
    }

    @Test
    void testGetEpics() {
        ArrayList<Task> epics = manager.getEpics();
        assertNotNull(epics, "Эпикс не должны быть пустыми");
        assertFalse(epics.isEmpty(), "Эпикс не должны быть пустыми");
    }

    @Test
    void testGetSubTasks() {
        ArrayList<Task> subtasks = manager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не должны быть пустыми");
        assertFalse(subtasks.isEmpty(), "Подзадачи не должны быть пустыми");
    }

    @Test
    void testAddTask() {
        Task task = new Task(1, "Название задачи", "Описание задачи", Status.NEW);
        int taskId = manager.addTask(task);
        Task addedTask = manager.getTask(taskId);
        assertNotNull(addedTask, "Добавленная задача не должна быть null");
        assertEquals(taskId, addedTask.getId(), "ID задачи должно совпадать с добавленным ID");
        assertEquals(task.getName(), addedTask.getName(), "Название задачи должно совпадать");
        assertEquals(task.getDescription(), addedTask.getDescription(), "Описание задачи должно совпадать");
        assertEquals(task.getStatus(), addedTask.getStatus(), "Статус задачи должен совпадать");
    }

    @Test
    void testAddEpic() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        int epicId = manager.addEpic(epic);
        Epic addedEpic = manager.getEpic(epicId);
        assertNotNull(addedEpic, "Добавленный эпик не должен быть null");
        assertEquals(epicId, addedEpic.getId(), "ID эпика должно совпадать с добавленным ID");
        assertEquals(epic.getName(), addedEpic.getName(), "Название эпика должно совпадать");
        assertEquals(epic.getDescription(), addedEpic.getDescription(), "Описание эпика должно совпадать");
        assertEquals(epic.getStatus(), addedEpic.getStatus(), "Статус эпика должен совпадать");
    }

    @Test
    void testAddSubTask() {
        Subtask subtask = new Subtask(1, "Название подзадачи", "Описание подзадачи", Status.IN_PROGRESS, 1);
        int subtaskId = manager.addSubTask(subtask);
        Subtask addedSubtask = manager.getSubTask(subtaskId);
        assertNotNull(addedSubtask, "Добавленная подзадача не должна быть null");
        assertEquals(subtaskId, addedSubtask.getId(), "ID подзадачи должно совпадать с добавленным ID");
        assertEquals(subtask.getName(), addedSubtask.getName(), "Название подзадачи должно совпадать");
        assertEquals(subtask.getDescription(), addedSubtask.getDescription(), "Описание подзадачи должно совпадать");
        assertEquals(subtask.getStatus(), addedSubtask.getStatus(), "Статус подзадачи должен совпадать");
        assertEquals(subtask.getIdEpic(), addedSubtask.getIdEpic(), "ID эпика подзадачи должно совпадать");
    }

    @Test
    void testGetTaskById() {
        Task task = new Task(1, "Название задачи", "Описание задачи", Status.NEW);
        int taskId = manager.addTask(task);
        Task retrievedTask = manager.getTask(taskId);
        assertNotNull(retrievedTask, "Полученная задача не должна быть null");
        assertEquals(taskId, retrievedTask.getId(), "ID задачи должно совпадать");
        assertEquals(task.getName(), retrievedTask.getName(), "Название задачи должно совпадать");
        assertEquals(task.getDescription(), retrievedTask.getDescription(), "Описание задачи должно совпадать");
        assertEquals(task.getStatus(), retrievedTask.getStatus(), "Статус задачи должен совпадать");
    }

    @Test
    void testGetEpicById() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        int epicId = manager.addEpic(epic);
        Epic retrievedEpic = manager.getEpic(epicId);
        assertNotNull(retrievedEpic, "Полученный эпик не должен быть null");
        assertEquals(epicId, retrievedEpic.getId(), "ID эпика должно совпадать");
        assertEquals(epic.getName(), retrievedEpic.getName(), "Название эпика должно совпадать");
        assertEquals(epic.getDescription(), retrievedEpic.getDescription(), "Описание эпика должно совпадать");
        assertEquals(epic.getStatus(), retrievedEpic.getStatus(), "Статус эпика должен совпадать");
    }

    @Test
    void testGetSubTaskById() {
        Subtask subtask = new Subtask(1, "Название подзадачи", "Описание подзадачи", Status.IN_PROGRESS, 1);
        int subtaskId = manager.addSubTask(subtask);
        Subtask retrievedSubtask = manager.getSubTask(subtaskId);
        assertNotNull(retrievedSubtask, "Полученная подзадача не должна быть null");
        assertEquals(subtaskId, retrievedSubtask.getId(), "ID подзадачи должно совпадать");
        assertEquals(subtask.getName(), retrievedSubtask.getName(), "Название подзадачи должно совпадать");
        assertEquals(subtask.getDescription(), retrievedSubtask.getDescription(), "Описание подзадачи должно совпадать");
        assertEquals(subtask.getStatus(), retrievedSubtask.getStatus(), "Статус подзадачи должен совпадать");
        assertEquals(subtask.getIdEpic(), retrievedSubtask.getIdEpic(), "ID эпика подзадачи должно совпадать");
    }

    @Test
    void testUpdateEpic() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        int epicId = manager.addEpic(epic);

        // Создаем обновленную версию эпика
        Epic updatedEpic = new Epic(epicId, "Обновленное название эпика", "Обновленное описание эпика", Status.IN_PROGRESS, new ArrayList<>());

        // Обновляем эпик
        manager.updateEpic(updatedEpic);

        // Проверяем обновленный эпик
        Epic retrievedEpic = manager.getEpic(epicId);
        assertNotNull(retrievedEpic, "Полученный эпик не должен быть null");
        assertEquals(epicId, retrievedEpic.getId(), "ID эпика должно совпадать");
        assertEquals("Обновленное название эпика", retrievedEpic.getName(), "Название эпика должно быть обновлено");
        assertEquals("Обновленное описание эпика", retrievedEpic.getDescription(), "Описание эпика должно быть обновлено");
        assertEquals(Status.IN_PROGRESS, retrievedEpic.getStatus(), "Статус эпика должен быть обновлен");
    }

    @Test
    void testUpdateSubtask() {
        Subtask subtask = new Subtask(1, "Название подзадачи", "Описание подзадачи", Status.IN_PROGRESS, 1);
        int subtaskId = manager.addSubTask(subtask);

        // Создаем обновленную версию подзадачи
        Subtask updatedSubtask = new Subtask(subtaskId, "Обновленное название подзадачи", "Обновленное описание подзадачи", Status.DONE, 1);

        // Обновляем подзадачу
        manager.updateSubtask(updatedSubtask);

        // Проверяем обновленную подзадачу
        Subtask retrievedSubtask = manager.getSubTask(subtaskId);
        assertNotNull(retrievedSubtask, "Полученная подзадача не должна быть null");
        assertEquals(subtaskId, retrievedSubtask.getId(), "ID подзадачи должно совпадать");
        assertEquals("Обновленное название подзадачи", retrievedSubtask.getName(), "Название подзадачи должно быть обновлено");
        assertEquals("Обновленное описание подзадачи", retrievedSubtask.getDescription(), "Описание подзадачи должно быть обновлено");
        assertEquals(Status.DONE, retrievedSubtask.getStatus(), "Статус подзадачи должен быть обновлен");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task(1, "Название задачи", "Описание задачи", Status.NEW);
        int taskId = manager.addTask(task);

        // Удаляем задачу
        manager.deleteTask(taskId);

        // Проверяем, что задача была удалена
        Task deletedTask = manager.getTask(taskId);
        assertNull(deletedTask, "Удаленная задача не должна быть найдена");
    }

    @Test
    void testDeleteEpic() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        int epicId = manager.addEpic(epic);

        // Удаляем эпик
        manager.deleteEpic(epicId);

        // Проверяем, что эпик был удален
        Epic deletedEpic = manager.getEpic(epicId);
        assertNull(deletedEpic, "Удаленный эпик не должен быть найден");
    }

    @Test
    void testDeleteSubtask() {
        Subtask subtask = new Subtask(1, "Название подзадачи", "Описание подзадачи", Status.IN_PROGRESS, 1);
        int subtaskId = manager.addSubTask(subtask);

        // Удаляем подзадачу
        manager.deleteSubtask(subtaskId);

        // Проверяем, что подзадача была удалена
        Subtask deletedSubtask = manager.getSubTask(subtaskId);
        assertNull(deletedSubtask, "Удаленная подзадача не должна быть найдена");
    }

    @Test
    void testGetTasksByEpicId() {
        Epic epic = new Epic(1, "Название эпика", "Описание эпика", Status.DONE, new ArrayList<>());
        int epicId = manager.addEpic(epic);

        // Добавляем несколько подзадач к эпику
        Subtask subtask1 = new Subtask(1, "Подзадача 1", "Описание подзадачи 1", Status.IN_PROGRESS, epicId);
        Subtask subtask2 = new Subtask(2, "Подзадача 2", "Описание подзадачи 2", Status.DONE, epicId);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);

        // Получаем список подзадач по ID эпика
        List<Subtask> tasks = manager.getTasks(epicId);

        // Проверяем, что все подзадачи возвращены
        assertTrue(tasks.contains(subtask1), "Список должен содержать первую подзадачу");
        assertTrue(tasks.contains(subtask2), "Список должен содержать вторую подзадачу");
        assertEquals(2, tasks.size(), "Список должен содержать две подзадачи");
    }
}