public class ToDoTest {
    private static final ToDo TEST1 = new ToDo("testing JUnit");
    private static final ToDo TEST2 = new ToDo("");

    public static ToDo test1() {
        return TEST1;
    }
    public static ToDo test2() {
        return TEST2;
    }
}