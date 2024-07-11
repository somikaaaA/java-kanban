package controllers;

import controllers.HistoryManager;
import controllers.Managers;

import model.Task;
import model.Subtask;
import model.Epic;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager(); // Инициализация перед каждым тестом
    }

    //Экземпляры класса Task равны друг другу, если равен их id
    @Test
    void taskEqualsbySameId() {
        Task task1 = new Task("Test 1", "Description 1");
        Task task2 = new Task("Test 2", "Description 2");
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи должны быть равны по ID");
    }

    //Наследники класса Task равны друг другу, если равен их id
    @Test
    void epicEqualsSubtaskBySameId() {
        Epic epic = new Epic("Epic Test", "Epic Description");
        Subtask subtask = new Subtask("Subtask Test", "Subtask Description", 1);
        epic.setId(1);
        subtask.setId(1);

        assertEquals(epic, subtask, "Наследники должны быть равны по ID");
    }

    //Объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void testAddSelfAsSubtask() {
        // Создаем объект Epic
        Epic epic = new Epic("Тестовый Эпик", "Описание Тестового Эпика");

        // Пытаемся добавить Epic в качестве подзадачи к самому себе
        try {
            epic.addSubtaskId(epic.getId()); // Предполагается, что метод addSubtaskId существует и доступен
            fail("Ожидалось исключение при попытке добавить Epic в качестве подзадачи к самому себе.");
        } catch (Exception e) {
            // Ожидаемое исключение, проверяем тип и сообщение
            assertInstanceOf(IllegalArgumentException.class, e, "Ожидалось IllegalArgumentException");
            assertEquals("Epic не может быть добавлен в качестве подзадачи к самому себе.", e.getMessage());
        }

        // Проверяем, что подзадача не была добавлена
        assertFalse(epic.getSubtaskIds().contains(epic.getId()));
    }

    //объект Subtask нельзя сделать своим же эпиком
    @Test
    public void testCannotMakeSubtaskItsOwnEpic(){
        TaskManager manager = Managers.getDefault();
        Subtask subtask = new Subtask("Тестовая подзадача", "Описание", 1);
        // Действие
        boolean result = manager.isSelfEpic(subtask);

        // Проверка
        assertFalse(result, "Subtask не должен иметь возможность стать своим эпиком.");
    }

    //утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    @Test
    public void testManagersReturnInitializedInstances() {
        // Проверка, что getDefault() возвращает не-null экземпляр TaskManager
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач должен быть проинициализирован.");

        // Проверка, что getDefaultHistory() возвращает не-null экземпляр HistoryManager
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "История задач должна быть проинициализирована.");
    }

    //InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    public void testAddAndGetTasksByType() {
        // Добавляем задачу
        Task task = new Task("Задача 1", "Описание задачи 1");
        int taskId = taskManager.addNewTask(task);
        assertEquals(taskId, task.getId(), "ID задачи после добавления должен совпадать с заданным ID.");

        // Добавляем подзадачу
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1);
        int subtaskId = taskManager.addNewSubtask(subtask);
        assertEquals(subtaskId, subtask.getId(), "ID подзадачи после добавления должен совпадать с заданным ID.");

        // Добавляем эпик
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        int epicId = taskManager.addNewEpic(epic);
        assertEquals(epicId, epic.getId(), "ID эпика после добавления должен совпадать с заданным ID.");

        // Проверяем наличие задачи в коллекции задач
        ArrayList<Task> allTasks = taskManager.getTasks();
        assertTrue(allTasks.contains(task), "Задача должна быть найдена в коллекции задач.");

        // Проверяем наличие подзадачи в коллекции подзадач
        ArrayList<Subtask> allSubtasks = taskManager.getSubtasks();
        assertTrue(allSubtasks.contains(subtask), "Подзадача должна быть найдена в коллекции подзадач.");

        // Проверяем наличие эпика в коллекции эпиков
        ArrayList<Epic> allEpics = taskManager.getEpics();
        assertTrue(allEpics.contains(epic), "Эпик должен быть найден в коллекции эпиков.");
    }

    //что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
    @Test
    public void testNoConflictBetweenKnownAndGeneratedId() {
        // Инициализация менеджера
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        // Задача с заданным ID
        Task knownIdTask = new Task("Задача с известным ID", "Описание задачи");
        knownIdTask.setId(10); // Установка известного ID

        // Добавление задачи с известным ID
        int knownId = 10; // Предполагаем, что ID 10 еще не используется
        taskManager.addNewTask(knownIdTask);

        // Добавление новой задачи, позволяя системе генерировать ID
        Task autoGeneratedTask = new Task("Задача с сгенерированным ID", "Еще одно описание");
        int generatedId = taskManager.addNewTask(autoGeneratedTask);

        // Проверка, что задачи с известным и сгенерированным ID присутствуют в менеджере
        ArrayList<Task> allTasks = taskManager.getTasks();
        assertTrue(allTasks.contains(knownIdTask), "Задача с известным ID должна быть найдена.");
        assertTrue(allTasks.contains(autoGeneratedTask), "Задача с сгенерированным ID должна быть найдена.");

        // Проверка уникальности ID
        assertNotEquals(knownIdTask.getId(), generatedId, "ID задачи с известным ID и сгенерированным ID не должны совпадать.");
    }

    // проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    public void testTaskRemainsUnchangedAfterAdding() {
        // Инициализация менеджера
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        // Создание задачи с известными данными
        Task originalTask = new Task("Оригинальная задача", "Описание задачи");
        int originalId = originalTask.getId();

        // Снимок состояния задачи до добавления
        String originalName = originalTask.getName();
        String originalDescription = originalTask.getDescription();
        originalId = originalTask.getId();

        // Добавление задачи в менеджер
        taskManager.addNewTask(originalTask);

        // Получение всех задач после добавления
        ArrayList<Task> allTasks = taskManager.getTasks();

        // Поиск добавленной задачи среди всех задач
        for (Task task : allTasks) {
            if (task.getId() == originalId) {
                // Сравнение полей добавленной задачи с оригиналом
                assertEquals(originalName, task.getName(), "Имя задачи должно остаться неизменным.");
                assertEquals(originalDescription, task.getDescription(), "Описание задачи должно остаться неизменным.");
                break;
            }
        }
    }

    //добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    public void testSavingPreviousVersionOfTask() {
        // Инициализация HistoryManager
        HistoryManager historyManager = new InMemoryHistoryManager();

        // Создание первой версии задачи
        Task originalTask = new Task("Оригинальная задача", "Описание задачи");

        // Добавление задачи в историю
        historyManager.add(originalTask);

        // Изменение данных задачи
        originalTask.setName("Измененное имя");
        originalTask.setDescription("Измененное описание");

        // Повторное добавление задачи в историю
        historyManager.add(originalTask);

        // Получение истории
        ArrayList<Task> history = historyManager.getHistory();

        // Проверка, что в истории присутствуют обе версии задачи
        assertEquals(2, history.size(), "В истории должно быть две версии задачи.");
        assertTrue(history.contains(originalTask), "Оригинальная версия задачи должна быть в истории.");
        assertTrue(history.contains(new Task("Оригинальная задача", "Описание задачи")), "Первая версия задачи должна быть в истории.");
    }
}
