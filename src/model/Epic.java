package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasks = new ArrayList<>();
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }
    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        subtaskIds.add(subtask.getId());
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    public void updateEpicStatus() {
        boolean allDone = true;
        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.stream()
                    .filter(s -> s.getId() == subtaskId)
                    .findFirst()
                    .orElse(null);
            if (subtask!= null && subtask.getStatus()!= Status.DONE) {
                allDone = false;
                break;
            }
        }

        if (allDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return super.toString() + ", Number of Subtasks: " + subtasks.size();
    }
}

