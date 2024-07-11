package controllers;

//import controllers.HistoryManager;
//import controllers.InMemoryHistoryManager;

public class Managers {
    private Managers() {}

    public static TaskManager getDefault() {
        return new controllers.InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
