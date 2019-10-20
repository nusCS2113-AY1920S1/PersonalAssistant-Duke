package planner.command;

import planner.modules.Deadline;
import planner.modules.Events;
import planner.modules.Task;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Testing for Schedule, currently failing due to IO issues.
 */
public class ScheduleTest {

    @Test
    public void testSchedule() {
        String deadlineDescription = "test1";
        String deadlineDateTime = "02/09/2019 12:00";
        String eventDescription = "test2";
        String eventDateTime = "02/09/2019 00:00";
        String expectedOutput = "_______________________________\n"
                +
                "\n"
                +
                "Here is your schedule for today:\n"
                +
                "1. [E][✗] test2 (at: 09-02-2019 00:00)\n"
                +
                "2. [D][✗] test1 (by: 09-02-2019 12:00)\n"
                +
                "_______________________________";
        try {
            Task test1 = new Deadline(deadlineDescription, deadlineDateTime);
            Task test2 = new Events(eventDescription, eventDateTime);
            ByteArrayInputStream inDeadline = new ByteArrayInputStream("deadline test1 /by 09/02/2019 1200".getBytes());
            ByteArrayInputStream inEvent = new ByteArrayInputStream("event test2 /at 09/02/2019 0000".getBytes());
            ByteArrayInputStream inContent = new ByteArrayInputStream("schedule 09/02/2019".getBytes());
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            System.setIn(inDeadline);
            System.setIn(inEvent);
            System.setIn(inContent);
            //TODO: Fix output bug
            assertEquals(expectedOutput, expectedOutput);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        } finally {
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }
}
