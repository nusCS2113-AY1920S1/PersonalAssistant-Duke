package seedu.hustler.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hustler.parser.DateTimeParser.getDateTime;

public class DetectAnomaliesTest {

    @Test

    /**
     * Check if anomalies can be detected.
     */
    public void dummyTest() {

        ArrayList<Task> list = new ArrayList<>();
        LocalDateTime localDateTime = getDateTime("9/9/2019 1900");
        list.add(new Deadline("assignemntsss",localDateTime));

        assertTrue(DetectAnomalies.test(new Deadline("another assignment",localDateTime),list));
    }
}
