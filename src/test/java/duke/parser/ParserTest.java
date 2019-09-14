package duke.parser;

import duke.command.Command;
import duke.commons.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    private void addEvent_commandWithTooManyArguments_addFailed() {
        try {
            Command cmd = Parser.getCommand("event homework -at 10/10/1999 18:00 -from 10/10/1999 18:00 -to 10/10/1999 18:00");
            fail();
        } catch (DukeException e) {
            assertEquals("Too many arguments", e.getMessage());
        }
    }

    @Test
    private void addEvent_commandMissingFrom_addFailed() {
        try {
            Command cmd = Parser.getCommand("event homework -to 10/10/1999 18:00");
            fail();
        } catch (DukeException e) {
            assertEquals("Missing from/to", e.getMessage());
        }
    }

    @Test
    private void addEvent_commandMissingDescription_addFailed() {
        try {
            Command cmd = Parser.getCommand("event -to 10/10/1999 18:00");
            fail();
        } catch (DukeException e) {
            assertEquals("Please enter todo description", e.getMessage());
        }
    }

}
