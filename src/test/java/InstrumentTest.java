//@@author Dng132FEI
import mistermusik.commons.Instruments.InstrumentList;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.formatting.EventDate;
import mistermusik.logic.EventList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstrumentTest {
    @Test
    public void addInstrumentTest() {
        InstrumentList instrumentListTest = new InstrumentList();
        int index = instrumentListTest.addInstrument("double bass");
        String instrumentIndexAndName = instrumentListTest.getIndexAndInstrument(index);
        assertEquals("1. double bass", instrumentIndexAndName);
        int indexTwo = instrumentListTest.addInstrument("flute");
        String instrumentIndexAndNameTwo = instrumentListTest.getIndexAndInstrument(indexTwo);
        assertEquals("2. flute", instrumentIndexAndNameTwo);
    }

    @Test
    public void viewInstrumentsTest() {
        InstrumentList instrumentListTest = new InstrumentList();
        int index = instrumentListTest.addInstrument("double bass");
        String instrumentIndexAndName = instrumentListTest.getIndexAndInstrument(index);
        int indexTwo = instrumentListTest.addInstrument("flute");
        String instrumentIndexAndNameTwo = instrumentListTest.getIndexAndInstrument(indexTwo);
        String viewResult = instrumentListTest.getInstruments();
        assertEquals("1. double bass\n2. flute\n", viewResult);
    }

    @Test
    public void serviceTest() {
        InstrumentList instrumentListTest = new InstrumentList();
        int instrumentIndex = instrumentListTest.addInstrument("double bass");
        EventDate inputDate = new EventDate("26-11-2017 1032");
        int serviceIndex = instrumentListTest.service(instrumentIndex, inputDate, "varnished body");
        String instrumentIndexAndName = instrumentListTest.getIndexAndInstrument(instrumentIndex);
        String serviceIndexAndName = instrumentListTest.getIndexAndService(instrumentIndex, serviceIndex);
        assertEquals("1. double bass", instrumentIndexAndName);
        assertEquals("1. varnished body  on Sun, 26 Nov 2017, 10:32", serviceIndexAndName);
        EventDate inputDateTwo = new EventDate("26-11-2018 1032");
        int serviceIndexTwo = instrumentListTest.service(instrumentIndex, inputDate, "rehaired bow");
        String instrumentIndexAndNameTwo = instrumentListTest.getIndexAndInstrument(instrumentIndex);
        String serviceIndexAndNameTwo = instrumentListTest.getIndexAndService(instrumentIndex, serviceIndex);
        assertEquals("1. double bass", instrumentIndexAndName);
        assertEquals("2. rehaired bow  on Mon, 26 Nov 2017, 10:32", serviceIndexAndName);
    }

    @Test
    public void viewServicesTest() {
        InstrumentList instrumentListTest = new InstrumentList();
        int instrumentIndex = instrumentListTest.addInstrument("double bass");
        EventDate inputDate = new EventDate("26-11-2017 1032");
        int serviceIndex = instrumentListTest.service(instrumentIndex, inputDate, "varnished body");
        String instrumentIndexAndName = instrumentListTest.getIndexAndInstrument(instrumentIndex);
        String serviceIndexAndName = instrumentListTest.getIndexAndService(instrumentIndex, serviceIndex);
        EventDate inputDateTwo = new EventDate("26-11-2018 1032");
        int serviceIndexTwo = instrumentListTest.service(instrumentIndex, inputDate, "rehaired bow");
        String serviceIndexAndNameTwo = instrumentListTest.getIndexAndService(instrumentIndex, serviceIndex);
        String viewServiceResult = instrumentListTest.getInstrumentServiceInfo(instrumentIndex);
        assertEquals("1. varnished body  on Sun, 26 Nov 2017, 10:32\n2. rehaired bow  on " +
                "Mon, 26 Nov 2017, 10:32\n", viewServiceResult);
    }
}
