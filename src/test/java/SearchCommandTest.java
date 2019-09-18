

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import command.SearchCommand;
import parser.Parser;

/**
 * test for search command feature
 * 
 * @author Hans kurnia
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
  void testParserValid(){
      Assertions.assertDoesNotThrow(() -> {Parser.parse("search 3");});
    }

  @Test
  @DisplayName("Test Parser with invalid search input")
  void testParserInvalidLong(){
    Assertions.assertThrows(NumberFormatException.class, () -> {Parser.parse("search h");});
  }
}