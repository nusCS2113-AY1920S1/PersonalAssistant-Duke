package duke.model.events;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoWithinTest {

    @Test
    void testToString() {
        LocalDateTime startDate = LocalDateTime.of(2018, 8, 8, 8, 8);
        LocalDateTime endDate = LocalDateTime.of(2019, 9, 9, 9, 9);
        DoWithin doWithin = new DoWithin("Homework", startDate, endDate);
        assertEquals(doWithin.toString(), "[W][✘] Homework within "
                            + startDate.toString() + " to " + endDate.toString());
    }
}
