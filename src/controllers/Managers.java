package controllers;

public class Managers {
    private Managers() {}

    public static TaskManager getDefault() {
        return new controllers.InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
