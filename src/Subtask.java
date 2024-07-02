public class Subtask extends Task {
    private Epic parentEpic;

    public Subtask(String name, String description, Epic parentEpic) {
        super(name, description);
        this.parentEpic = parentEpic;
    }

    @Override
    public String toString() {
        return super.toString() + ", Parent Epic: " + (parentEpic!= null? parentEpic.getName() : "None");
    }
}
