import Task.After;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test for After class.
 */
public class AfterTest {
    @Test
    public void afterTest() {
        String cross = "✗";
        System.out.println("Start afterTest");

        /**
         * First test is for after a specific task
         */
        String info = "return book";
        String endDate = "exam is done";
        After after = new After(info, false, endDate);
        assertEquals("[A][" + cross + "] return book (after: exam is done)",after.toString());

        /**
         * Second test is for after a specific date
         */
        String info2 = "buy bread";
        String endDate2 = "15/9/2019";
        After after2 = new After(info2, false, endDate2);
        assertEquals("[A][" + cross + "] buy bread (after: 15/9/2019)",after2.toString());

        System.out.println("Passed afterTest");
    }
}
