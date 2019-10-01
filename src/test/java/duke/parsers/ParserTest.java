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
            assertTrue(Parser.parse("breakfast burger") instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100 /date 1/1/2019") instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger") instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100 /date 1/1/2019") instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger") instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100 /date 1/1/2019") instanceof AddCommand);
            assertTrue(Parser.parse("list") instanceof ListCommand);
            assertTrue(Parser.parse("done 1") instanceof MarkDoneCommand);
        } catch (DukeException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

