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
            Parser Parser = new Parser(autocorrect);
            assertTrue(Parser.parse("bye") instanceof ExitCommand);
            assertTrue(Parser.parse("breakfast burger") instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("breakfast burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger") instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("lunch burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger") instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100") instanceof AddCommand);
            assertTrue(Parser.parse("dinner burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(Parser.parse("add burger /calorie 100 /sodium 100 /fats 100")
                    instanceof AddItemCommand);
            assertTrue(Parser.parse("list") instanceof ListCommand);
            assertTrue(Parser.parse("done 1") instanceof MarkDoneCommand);
            assertTrue(Parser.parse("help") instanceof HelpCommand);
            assertTrue(Parser.parse("help breakfast") instanceof HelpCommand);
        } catch (DukeException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

