package controllers;

import model.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.size() >= 10) {
                history.remove(0);
            }
            history.add(task);
        } else {
            System.out.println("Невозможно добавить null в историю");
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
