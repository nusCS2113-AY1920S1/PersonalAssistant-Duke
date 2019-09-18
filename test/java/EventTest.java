public class EventTest {
    private static final Parser parser = new Parser();
    private static final Date by = parser.formatDate("22/12/2019 18:00");
    private static final Event EVENT1= new Event("homework", by);
    private static final Event EVENT2= new Event("", by);

    public static Event event1() {
        return EVENT1;
    }
    public static Event event2() {
        return EVENT2;
    }
}