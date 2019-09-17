package leduc;

import leduc.command.*;
import leduc.exception.NonExistentDateException;
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
        assertFalse(parser.parse("bye " )instanceof ByeCommand);
    }


}