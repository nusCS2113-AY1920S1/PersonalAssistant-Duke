package wrapper;

import entertainment.pro.model.MyDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MyDatetest {
    @Test
    public void dummyTest() {

        MyDate temp = new MyDate("12/12/2019 12:30", "14/12/2019 19:30");

        assertEquals("12th of December 2019, 12:30 PM", temp.getStartDateStr());
        assertEquals("14th of December 2019, 07:30 PM", temp.getEndDateStr());

    }
}
