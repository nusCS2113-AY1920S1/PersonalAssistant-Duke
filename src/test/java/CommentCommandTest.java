import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import command.CommentCommand;
import exception.DukeException;
import parser.ParserFactory;

/**
 * unit test for search command feature.
 * 
 * @author Hans kurnia
 * @version 1.2
 */

public class CommentCommandTest {

    @Test
    @DisplayName("Testing valid comment command creation")
    void testValidCommentCommandCreation() {
        CommentCommand actualCmd = new CommentCommand(1, "this is a test");
        int index = actualCmd.getIndexOfTask();
        String comment = actualCmd.getComment();
        Assertions.assertEquals(1, index);
        Assertions.assertEquals("this is a test", comment);
    }

    @Test
    @DisplayName("Test Parser with valid comment input")
    void testParserValid() {
        Assertions.assertDoesNotThrow(() -> {
            ParserFactory.parse("comment 1 testing");
        });
    }

    @Test
    @DisplayName("Test Parser with invalid comment input")
    void testParserInvalidLong() {
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("comment");
        });
    }
}