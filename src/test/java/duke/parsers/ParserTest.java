package duke.parsers;

import duke.commands.*;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
  
    @Test
    void parse() {
        try {
            assertTrue(Parser.parse("bye") instanceof ExitCommand);
            assertTrue(Parser.parse("todo hackathon") instanceof addCommand);
            assertTrue(Parser.parse("todo hackathon /needs 3 hours") instanceof addCommand);
            assertTrue(Parser.parse("todo hackathon /between 1/1/2019 1800, 2/1/2019 1900") instanceof addCommand);
            assertTrue(Parser.parse("deadline homework /by sunday") instanceof addCommand);
            assertTrue(Parser.parse("event exam /at classroom") instanceof addCommand);
            assertTrue(Parser.parse("list") instanceof ListCommand);
            assertTrue(Parser.parse("done 1") instanceof MarkDoneCommand);
            assertTrue(Parser.parse("schedule 12/12/2012 1212") instanceof ScheduleCommand);
            assertTrue(Parser.parse("remindme 4") instanceof RemindCommand);
            assertTrue(Parser.parse("findfreetime 4") instanceof FindFreeTimeCommand);
            assertTrue(Parser.parse("snooze 17 19") instanceof SnoozeCommand);
        } catch (DukeException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

