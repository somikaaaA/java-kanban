package model;

public class Subtask extends Task {
    private int epicId; // Теперь здесь хранится ID эпика

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + ", Epic ID: " + epicId;
    }
}
