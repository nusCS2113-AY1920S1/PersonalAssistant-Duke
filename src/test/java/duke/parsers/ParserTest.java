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
            Parser parser = new Parser(autocorrect);
            assertTrue(parser.parse("bye") instanceof ExitCommand);
            assertTrue(parser.parse("breakfast burger") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("lunch burger") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("dinner burger") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("add burger /calorie 100 /sodium 100 /fats 100")
                    instanceof AddItemCommand);
            assertTrue(parser.parse("list") instanceof ListCommand);
            assertTrue(parser.parse("done 1") instanceof MarkDoneCommand);
            assertTrue(parser.parse("help") instanceof HelpCommand);
            assertTrue(parser.parse("help breakfast") instanceof HelpCommand);
        } catch (DukeException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

