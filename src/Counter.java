public class Counter {
    private static int count = 0;

    public static int nextId() {
        return ++count;
    }
}
