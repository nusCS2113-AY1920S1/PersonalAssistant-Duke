package seedu.hustler.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnoozeTaskTest {

    // Create variables for sampleDeadline and the snoozed sampleDeadline of 1 days
    private LocalDateTime ldt1 = LocalDateTime.of(2019,11,20,18,0);
    private LocalDateTime ldt2 = LocalDateTime.of(2019,11,21,18,0);
    private LocalDateTime now = LocalDateTime.of(2019,11,13,12,0);
    private Deadline sampleDeadline = new Deadline("lab report", ldt1, "M", "chem", now);
    private Deadline snoozedDeadline = new Deadline("lab report", ldt2, "M", "chem", now);

    @Test
    public void test_snoozeTask_format1() {
        TaskList sampleTaskList = new TaskList(new ArrayList<>());
        sampleTaskList.add(sampleDeadline);
        String input = "/snooze 1 1 days";
        String[] splitInput = input.split(" ",2)[1].split(" ");
        sampleTaskList.snoozeTask(sampleTaskList,0, splitInput);
        assertEquals(snoozedDeadline, sampleTaskList.get(0));
    }

    @Test
    public void test_snoozeTask_format2() {
        TaskList sampleTaskList = new TaskList(new ArrayList<>());
        sampleTaskList.add(sampleDeadline);
        String input = "/snooze 1 21/11/2019 1800";
        String[] splitInput = input.split(" ",2)[1].split(" ");
        sampleTaskList.snoozeTask(sampleTaskList,0, splitInput);
        assertEquals(snoozedDeadline, sampleTaskList.get(0));
    }
}
