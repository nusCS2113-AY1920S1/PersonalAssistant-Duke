
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import command.SearchCommand;
import exception.DukeException;
import parser.ParserFactory;

/**
 * unit test for search command feature.
 * 
 * @author Hans kurnia
 * @version 1.1
 */

public class SearchCommandTest {

    @Test
    @DisplayName("Testing valid search Command creation")
    void testValidSearchCommandCreation() {
        SearchCommand actualCmd = new SearchCommand((long) 3);
        long duration = actualCmd.getDurationToFind();
        Assertions.assertEquals((long) 3, duration);
    }

    @Test
    @DisplayName("Test Parser with valid search input")
    void testParserValid() {
        Assertions.assertDoesNotThrow(() -> {
            ParserFactory.parse("search 3");
        });
    }

    @Test
    @DisplayName("Test Parser with invalid search input")
    void testParserInvalidLong() {
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("search h");
        });
    }
}