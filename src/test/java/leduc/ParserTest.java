package leduc;

import leduc.command.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents a JUnit test class for the Parser.
 */
public class ParserTest {

    /**
     * Represents a JUnit test method to test if the parser could
     * create the right command depending on the input String (user).
     */
    @Test
    public void commandCreatedTest(){
        Parser parser = new Parser();
        assertTrue(parser.parse("ok") instanceof MeaninglessCommand);
        assertTrue(parser.parse("list") instanceof ListCommand);
        assertFalse(parser.parse("listlist") instanceof ListCommand);
        assertTrue(parser.parse("find eizae") instanceof FindCommand);
        assertTrue(parser.parse("done 12") instanceof DoneCommand);
        assertFalse(parser.parse("done okk") instanceof DoneCommand);
        assertTrue(parser.parse("delete 12") instanceof DeleteCommand);
        assertFalse(parser.parse("delete e") instanceof DeleteCommand);
        assertTrue(parser.parse("todo ekzoa") instanceof TodoCommand);
        assertTrue(parser.parse("deadline d1")instanceof DeadlineCommand);
        assertTrue(parser.parse("event e") instanceof EventCommand);
        assertTrue(parser.parse("bye")instanceof ByeCommand);
        assertTrue(parser.parse("edit")instanceof EditCommand);
        assertTrue(parser.parse("setwelcome message")instanceof SetWelcomeCommand);
        assertTrue(parser.parse("postpone 2 /by 12/12/2000 22:22")instanceof PostponeCommand);
        assertTrue(parser.parse("reschedule 2 /at 12/12/2000 11:11 - 13/12/2000 12:22")instanceof RescheduleCommand);
        assertTrue(parser.parse("snooze 2")instanceof SnoozeCommand);
        assertTrue(parser.parse("help")instanceof HelpCommand);
        assertTrue(parser.parse("sort date")instanceof SortCommand);
        assertTrue(parser.parse("sort description")instanceof SortCommand);
        assertFalse(parser.parse("bye " )instanceof ByeCommand);
    }


}