public class Subtask extends Task {
    public final Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

//    public Epic getEpic() {
//        return epic;
//    }
}