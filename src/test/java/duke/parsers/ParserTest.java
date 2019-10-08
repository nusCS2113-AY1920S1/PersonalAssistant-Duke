package duke.parsers;

import duke.autocorrect.Autocorrect;
import duke.commands.*;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    private Autocorrect autocorrect = new Autocorrect();

    @Test
    void parse() {
        try {
            assertTrue(Parser.parse("bye", autocorrect) instanceof ExitCommand);
            assertTrue(Parser.parse("breakfast burger", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100 /date 1/1/2019", autocorrect)
                    instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100 /date 1/1/2019", autocorrect)
                    instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100", autocorrect) instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100 /date 1/1/2019", autocorrect)
                    instanceof AddCommand);
            assertTrue(Parser.parse("list", autocorrect) instanceof ListCommand);
            assertTrue(Parser.parse("done 1", autocorrect) instanceof MarkDoneCommand);
        } catch (DukeException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

