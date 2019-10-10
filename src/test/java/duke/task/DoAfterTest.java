package duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterTest {
    @Test
    public void testToString() {
        LocalDateTime testTime = LocalDateTime.of(2017, 2, 13, 15, 56);
        DoAfter newDoAfter = new DoAfter("To return book", testTime, null);
        assertEquals("[A][NOT DONE] To return book (after: 13/2/2017 1556)", newDoAfter.toString());

        newDoAfter.markAsDone();
        assertEquals("[A][DONE] To return book (after: 13/2/2017 1556)", newDoAfter.toString());

        newDoAfter = new DoAfter("To return book", null, "Read Book");

        assertEquals("[A][NOT DONE] To return book (after: Read Book)", newDoAfter.toString());

    }
}