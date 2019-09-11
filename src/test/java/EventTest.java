import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void EventTest(){
        Event event = new Event("Test", "Place");
        assert event.toString().equals("[E][\u2718] Test (at: Place)");
        event.markDone();
        assert event.toString().equals("[E][\u2713] Test (at: Place)");
    }

}
