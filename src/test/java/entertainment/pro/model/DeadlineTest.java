//@@author hotspur1997

package entertainment.pro.model;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeadlineTest {
    @Test
    public void getDateSuccess() {
        Deadline devent = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", devent.getDate().toString());

        devent = new Deadline("Joker", "D", "20/09/2019");
        assertEquals("20th of September 2019, 12:00 AM", devent.getDate().toString());

        devent = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", devent.getDate().toString());

        devent = new Deadline("Joker", "D", "20/09/2019 1000");
        assertEquals("20th of September 2019, 10:00 AM", devent.getDate().toString());

        devent = new Deadline("Joker", "D", "20-SEP-2019 1000");
        assertEquals("20th of September 2019, 10:00 AM", devent.getDate().toString());
    }

    @Test
    public void getDateFailure() {
        Deadline devent = new Deadline("Joker", "D", "20-09-2019 22:00");
        assertEquals(false, devent.getDate().toString().equals("20th of September 2019, 10:00 PM"));

        devent = new Deadline("Joker", "D", "20/SEP/2019");
        assertEquals(false, devent.getDate().toString().equals("20th of September 2019, 12:00 AM"));

        devent = new Deadline("Joker", "D", "2019/09/20 2200");
        assertEquals(false, devent.getDate().toString().equals("20th of September 2019, 10:00 PM"));

        devent = new Deadline("Joker", "D", "20/09/2019 2200AM");
        assertEquals(false, devent.getDate().toString().equals("20th of September 2019, 10:00 AM"));

        devent = new Deadline("Joker", "D", "2019-09-20 1000PM");
        assertEquals(false, devent.getDate().toString().equals("20th of September 2019, 10:00 AM"));
    }

    @Test
    public void setTimeSuccess() {
        Deadline devent = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", devent.getDate().toString());
        devent.setTime("20/09/2019 16:00");
        assertEquals("20th of September 2019, 04:00 PM", devent.getDate().toString());

        Deadline devent2 = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", devent2.getDate().toString());
        devent2.setTime("20/09/2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", devent2.getDate().toString());

        Deadline devent3 = new Deadline("Joker", "D", "20-SEP-2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", devent3.getDate().toString());
        devent3.setTime("20-SEP-2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", devent3.getDate().toString());
    }

    @Test
    public void setTimeFailure() {
        Deadline devent = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("20th of September 2019, 10:00 PM", devent.getDate().toString());
        devent.setTime("20/09/2019 16:00");
        assertEquals("20th of September 2019, 04:00 PM", devent.getDate().toString());

        Deadline devent2 = new Deadline("Joker", "D", "20/09/2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", devent2.getDate().toString());
        devent2.setTime("20/09/2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", devent2.getDate().toString());

        Deadline devent3 = new Deadline("Joker", "D", "20-SEP-2019 2200");
        assertEquals("20th of September 2019, 10:00 PM", devent3.getDate().toString());
        devent3.setTime("20-SEP-2019 1600");
        assertEquals("20th of September 2019, 04:00 PM", devent3.getDate().toString());
    }

    @Test
    public void toMessageSuccess() {
        Deadline devent = new Deadline("Joker", "D", "20/09/2019 22:00");
        assertEquals("Joker (by: 20th of September 2019, 10:00 PM)", devent.toMessage());

        Deadline devent2 = new Deadline("Batman Begins", "D", "20/09/2019 16:00");
        assertEquals("Batman Begins (by: 20th of September 2019, 04:00 PM)", devent2.toMessage());

        Deadline devent3 = new Deadline("Superman", "D", "20/09/2019");
        assertEquals("Superman (by: 20th of September 2019, 12:00 AM)", devent3.toMessage());
    }

    @Test
    public void toMessageFailure() {
        Deadline devent = new Deadline("Batman", "D", "20/09/2019 22:00");
        assertEquals(false, devent.toMessage().equals("Joker (by: 20th of September 2019, 10:00 PM)"));

        Deadline devent2 = new Deadline("Joker", "D", "2019/09/20 16:00");
        assertEquals(false, devent2.toMessage().equals("Joker (by: 20th of September 2019, 04:00 PM)"));

        Deadline devent3 = new Deadline("Superman", "D", "20-09-2019");
        assertEquals(false, devent3.toMessage().equals("Superman (by: 20th of September 2019, 12:00 AM)"));
    }
}