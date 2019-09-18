public class DeadlineTest {
    private static final Parser parser = new Parser();
    private static final Date by = parser.formatDate("22/12/2019 18:00");
    private static final Deadline DEADLINE1 = new Deadline("homework", by);
    private static final Deadline DEADLINE2 = new Deadline("", by);

    public static Deadline deadline1() {
        return DEADLINE1;
    }

    public static Deadline deadline2() {
        return DEADLINE2;
    }
}