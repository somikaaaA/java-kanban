package model;

public class Subtask extends Task {
    private Epic parentEpic;
    private int epicId;

    public Subtask(String name, String description, Epic parentEpic) {
        super(name, description);
        this.parentEpic = parentEpic;
        this.epicId = parentEpic.getId();
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + ", Parent Epic ID: " + epicId + ", Parent Epic: " + (parentEpic!= null? parentEpic.getName() : "None");
    }
}
