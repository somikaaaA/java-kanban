package controllers;

import controllers.TaskManager;

public class Managers {
    private Managers() {

    }

    public static TaskManager getDefault() {
        return new controllers.InMemoryTaskManager();
    }
}
