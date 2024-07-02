public class Subtask extends Task {
    private Epic parentEpic;

    public Subtask(String name, String description, Epic parentEpic) {
        super(name, description);
        this.parentEpic = parentEpic;
    }

//    public Epic getParentEpic() {
//        return parentEpic;
//    }

    @Override
    public String toString() {
        return super.toString() + ", Parent Epic: " + (parentEpic!= null? parentEpic.getName() : "None");
    }
}
