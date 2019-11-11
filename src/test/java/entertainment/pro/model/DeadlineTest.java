package entertainment.pro.model;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeadlineTest {
    @Test
    public void getDateSuccess() {
        Deadline d_event = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", d_event.getDate().toString());

        d_event = new Deadline("Joker", "D", "20/09/2019");
        assertEquals("20th of September 2019, 12:00 AM", d_event.getDate().toString());

        d_event = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", d_event.getDate().toString());

        d_event = new Deadline("Joker", "D", "20/09/2019 1000");
        assertEquals("20th of September 2019, 10:00 AM", d_event.getDate().toString());

        d_event = new Deadline("Joker", "D", "20-SEP-2019 1000");
        assertEquals("20th of September 2019, 10:00 AM", d_event.getDate().toString());
    }

    @Test
    public void getDateFailure() {
        Deadline d_event = new Deadline("Joker", "D", "20-09-2019 22:00");
        assertEquals(false, d_event.getDate().toString().equals("20th of September 2019, 10:00 PM"));

        d_event = new Deadline("Joker", "D", "20/SEP/2019");
        assertEquals(false, d_event.getDate().toString().equals("20th of September 2019, 12:00 AM"));

        d_event = new Deadline("Joker", "D", "2019/09/20 2200");
        assertEquals(false, d_event.getDate().toString().equals("20th of September 2019, 10:00 PM"));

        d_event = new Deadline("Joker", "D", "20/09/2019 2200AM");
        assertEquals(false, d_event.getDate().toString().equals("20th of September 2019, 10:00 AM"));

        d_event = new Deadline("Joker", "D", "2019-09-20 1000PM");
        assertEquals(false, d_event.getDate().toString().equals("20th of September 2019, 10:00 AM"));
    }

    @Test
    public void setTimeSuccess() {
        Deadline d_event = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", d_event.getDate().toString());
        d_event.setTime("20/09/2019 16:00");
        assertEquals("20th of September 2019, 04:00 PM", d_event.getDate().toString());

        Deadline d_event2 = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", d_event2.getDate().toString());
        d_event2.setTime("20/09/2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", d_event2.getDate().toString());

        Deadline d_event3 = new Deadline("Joker", "D", "20-SEP-2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", d_event3.getDate().toString());
        d_event3.setTime("20-SEP-2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", d_event3.getDate().toString());
    }

    @Test
    public void setTimeFailure() {
        Deadline d_event = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", d_event.getDate().toString());
        d_event.setTime("20/09/2019 16:00");
        assertEquals("20th of September 2019, 04:00 PM", d_event.getDate().toString());

        Deadline d_event2 = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", d_event2.getDate().toString());
        d_event2.setTime("20/09/2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", d_event2.getDate().toString());

        Deadline d_event3 = new Deadline("Joker", "D", "20-SEP-2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", d_event3.getDate().toString());
        d_event3.setTime("20-SEP-2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", d_event3.getDate().toString());
    }

    @Test
    public void toMessageSuccess() {
        Deadline d_event = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("Joker (by: 20th of September 2019, 10:00 PM)", d_event.toMessage());

        Deadline d_event2 = new Deadline("Batman Begins", "D", "20/09/2019 16:00");
        assertEquals("Batman Begins (by: 20th of September 2019, 04:00 PM)", d_event2.toMessage());

        Deadline d_event3 = new Deadline("Superman", "D", "20/09/2019");
        assertEquals("Superman (by: 20th of September 2019, 12:00 AM)", d_event3.toMessage());
    }

    @Test
    public void toMessageFailure() {
        Deadline d_event = new Deadline("Batman", "D", "20/09/2019 22:00");
        assertEquals(false, d_event.toMessage().equals("Joker (by: 20th of September 2019, 10:00 PM)"));

        Deadline d_event2 = new Deadline("Joker", "D", "2019/09/20 16:00");
        assertEquals(false, d_event2.toMessage().equals("Joker (by: 20th of September 2019, 04:00 PM)"));

        Deadline d_event3 = new Deadline("Superman", "D", "20-09-2019");
        assertEquals(false, d_event3.toMessage().equals("Superman (by: 20th of September 2019, 12:00 AM)"));
    }
}
