package entertainment.pro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertEquals;
public class PeriodTest {
    @Test
    public void getPeriodSuccess() {
        Period p_event = new Period("Joker", "P", "20/09/2019 22:00", "20/09/2019 00:00");
        assertEquals("20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM", p_event.getPeriod());

        Period p_event2 = new Period("Batman", "P", "20/09/2019 22:00", "01/10/2019 00:00");
        assertEquals("20th of September 2019, 10:00 PM to 01st of October 2019, 12:00 AM", p_event2.getPeriod());

        Period p_event3 = new Period("Batman", "P", "20/09/2019 1000", "01/10/2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", p_event3.getPeriod());

        Period p_event4 = new Period("Superman", "P", "20/09/2019 1000", "01-OCT-2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", p_event4.getPeriod());

        Period p_event5 = new Period("Superman", "P", "20-SEP-2019 1000", "01-OCT-2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", p_event5.getPeriod());
    }

    @Test
    public void getPeriodFailure() {
        Period p_event = new Period("Joker", "P", "20/09/2019 2200", "20/09/2020 00:00");
        assertEquals(false, p_event.getPeriod().equals("20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period p_event2 = new Period("Joker", "P", "20/10/2019 2200", "20/09/2019 00:00");
        assertEquals(false, p_event2.getPeriod().equals("20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period p_event3 = new Period("Joker", "P", "20/10/2019 2200", "20/09/2019 8 PM");
        assertEquals(false, p_event3.getPeriod().equals("20th of September 2019, 10:00 PM to 20th of September 2019, 8:00 PM"));

        Period p_event4 = new Period("Joker", "P", "20/10/2019 2200", "20-09-2019 12:00");
        assertEquals(false, p_event4.getPeriod().equals("20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period p_event5 = new Period("Superman", "P", "20-08-2019 1000", "01-09-2019 1400");
        assertEquals(false, p_event5.getPeriod().equals("20th of August 2019, 10:00 PM to 01st of September 2019, 12:00 AM"));
    }

    @Test
    public void toMessageSuccess() {
        Period p_event = new Period("Joker", "P", "20/09/2019 22:00", "20/09/2019 00:00");
        assertEquals(true, p_event.toMessage().equals("Joker (Period: 20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM)"));

        Period p_event2 = new Period("Joker", "P", "20/09/2019 16:00", "20/09/2019 20:00");
        assertEquals(true, p_event2.toMessage().equals("Joker (Period: 20th of September 2019, 04:00 PM to 20th of September 2019, 08:00 PM)"));

        Period p_event3 = new Period("Joker", "P", "20/09/2019 2000", "20/09/2019 2300");
        assertEquals(true, p_event3.toMessage().equals("Joker (Period: 20th of September 2019, 08:00 PM to 20th of September 2019, 11:00 PM)"));


        Period p_event4 = new Period("Joker", "P", "20-SEP-2019 20:00", "20-OCT-2019 23:00");
        assertEquals(true, p_event4.toMessage().equals("Joker (Period: 20th of September 2019, 08:00 PM to 20th of October 2019, 11:00 PM)"));

        Period p_event5 = new Period("Joker", "P", "20-SEP-2019 2100", "20-OCT-2019 2350");
        assertEquals(true, p_event5.toMessage().equals("Joker (Period: 20th of September 2019, 09:00 PM to 20th of October 2019, 11:50 PM)"));
    }

    @Test
    public void toMessageFailure() {
        Period p_event = new Period("Joker", "P", "20/09/2019 10 PM", "20/09/2019 12 AM");
        assertEquals(false, p_event.toMessage().equals("Joker (Period: 20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM)"));

        Period p_event2 = new Period("Joker", "P", "20/SEP/2019 16:00", "20/09/2019 8PM");
        assertEquals(false, p_event2.toMessage().equals("Joker (Period: 20th of September 2019, 04:00 PM to 20th of September 2019, 08:00 PM)"));

        Period p_event3 = new Period("Joker", "P", "20/09/2019 PM8", "20/09/2019 PM11");
        assertEquals(false, p_event3.toMessage().equals("Joker (Period: 20th of September 2019, 08:00 PM to 20th of September 2019, 11:00 PM)"));


        Period p_event4 = new Period("Joker", "P", "20-SEP-2019 8PM", "20-OCT-2019 11PM");
        assertEquals(false, p_event4.toMessage().equals("Joker (Period: 20th of September 2019, 08:00 PM to 20th of October 2019, 11:00 PM)"));

        Period p_event5 = new Period("Joker", "P", "20-SEP-2019 900PM", "20-OCT-2019 1150PM");
        assertEquals(false, p_event5.toMessage().equals("Joker (Period: 20th of September 2019, 09:00 PM to 20th of October 2019, 11:50 PM)"));
    }




}
