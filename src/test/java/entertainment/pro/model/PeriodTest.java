//@@author hotspur1997

package entertainment.pro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PeriodTest {
    @Test
    public void getPeriodSuccess() {
        Period pevent = new Period("Joker", "P", "20/09/2019 22:00", "20/09/2019 00:00");
        assertEquals("20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM", pevent.getPeriod());

        Period pevent2 = new Period("Batman", "P", "20/09/2019 22:00", "01/10/2019 00:00");
        assertEquals("20th of September 2019, 10:00 PM to 01st of October 2019, 12:00 AM", pevent2.getPeriod());

        Period pevent3 = new Period("Batman", "P", "20/09/2019 1000", "01/10/2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", pevent3.getPeriod());

        Period pevent4 = new Period("Superman", "P", "20/09/2019 1000", "01-OCT-2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", pevent4.getPeriod());

        Period pevent5 = new Period("Superman", "P", "20-SEP-2019 1000", "01-OCT-2019 1400");
        assertEquals("20th of September 2019, 10:00 AM to 01st of October 2019, 02:00 PM", pevent5.getPeriod());
    }

    @Test
    public void getPeriodFailure() {
        Period pevent = new Period("Joker", "P", "20/09/2019 2200", "20/09/2020 00:00");
        assertEquals(false,
                pevent.getPeriod().equals(
                        "20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period pevent2 = new Period("Joker", "P", "20/10/2019 2200", "20/09/2019 00:00");
        assertEquals(false,
                pevent2.getPeriod().equals(
                        "20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period pevent3 = new Period("Joker", "P", "20/10/2019 2200", "20/09/2019 8 PM");
        assertEquals(false,
                pevent3.getPeriod().equals(
                        "20th of September 2019, 10:00 PM to 20th of September 2019, 8:00 PM"));

        Period pevent4 = new Period("Joker", "P", "20/10/2019 2200", "20-09-2019 12:00");
        assertEquals(false,
                pevent4.getPeriod().equals(
                        "20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM"));

        Period pevent5 = new Period("Superman", "P", "20-08-2019 1000", "01-09-2019 1400");
        assertEquals(false,
                pevent5.getPeriod().equals(
                        "20th of August 2019, 10:00 PM to 01st of September 2019, 12:00 AM"));
    }

    @Test
    public void toMessageSuccess() {
        Period pevent = new Period("Joker", "P", "20/09/2019 22:00", "20/09/2019 00:00");
        assertEquals(true,
                pevent.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM)"));

        Period pevent2 = new Period("Joker", "P", "20/09/2019 16:00", "20/09/2019 20:00");
        assertEquals(true,
                pevent2.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 04:00 PM to 20th of September 2019, 08:00 PM)"));

        Period pevent3 = new Period("Joker", "P", "20/09/2019 2000", "20/09/2019 2300");
        assertEquals(true,
                pevent3.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 08:00 PM to 20th of September 2019, 11:00 PM)"));


        Period pevent4 = new Period("Joker", "P", "20-SEP-2019 20:00", "20-OCT-2019 23:00");
        assertEquals(true,
                pevent4.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 08:00 PM to 20th of October 2019, 11:00 PM)"));

        Period pevent5 = new Period("Joker", "P", "20-SEP-2019 2100", "20-OCT-2019 2350");
        assertEquals(true,
                pevent5.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 09:00 PM to 20th of October 2019, 11:50 PM)"));
    }

    @Test
    public void toMessageFailure() {
        Period pevent = new Period("Joker", "P", "20/09/2019 10 PM", "20/09/2019 12 AM");
        assertEquals(false,
                pevent.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 10:00 PM to 20th of September 2019, 12:00 AM)"));

        Period pevent2 = new Period("Joker", "P", "20/SEP/2019 16:00", "20/09/2019 8PM");
        assertEquals(false,
                pevent2.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 04:00 PM to 20th of September 2019, 08:00 PM)"));

        Period pevent3 = new Period("Joker", "P", "20/09/2019 PM8", "20/09/2019 PM11");
        assertEquals(false,
                pevent3.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 08:00 PM to 20th of September 2019, 11:00 PM)"));


        Period pevent4 = new Period("Joker", "P", "20-SEP-2019 8PM", "20-OCT-2019 11PM");
        assertEquals(false,
                pevent4.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 08:00 PM to 20th of October 2019, 11:00 PM)"));

        Period pevent5 = new Period("Joker", "P", "20-SEP-2019 900PM", "20-OCT-2019 1150PM");
        assertEquals(false,
                pevent5.toMessage().equals(
                        "Joker (Period: 20th of September 2019, 09:00 PM to 20th of October 2019, 11:50 PM)"));
    }
}